package Model;

import org.lwjgl.Sys;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Created by Martin on 23/02/2016.
 */
public class World{
	//TODO-------------------------------????-------------------------------------------------------------------------\\
//TODO REMOVE
//	public enum GAMESPEED {
//		NORMAL(1), FAST(1.5), FASTER(2);
//
//		private final double gameSpeed;
//
//		GAMESPEED(double gameSpeed) {
//			this.gameSpeed = gameSpeed;
//		}
//
//		public double getGameSpeed() {
//			return gameSpeed;
//		}
//	}
//
//	private static double gameSpeed;
//	public static double getGameSpeed() {
//		return gameSpeed;
//	}
//	public static void setGameSpeed(double gs) {
//		gameSpeed = gs;
//	}

//TODO REMOVE test method.
//	public void hit() {
//		this.characters.get(1).hit();
//	}

	//TODO-------------------------------END ????---------------------------------------------------------------------\\

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	//------------------Functionality-------------------\\
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private boolean pause;

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

	public World (double width, double height){
		//Initializing world characteristics
		this.width = width;
		this.height = height;

		//Initializing lists for objects in world.
		this.collidables = new CollisionList();
		this.collidablesR = new LinkedList<>();
		this.timeables = new LinkedList<>();
		this.characters = new HashMap<>();
		this.statics = new CollisionList();

		//Initializing removal lists
		collidablestoberemoved = new LinkedList<>();
		collideablesrtoberemoved = new LinkedList<>();
		timeablestoberemoved = new LinkedList<>();
		characterstoberemoved = new LinkedList<>();

		//Initializing mask for pathfinding
		Constants.PATHFINDER_OBJECT.updateMask(this.statics);

	}

	public World (double width, double height, int nrTrees, int nrLakes, int nrStones, int nrCrops){

		this(width, height);

		Wood tw1 = new Wood(1000,1000,1);
		ResourcePoint trp1 = new ResourcePoint(tw1, RenderObject.RENDER_OBJECT_ENUM.WOOD,1050,1050,50);
		this.collidables.add(trp1);
		this.collidablesR.add(trp1);

		int i = 0;
		float tmpX;
		float tmpY;
		while(i < nrTrees){
			tmpX = (float)(Math.random()*this.width/20);
			tmpY = (float)(Math.random()*this.height/20);

			Wood tmpWood = new Wood(10,10,1);
			ResourcePoint tmpPoint = new ResourcePoint(tmpWood, RenderObject.RENDER_OBJECT_ENUM.WOOD,tmpX,tmpY,10);

			this.collidables.add(tmpPoint);
			this.collidablesR.add(tmpPoint);
			this.timeables.add(tmpWood);
			this.statics.add(tmpPoint);

			i++;
		}
		i = 0;
		while(i < nrLakes){
			tmpX = (float)(Math.random()*this.width);
			tmpY = (float)(Math.random()*this.height);

			Water tmpLake = new Water(1);
			ResourcePoint tmpPoint = new ResourcePoint(tmpLake, RenderObject.RENDER_OBJECT_ENUM.LAKE,tmpX,tmpY,100);

			this.collidables.add(tmpPoint);
			this.collidablesR.add(tmpPoint);
			this.statics.add(tmpPoint);

			i++;
		}
		i = 0;
		while(i < nrStones){
			tmpX = (float)(Math.random()*this.width);
			tmpY = (float)(Math.random()*this.height);

			Stone tmpStone = new Stone(50,5);
			ResourcePoint tmpPoint = new ResourcePoint(tmpStone, RenderObject.RENDER_OBJECT_ENUM.STONE,tmpX,tmpY,10);

			this.collidables.add(tmpPoint);
			this.collidablesR.add(tmpPoint);
			this.statics.add(tmpPoint);

			i++;
		}
		i = 0;
		while(i < nrCrops){
			tmpX = (float)(Math.random()*this.width);
			tmpY = (float)(Math.random()*this.height);

			Crops tmpCrops = new Crops(100,5);
			ResourcePoint tmpPoint = new ResourcePoint(tmpCrops, RenderObject.RENDER_OBJECT_ENUM.CROPS,tmpX,tmpY,20);

			this.collidables.add(tmpPoint);
			this.collidablesR.add(tmpPoint);
			this.statics.add(tmpPoint);

			i++;
		}
		
		//update mask for pathfinding
		Constants.PATHFINDER_OBJECT.updateMask(this.statics);

	}

//---------------------------------------------UPDATE METHODS---------------------------------------------------------\\

	public void uppdate() {
		if (!pause) {
			updateTimeables();

			checkObjectsForRemoval();

			removeObjects();

			this.collidables.handleCollision();

			//TODO Code for updating the character (movement and actions?)

		}
	}

	//TODO Add parameter for fast-forward
	private void updateTimeables(){
		for (ITimeable timedObj : timeables) {
			timedObj.updateTimeable();
		}
	}

	private void checkObjectsForRemoval(){
		for (ICollidable collidable : collidablesR) {//Loop on collidablesR as it supportes for-each
			if (collidable.toBeRemoved()) {
				collidablestoberemoved.add(collidable);
				collideablesrtoberemoved.add(collidable);
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

	// Pause the game, if P is pressed, pause() will pause the uppdate lopp
	public void togglePause() {
		if (!pause) {
			pause = true;
		} else {
			pause = false;
		}
	}

//-----------------------------------------ADD & REMOVE METHODS-------------------------------------------------------\\

	//TODO check if place is available.
	public Character addCharacter(float xPoss, float yPoss, int key) {
		Character character = new Character(xPoss, yPoss, key);

		this.collidablesR.add(character);
		this.collidables.add(character);
		this.timeables.add(character);
		this.characters.put(key, character);

		pcs.firePropertyChange("createdCharacter", null, character);

		return character;
	}

	//TODO implement properly---------------------------------
				public ResourcePoint addFiniteResourcePoint(FiniteResource resourceType, float xPoss, float yPoss, double radius){
					ResourcePoint point = new ResourcePoint(resourceType, RenderObject.RENDER_OBJECT_ENUM.CHARACTER, xPoss, yPoss, radius);
					this.collidables.add(point);
					this.collidablesR.add(point);
					return point;
				}

				public ResourcePoint addInfiniteResourcePoint(InfiniteResource resourceType, float xPoss, float yPoss, double radius){
					ResourcePoint point = new ResourcePoint(resourceType, RenderObject.RENDER_OBJECT_ENUM.CHARACTER, xPoss, yPoss, radius);
					this.collidables.add(point);
					this.collidablesR.add(point);
					return point;
				}

				public ResourcePoint addRenewableResourcePoint(RenewableResource resourceType, float xPoss, float yPoss, double radius){
					ResourcePoint point = new ResourcePoint(resourceType, RenderObject.RENDER_OBJECT_ENUM.CHARACTER, xPoss, yPoss, radius);
					this.collidables.add(point);
					this.collidablesR.add(point);
					this.timeables.add(resourceType);
					return point;
	}

	public void removeObjects() {
		for (ICollidable collidable : this.collidablestoberemoved) {
			collidables.remove(collidable);
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
	}

//----------------------------------------------RENDER METHODS--------------------------------------------------------\\

	public RenderObject[] getRenderObjects() {
		RenderObject[] renderObjects = new RenderObject[collidables.getSize()];

		for (int i = 0; i < collidablesR.size(); i++) {
			renderObjects[i] = collidablesR.get(i).getRenderObject();
		}
		return renderObjects;
	}

	//TODO better MVC praxis
	public LinkedList<InventoryRender> displayPlayerInventory() {
		if(characters.get(Constants.PLAYER_CHARACTER_KEY) != null) {
			return characters.get(Constants.PLAYER_CHARACTER_KEY).getRenderInventory();
		}else{
			return new LinkedList<>();
		}
	}


//------------------------------------------------PCS METHODS---------------------------------------------------------\\

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

}


