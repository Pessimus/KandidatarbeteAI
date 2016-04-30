package Model;

import Model.Resources.*;
import Model.Structures.Farm;
import Model.Structures.House;
import Model.Structures.Stockpile;
import Utility.Constants;
import Utility.InventoryRender;
import Utility.RenderObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * Created by Martin on 23/02/2016.
 */
public class World{

	public static int nbrCharacters = 0;
	public static int nbrStructures = 0;
	public static int nbrTrees = 0;
	public static int nbrAnimals = 0;
	public static int nbrTime = 0;

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	//------------------Functionality-------------------\\
	//PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private boolean pause;
	private boolean showingCurrentActivity = false;

	//-----------------Characteristics------------------\\
	private double width;
	private double height;

	//----------------Objects in World------------------\\
	private HashMap<Integer,Character> characters;
	private LinkedList<ICollidable> collidablesR;
	private CollisionList collidables;
	private LinkedList<ITimeable> timeables;
	private CollisionList statics; //List containing all collidables that does not move (or get destroyed or created too often)

	//------------------Remove lists--------------------\\
	private LinkedList<ICollidable> collidablestoberemoved;
	private LinkedList<ICollidable> collideablesrtoberemoved;
	private LinkedList<ITimeable> timeablestoberemoved;
	private LinkedList<Character> characterstoberemoved;


//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * Creating a world with all lists initialized as empty.
	 * @param width the width of the world.
	 * @param height the height of the world.
	 */
	public World (double width, double height){
		//Initializing world characteristics
		this.width = width;
		this.height = height;

		//Initializing lists for objects in world.
		this.collidables = new CollisionList(0,width,0,height);
		this.collidablesR = new LinkedList<>();
		this.timeables = new LinkedList<>();
		this.characters = new HashMap<>();
		this.statics = new CollisionList(0,width,0,height);

		//Initializing removal lists
		collidablestoberemoved = new LinkedList<>();
		collideablesrtoberemoved = new LinkedList<>();
		timeablestoberemoved = new LinkedList<>();
		characterstoberemoved = new LinkedList<>();

		//Initializing mask for pathfinding
		Constants.PATHFINDER_OBJECT.updateMask(this.statics);

		Schedule.init();

	}

	//TODO remove hardcoded values (move them to constants)
	/**
	 * Creating a world with randomly generated objects specified by the parameters.
	 * @param width the width of the world.
	 * @param height the height of the world.
	 * @param nrTrees the number of trees to randomly spawn in the world at creation.
	 * @param nrLakes the number of lakes to randomly spawn in the world at creation.
	 * @param nrStones the number of stones to randomly spawn in the world at creation.
	 * @param nrAnimals the number of crops to randomly spawn in the world at creation.
	 */
	public World (double width, double height, int nrTrees, int nrLakes, int nrStones, int nrGold, int nrAnimals){

		this(width, height);

		int i = 0;
		double tmpX;
		double tmpY;
		while(i < nrAnimals){
			tmpX = Math.random()*this.width;
			tmpY = Math.random()*this.height;

			addAnimal(tmpX, tmpY, new Meat(10, 10), 0, 0, width, height);

			i++;
		}
		i = 0;
		while(i < nrTrees){
			tmpX = Math.random()*this.width;
			tmpY = Math.random()*this.height;

			Wood tmpWood = new Wood(10,10,1,tmpX,tmpY);
			addRenewableResourcePoint(tmpWood, RenderObject.RENDER_OBJECT_ENUM.WOOD, tmpX, tmpY, Constants.TREE_COLLISION_RADIUS);

			i++;
		}
		i = 0;
		while(i < nrLakes){
			tmpX = Math.random()*this.width;
			tmpY = Math.random()*this.height;

			Water tmpLake = new Water(1,1);
			addInfiniteResourcePoint(tmpLake, RenderObject.RENDER_OBJECT_ENUM.LAKE, tmpX, tmpY, Constants.LAKE_COLLISION_RADIUS);

			i++;
		}
		i = 0;
		while(i < nrStones){
			tmpX = Math.random()*this.width;
			tmpY = Math.random()*this.height;

			Stone tmpStone = new Stone(50,5);
			addFiniteResourcePoint(tmpStone, RenderObject.RENDER_OBJECT_ENUM.STONE, tmpX, tmpY, Constants.STONE_COLLISION_RADIUS);

			i++;
		}
		i = 0;
		while(i < nrGold){
			tmpX = Math.random()*this.width;
			tmpY = Math.random()*this.height;

			Gold tmpStone = new Gold(50,5);
			addFiniteResourcePoint(tmpStone, RenderObject.RENDER_OBJECT_ENUM.GOLD, tmpX, tmpY, Constants.STONE_COLLISION_RADIUS);

			i++;
		}

	}

//---------------------------------------------UPDATE METHODS---------------------------------------------------------\\

	/**
	 * Public method for updating the values of all objects in the world that depend on time,
	 * remove objects that should no longer exist and
	 * check what objects collide with each other.
	 */
	public void update() {

		World.nbrTime = nbrTime+1;//%100000;

		if (!pause) {
			updateTimeables();

			checkObjectsForRemoval();

			removeObjects();

			this.collidables.handleCollision();

			Schedule.executeTasks();

			updateCharacters();

		}
	}

	private void updateCharacters(){
		LinkedList<Character> mothers = new LinkedList<>();
		for(Character character : this.characters.values()){
			if(character.isInLabour()){
				//Character tmp = character.birth();
				mothers.add(character);
				//this.characters.put(tmp.getKey(),tmp);
			}
		}
		for(Character tmp : mothers){
			tmp.birth(this);
			//this.characters.put(tmp.getKey(),tmp);
		}
	}

	/**
	 * Private method for updating the values of all objects in the world that depend on time.
	 */
	private void updateTimeables(){
		LinkedList<ITimeable> spawningTimeables = new LinkedList<>();
		for (ITimeable timedObj : timeables) {
			timedObj.updateTimeable();
			if(timedObj.isSpawning()){
				spawningTimeables.add(timedObj);
			}
		}
		for (ITimeable spawner : spawningTimeables){
			spawner.spawn(this);
		}
	}

	/**
	 * Private method for checking what objects should be removed, and staging them for removal.
	 */
	private void checkObjectsForRemoval(){
		for (ICollidable collidable : collidablesR) {//Loop on collidablesR as it supportes for-each
			if (collidable.toBeRemoved()) {
				collidablestoberemoved.add(collidable);
				collideablesrtoberemoved.add(collidable);

				if(collidable.getClass()==Character.class){
					nbrCharacters--;
				}else if(collidable.getClass()==Animal.class){
					nbrAnimals--;
				}else if(collidable.getClass()==ResourcePoint.class){
					ResourcePoint rp = (ResourcePoint)collidable;
					if(rp.getResource().getClass()==Wood.class) {
						nbrTrees--;
					}
				}else if(collidable.getClass()==House.class || collidable.getClass()==Stockpile.class || collidable.getClass()==Farm.class){
					nbrStructures--;
				}


			}
		}
		for (ITimeable timeable : timeables){
			if(timeable.toBeRemoved()){
				timeablestoberemoved.add(timeable);
			}
		}
		for (Character character : characters.values()){
			if(!character.isAlive()){
				characterstoberemoved.add(character);
			}
		}
	}

//-----------------------------------------ADD & REMOVE METHODS-------------------------------------------------------\\

	/**
	 * Adds a new character to the world at the specified position.
	 * @param xPoss the position on the x axis.
	 * @param yPoss the position on the y axis.
	 * @return the character that was just created.
	 */
	public Character addCharacter(double xPoss, double yPoss) {
		Character character = new Character(xPoss, yPoss);

		if(collidables.canAdd(character)) {
			this.collidablesR.add(character);
			this.collidables.add(character);
			this.timeables.add(character);
			this.characters.put(character.getKey(), character);
			nbrCharacters++;
			return character;
		}

		return null;
	}

	public Animal addAnimal(double xPoss, double yPoss, IResource resourceType, double territoryMinX, double territoryMinY, double territoryMaxX, double territoryMaxY) {
		Animal animal = new Animal(xPoss, yPoss, resourceType, territoryMinX, territoryMinY, territoryMaxX, territoryMaxY);

		if(collidables.canAdd(animal)) {
			this.collidablesR.add(animal);
			this.collidables.add(animal);
			this.timeables.add(animal);
			nbrAnimals++;
			return animal;
		}
		animal.remove();
		return null;

	}

	//TODO code this in a good way, this is not good.
	public IStructure addStructure(double xPoss, double yPoss, IStructure.StructureType type){
		IStructure structure = StructureFactory.createStructure(type, xPoss, yPoss);

		if (type.equals(IStructure.StructureType.FARM)) {
			if(collidables.canAdd(structure)) {
				this.collidables.add(structure);
				this.collidablesR.add(structure);
				this.statics.add(structure);
				this.timeables.add((ITimeable) structure);

				//update mask for pathfinding
				Constants.PATHFINDER_OBJECT.updateMask(this.statics);

				nbrStructures++;

				return structure;
			}
		}else if(type.equals(IStructure.StructureType.HOUSE)){
			ICollidable door = ((House)structure).getDoor();
			if(collidables.canAdd(structure) && collidables.canAdd(door)) {
				this.collidables.add(structure);
				this.collidablesR.add(structure);
				this.statics.add(structure);
				this.collidables.add(door);
				this.collidablesR.add(door);

				//update mask for pathfinding
				Constants.PATHFINDER_OBJECT.updateMask(this.statics);

				nbrStructures++;

				return structure;
			}
		}else{
			if(collidables.canAdd(structure)) {
				this.collidables.add(structure);
				this.collidablesR.add(structure);
				this.statics.add(structure);

				//update mask for pathfinding
				Constants.PATHFINDER_OBJECT.updateMask(this.statics);

				nbrStructures++;

				return structure;
			}
		}

		return null;
	}

	/**
	 * Adds a new finite resource point to the world at the specified position.
	 * @param resourceType the resource the point should contain.
	 * @param renderEnum the type of collidable the resource point is visually.
	 * @param xPoss the position on the x axis.
	 * @param yPoss the position on the y axis.
	 * @param radius the collision radius of the point.
	 * @return the resource point that was just added.
	 */
	public ResourcePoint addFiniteResourcePoint(FiniteResource resourceType, RenderObject.RENDER_OBJECT_ENUM renderEnum, double xPoss, double yPoss, double radius){
		ResourcePoint point = new ResourcePoint(resourceType, renderEnum, xPoss, yPoss, radius);

		if(collidables.canAdd(point)) {
			this.collidables.add(point);
			this.collidablesR.add(point);
			this.statics.add(point);

			//update mask for pathfinding
			//Constants.PATHFINDER_OBJECT.updateMask(this.statics);
			Constants.PATHFINDER_OBJECT.updateMask(point);

			return point;
		}

		return null;
	}

	/**
	 * Adds a new infinite resource point to the world at the specified position.
	 * @param resourceType the resource the point should contain.
	 * @param renderEnum the type of collidable the resource point is visually.
	 * @param xPoss the position on the x axis.
	 * @param yPoss the position on the y axis.
	 * @param radius the collision radius of the point.
	 * @return the resource point that was just added.
	 */
	public ResourcePoint addInfiniteResourcePoint(InfiniteResource resourceType, RenderObject.RENDER_OBJECT_ENUM renderEnum, double xPoss, double yPoss, double radius){
		ResourcePoint point = new ResourcePoint(resourceType, renderEnum, xPoss, yPoss, radius);

		if(collidables.canAdd(point)) {
			this.collidables.add(point);
			this.collidablesR.add(point);
			this.statics.add(point);

			//update mask for pathfinding
			//Constants.PATHFINDER_OBJECT.updateMask(this.statics);
			Constants.PATHFINDER_OBJECT.updateMask(point);

			return point;
		}

		return null;
	}


	/**
	 * Adds a new renewable resource point to the world at the specified position.
	 * @param resourceType the resource the point should contain.
	 * @param renderEnum the type of collidable the resource point is visually.
	 * @param xPoss the position on the x axis.
	 * @param yPoss the position on the y axis.
	 * @param radius the collision radius of the point.
	 * @return the resource point that was just added.
	 */
	public ResourcePoint addRenewableResourcePoint(RenewableResource resourceType, RenderObject.RENDER_OBJECT_ENUM renderEnum, double xPoss, double yPoss, double radius){
		ResourcePoint point = new ResourcePoint(resourceType, renderEnum, xPoss, yPoss, radius);

		if(collidables.canAdd(point)) {
			this.collidables.add(point);
			this.collidablesR.add(point);
			this.timeables.add(resourceType);
			this.statics.add(point);

			//update mask for pathfinding
			//Constants.PATHFINDER_OBJECT.updateMask(this.statics);
			Constants.PATHFINDER_OBJECT.updateMask(point);

			nbrTrees++;

			return point;
		}

		return null;
	}

	/**
	 * Removes all objects in the world that were staged for removal by 'checkObjectsForRemoval'.
	 */
	private void removeObjects() {
		for (ICollidable collidable : this.collidablestoberemoved) {
			collidables.remove(collidable);
			statics.remove(collidable);
		}
		collidablestoberemoved.clear();

		for (ITimeable timeable : this.timeablestoberemoved) {
			timeables.remove(timeable);
		}
		timeablestoberemoved.clear();

		for (Character character : this.characterstoberemoved) {
			characters.remove(character.getKey());
		}
		characterstoberemoved.clear();

		for (ICollidable collidable : this.collideablesrtoberemoved) {
			collidablesR.remove(collidable);
		}
		collideablesrtoberemoved.clear();

		//update mask for pathfinding
		Constants.PATHFINDER_OBJECT.updateMask(this.statics);
	}

//----------------------------------------------RENDER METHODS--------------------------------------------------------\\

	/**
	 * @return a list of all objects in the world, represented as RenderObjects.
	 */
	public RenderObject[] getRenderObjects() {

		int size = collidables.getSize();
		if (showingCurrentActivity) {size += characters.size();}
		RenderObject[] ret = new RenderObject[size];
		RenderObject[] temp = collidables.getRenderObjectsFromY();
		int i = 0;
		for (; i < collidables.getSize(); i++) {
			ret[i] = temp[i];
		}

		if (showingCurrentActivity) {
			for (Character c : characters.values()) {
				if (c.getCurrentActivityRenderObject() == null){
					ret[i] = new RenderObject(0, 0, 0, RenderObject.RENDER_OBJECT_ENUM.EMPTY);
				}else{
					ret[i] = c.getCurrentActivityRenderObject();
				}
				i++;
			}
		}

		return ret;

	}

	/**
	 * @return a list of all items in the player characters inventory, represented as InventoryRenders
	 */
	public LinkedList<InventoryRender> displayPlayerInventory() {
		if(characters.get(Constants.PLAYER_CHARACTER_KEY) != null) {
			return characters.get(Constants.PLAYER_CHARACTER_KEY).getRenderInventory();
		}else{
			return new LinkedList<>();
		}
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	public boolean isPaused(){ return pause; }

	/**
	 * @return the width of the world.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the height of the world.
	 */
	public double getHeight() {
		return height;
	}

	public LinkedList<Character> getCharacterList(){
		LinkedList<Character> charList = new LinkedList<>();
		Iterator<Character> it = characters.values().iterator();
		while(it.hasNext())
			charList.add(it.next());
		return charList;
	}

	public void setShowingCurrentActivity() {
		this.showingCurrentActivity = !showingCurrentActivity;
	}
}


