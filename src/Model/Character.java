package Model;

import Model.Structures.House;
import Model.Structures.Stockpile;
import Model.Tasks.*;
import Utility.Constants;
import Utility.InventoryRender;
import Utility.RenderObject;
import Utility.UniversalStaticMethods;
import org.lwjgl.Sys;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Character implements ICollidable, ITimeable, ICharacterHandle {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	//------------------Functionality-------------------\\
	private int updateCounter = 0;

	private static int nexKey = 1;
	private int key;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private Inventory inventory;

	private House home = null;

	private boolean waiting;

	private boolean spawning;
	private IStructure.StructureType typeToSpawn;

	private boolean pregnant;
	private Character father;
	private boolean labour;

	private Interaction.InteractionType currentInteraction;

	//--------------------Collision---------------------\\
	private double xPos;
	private double yPos;

	private double stepLength = Constants.CHARACTER_WALK_SPEED;

	private LinkedList<ICollidable> surroundingX;
	private LinkedList<ICollidable> surroundingY;
	private LinkedList<ICollidable> surroundings;

	private LinkedList<ICollidable> interactableX;
	private LinkedList<ICollidable> interactableY;
	private LinkedList<ICollidable> interactables;

	//-----------------NEEDS VARIABLES--------------------\\

	//TODO toBeRemoved after testing
	public boolean godMode = false;

	private boolean genderMale;
	private String name;
	private boolean alive;
	private int age;

	//---BASIC NEEDS---\\
	//Ranges between 0-100, 100 is good, 0 is bad..
	private int hunger;
	private int thirst;
	private int energy;

	//---SECONDARY NEEDS---\\
	//Ranges between 0-100, 100 is good, 0 is bad..
	private int social;
	private int intimacy;
	private int attention;

	//---PERSONALITY TRAITS---\\
	private int gluttony;		//Temperance(0)		- 		Gluttony(100) 		(Increases hunger decrease over time)
	private int sloth;			//Diligent(0)		-		Sloth(100)			(Increases energy decrease over time)
	private int lust;			//Virtues(0)		-		Lust(100)
	private int pride;			//Humility(0)		-		Pride(100)			(Decreases social increase from interaction)
	private int greed;			//Charity(0)		-		Greed(100)
	private int envy;			//Benevolence(0)	-		Envy(100)			(Increases surrounding range)
	private int wrath;			//Tranquility(0)	-		Wrath(100)			(Decreases the change in energy from attacks)

	//---UPDATE NEEDS BASED ON TRAITS---\\
	private final int hungerUpdate;
	private final int energyUpdate;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing a human in the world.
	 * @param xPos the position on the x-axis
	 * @param yPos the position on the y-axis
	 */
	public Character(double xPos, double yPos) {
		this(xPos,yPos,nexKey++);
	}
	/**
	 * A class representing a human in the world.
	 * @param xPos the position on the x-axis
	 * @param yPos the position on the y-axis
	 * @param key a unique value to identify this character
	 */
	public Character(double xPos, double yPos, int key){
		this.alive = true;
		this.age = 0;
		this.inventory = new Inventory();
		this.key = key;

		int genderRandom = (int)(Math.random()*10)+1;
		if(genderRandom>5) {
			genderMale = true;
			renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;
		}else {
			renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER2;
			genderMale = false;
		}

		this.name = randomizeName();

		this.spawning = false;
		this.pregnant = false;
		this.labour = false;

		this.waiting = false;

		//Initial position
		this.xPos = xPos;
		this.yPos = yPos;

		//Initialize needs
		this.hunger = Constants.CHARACTER_HUNGER_MAX;
		this.thirst = Constants.CHARACTER_THIRST_MAX;
		this.energy = Constants.CHARACTER_ENERGY_MAX;

		//Initialize secondary needs
		this.social = Constants.CHARACTER_SOCIAL_MAX;
		//this.intimacy = 100;
		//this.attention = 100;

		//Initialize collision detection lists
		this.surroundingX = new LinkedList<>();
		this.surroundingY = new LinkedList<>();
		this.surroundings = new LinkedList<>();

		this.interactableX = new LinkedList<>();
		this.interactableY = new LinkedList<>();
		this.interactables = new LinkedList<>();

		//Generates random personality traits
		int range = (Constants.MAX_TRAIT_VALUE-Constants.MIN_TRAIT_VALUE)+1;
		gluttony = (int)(Math.random()*range)+Constants.MIN_TRAIT_VALUE;
		sloth = (int)(Math.random()*range)+Constants.MIN_TRAIT_VALUE;
		lust = (int)(Math.random()*range)+Constants.MIN_TRAIT_VALUE;
		pride = (int)(Math.random()*range)+Constants.MIN_TRAIT_VALUE;
		greed = (int)(Math.random()*range)+Constants.MIN_TRAIT_VALUE;
		envy = (int)(Math.random()*range)+Constants.MIN_TRAIT_VALUE;
		wrath = (int)(Math.random()*range)+Constants.MIN_TRAIT_VALUE;



		hungerUpdate = (int)(Constants.CHARACTER_HUNGER_UPDATE - Constants.CHARACTER_HUNGER_UPDATE*gluttony*Constants.GLUTTONY_HUNGER_CHANGE_MODIFIER);
		energyUpdate = (int)(Constants.CHARACTER_ENERGY_UPDATE - Constants.CHARACTER_ENERGY_UPDATE*sloth*Constants.SLOTH_ENERGY_CHANGE_MODIFIER);

	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	/**
	 * @param key The key identifying this character.
	 */
	public void setKey(int key){
		if(key < 1 && key > nexKey) {
			this.key = key;
		}
	}

	/**
	 * @return the key identifying this character.
	 */
	public int getKey(){
		return key;
	}

	public String getName(){
		return name;
	}

	public int getAge(){
		return age;
	}

	public String randomizeName(){
		String name = "";
		try{
			FileInputStream fstream;
			int random_max;
			if(genderMale) {
				fstream = new FileInputStream("res/boy_names.txt");
				random_max = Constants.NUMBER_OF_MALE_NAMES;
			}else {
				fstream = new FileInputStream("res/girl_names.txt");
				random_max = Constants.NUMBER_OF_FEMALE_NAMES;
			}
			int randomNr = (int)(Math.random()*random_max)+1;
			DataInputStream din = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(din));
			PrintWriter writer = new PrintWriter("girl_names.txt", "UTF-8");

			String strLine;
			int counter = 1;
			while((strLine = br.readLine()) != null){
				if(counter==randomNr) {
					String[] delims = strLine.split(" ");
					name = delims[0];
					name = name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
					break;
				}
				counter++;
			}
			din.close();
			writer.close();
		}catch(Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return name;
	}

	/**
	 * @return the gender, true if man or false if female.
	 */
	public boolean getGenderMale(){
		return genderMale;
	}

	@Override
	/**{@inheritDoc}*/
	public double getX() {
		return this.xPos;
	}

	@Override
	/**{@inheritDoc}*/
	public double getY() {
		return this.yPos;
	}

	/**
	 * @return the distance moved by this character in one update.
	 */
	public double getSteplength(){
		return stepLength;
	}

	public boolean inventoryContains(IItem item){
		return this.inventory.contains(item);
	}


//---------------------------------------Collision Methods------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public double getCollisionRadius() {
		return Constants.CHARACTER_COLLISION_RADIUS;
	}

	@Override
	/**{@inheritDoc}*/
	public double getInteractionRadius(){
		return Constants.CHARACTER_INTERACTION_RADIUS;
	}

	@Override
	/**{@inheritDoc}*/
	public double getSurroundingRadius(){
		return Constants.CHARACTER_SURROUNDING_RADIUS*(1+envy/Constants.ENVY_SURROUNDING_RADIUS_MODIFYER);
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingX(ICollidable rhs) {
		this.surroundingX.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingY(ICollidable rhs) {
		this.surroundingY.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void checkSurroundings() {
		LinkedList<ICollidable> tmp = new LinkedList<>();
		for(ICollidable c : this.surroundingX){
			if(this.surroundingY.contains(c)){
				tmp.add(c);
			}
		}
		this.surroundingX.clear();
		this.surroundingY.clear();
		this.surroundings = tmp;
	}

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableX(ICollidable rhs) {
		this.interactableX.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableY(ICollidable rhs) {
		this.interactableY.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void checkInteractables() {
		LinkedList<ICollidable> tmp = new LinkedList<>();
		for(ICollidable c : this.interactableX){
			if(this.interactableY.contains(c)){
				tmp.add(c);
			}
		}
		this.interactableX.clear();
		this.interactableY.clear();
		this.interactables = tmp;
	}

//---------------------------------------Interaction methods----------------------------------------------------------\\

	public Interaction.InteractionType getInteractionType(){
		return currentInteraction;
	}

	public void setInteractionType(Interaction.InteractionType type){
		currentInteraction = type;
	}

	@Override
	/**{@inheritDoc}*/
	public void interacted(Character rhs){
		Interaction i = new Interaction(rhs, this, rhs.getInteractionType());
		currentInteraction = rhs.getInteractionType();
		pcs.firePropertyChange("startInteraction", i, rhs);
		rhs.startCharacterInteraction(this, i);
	}

	public  boolean reproduce(Character rhs){
		if(!this.genderMale && rhs.genderMale && !this.pregnant){
			this.pregnant = true;
			this.father = rhs;
			Schedule.addTask(new ReproduceTask(this,Constants.CHARACTER_PREGNANCY_TIME));//TODO remove magic number
			return true;
		}else if(!rhs.genderMale && this.genderMale && !rhs.pregnant){
			rhs.pregnant = true;
			rhs.father = rhs;
			Schedule.addTask(new ReproduceTask(rhs,Constants.CHARACTER_PREGNANCY_TIME));//TODO remove magic number
			return true;
		}
		return false;
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs){
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs){
		//TODO fire property change
		Schedule.addTask(new AttackTask(this, rhs, Constants.CHARACTER_ATTACKED_TIME));
	}

	@Override
	public void interactedCommand(Character rhs) {
		//Not used. Interaction instant.
	}

	public void reproduceCommand(){
		this.labour = true;
	}

	@Override
	public void consumedCommand(Character rhs) {
		//TODO implement
	}

	@Override
	public void attackedCommand(Character rhs) {
		this.changeEnergy(Constants.CHARACTER_ATTACKED_ENERGY_CHANGE-(int)(Constants.WRATH_ENERGY_ATTACK_MODIFIER*this.wrath));
	}

	@Override
	public void interactedInterrupted(Character rhs) {
		//TODO implement
	}

	@Override
	public void consumedInterrupted(Character rhs) {
		//TODO implement
	}

	@Override
	public void attackedInterrupted(Character rhs) {
		//TODO implement
	}

	private void startCharacterInteraction(Character rhs, Interaction i){
		pcs.firePropertyChange("startInteraction", i, rhs);
	}

	public void startStockpileInteraction(Stockpile rhs, StockpileInteraction i){
		pcs.firePropertyChange("startStockpileInteraction", i, rhs);
	}

	/**
	 * Adds the specified amount of the specified item to this characters inventory.
	 * @param item The item to be added.
	 */
	public void addToInventory(IItem item){
			inventory.addItem(item);
	}

	/**
	 * Removes the specified amount of the specified item from this characters inventory.
	 * @param item The item to be removed.
	 */
	public void removeFromInventory(IItem item){
		inventory.removeItem(item);
	}

	/**
	 * Changes the Hunger level of this character by the specified amount.
	 * If this change would set the hunger level to higher than the maximum, it is set to the maximum instead.
	 * @param change the desired change in hunger.
	 */
	public void changeHunger(int change) {
		if (hunger + change >= Constants.CHARACTER_HUNGER_MAX) {
			hunger = Constants.CHARACTER_HUNGER_MAX;
		} else if (hunger + change <= 0){
			hunger = 0;
		}else{
			this.hunger = hunger + change;
		}

	}

	/**
	 * Changes the Thirst level of this character by the specified amount.
	 * If this change would set the thirst level to higher than the maximum, it is set to the maximum instead.
	 * @param change the desired change in thirst.
	 */
	public void changeThirst(int change){
		if(thirst+change >= Constants.CHARACTER_THIRST_MAX){
			thirst = Constants.CHARACTER_THIRST_MAX;
		} else if (thirst + change <= 0){
			thirst = 0;
		}else{
			this.thirst = thirst + change;
		}
	}

	/**
	 * Changes the Energy level of this character by the specified amount.
	 * If this change would set the energy level to higher than the maximum, it is set to the maximum instead.
	 * @param change the desired change in energy.
	 */
	public void changeEnergy(int change){
		if(energy+change >= Constants.CHARACTER_ENERGY_MAX){
			energy = Constants.CHARACTER_ENERGY_MAX;
		} else if (energy + change <= 0){
			energy = 0;
		}else{
			this.energy = energy + change;
		}
	}

	public void changeSocial(int change){
		if(social+change >= Constants.CHARACTER_SOCIAL_MAX){
			social = Constants.CHARACTER_SOCIAL_MAX;
		} else if (social + change <= 0){
			social = 0;
		}else{
			this.social = social + change*(1-pride/Constants.PRIDE_INTERACT_SOCIAL_MODIFIER);
		}
	}

	public void enterHouse(House house){
		this.xPos = house.getX();
		this.yPos = house.getY()-1;//-1 for rendering order... (instead of using a third dimension)
	}

	public void exitHouse(House house){
		this.xPos = house.getDoorPositionX();
		this.yPos = house.getDoorPositionY();
	}

//------------------------------------------UPDATE METHODS------------------------------------------------------------\\

	/**
	 * Updates the variable noting if the character is alive or not.
	 */
	private void updateAlive() {
		if (hunger <= 0 || thirst <= 0 || energy <= 0) {
			alive = false;
		}
	}

	@Override
	/**{@inheritDoc}*/
	public boolean toBeRemoved(){
		return !isAlive();
	}

	@Override
	public boolean isSpawning() {
		return spawning;
	}

	public boolean isPregnant(){
		return pregnant;
	}

	public boolean isInLabour(){
		return labour;
	}

	public void birth(World world){
		this.labour = false;
		this.pregnant = false;
		world.addCharacter(this.xPos,this.yPos);
	}

	@Override
	/**{@inheritDoc}*/
	public boolean isImovable(){
		return false;
	}

	/*private class Node{
		private final Point p;

	}*/

	@Override
	public void spawn(World rhs) {
		LinkedList<IItem> cost = StructureFactory.getCost(typeToSpawn);
		boolean canPay = true;
		for(IItem itemCost : cost){
			if(!inventory.contains(itemCost)){
				canPay = false;
				break;
			}
		}

		if(canPay) {
			Schedule.addTask(new BuildTask(this,cost,rhs,StructureFactory.getWaitTime(typeToSpawn)));
		}

		spawning = false;
	}

	public void build(LinkedList<IItem> cost, World rhs){

		IStructure structure = null;
		for(IItem itemCost : cost){
			inventory.removeItem(itemCost);
		}
		int collision = 0;

		switch (typeToSpawn){
			case FARM:
				collision = (int) Constants.FARM_COLLISION_RADIUS;
				break;
			case HOUSE:
				collision = (int) Constants.HOUSE_COLLISION_RADIUS;
				break;
			case STOCKPILE:
				collision = (int) Constants.STOCKPILE_COLLISION_RADIUS;
				break;
		}
		//structure = rhs.addStructure(xPos, ()(yPos-Constants.CHARACTER_INTERACTION_RADIUS), typeToSpawn);
		//structure = rhs.addStructure(xPos, yPos-collision, typeToSpawn);

		int tempX = (int)xPos / Constants.PATHFINDER_GRID_SIZE;
		int tempY = (int)yPos / Constants.PATHFINDER_GRID_SIZE;

		Queue<Point> queue = new LinkedList<>();

		int tempPositiveX = tempX + collision / Constants.PATHFINDER_GRID_SIZE + 1;
		int tempNegativeX = tempX - collision / Constants.PATHFINDER_GRID_SIZE - 1;
		int tempPositiveY = tempY + collision / Constants.PATHFINDER_GRID_SIZE + 1;
		int tempNegativeY = tempY - collision / Constants.PATHFINDER_GRID_SIZE - 1;

		queue.offer(new Point(tempPositiveX, tempPositiveY));
		queue.offer(new Point(tempPositiveX, tempNegativeY));
		queue.offer(new Point(tempNegativeX, tempPositiveY));
		queue.offer(new Point(tempNegativeX, tempNegativeY));

		Point tempPoint = queue.poll();
		tempX = tempPoint.x;
		tempY = tempPoint.y;

		while((structure = rhs.addStructure(tempX * Constants.PATHFINDER_GRID_SIZE, tempY * Constants.PATHFINDER_GRID_SIZE, typeToSpawn)) == null) {
			Point p = new Point(tempPositiveX, tempPositiveY);
			if(Math.abs(xPos / Constants.PATHFINDER_GRID_SIZE - p.getX()) > collision / Constants.PATHFINDER_GRID_SIZE + 1 && Math.abs(yPos / Constants.PATHFINDER_GRID_SIZE - p.getY()) > collision / Constants.PATHFINDER_GRID_SIZE + 1){
				queue.offer(p);
			}
			p = new Point(tempPositiveX, tempNegativeY);
			if(Math.abs(xPos / Constants.PATHFINDER_GRID_SIZE - p.getX()) > collision / Constants.PATHFINDER_GRID_SIZE + 1 && Math.abs(yPos / Constants.PATHFINDER_GRID_SIZE - p.getY()) > collision / Constants.PATHFINDER_GRID_SIZE + 1){
				queue.offer(p);
			}
			p = new Point(tempNegativeX, tempPositiveY);
			if(Math.abs(xPos / Constants.PATHFINDER_GRID_SIZE - p.getX()) > collision / Constants.PATHFINDER_GRID_SIZE + 1 && Math.abs(yPos / Constants.PATHFINDER_GRID_SIZE - p.getY()) > collision / Constants.PATHFINDER_GRID_SIZE + 1){
				queue.offer(p);
			}
			p = new Point(tempNegativeX, tempNegativeY);
			if(Math.abs(xPos / Constants.PATHFINDER_GRID_SIZE - p.getX()) > collision / Constants.PATHFINDER_GRID_SIZE + 1 && Math.abs(yPos / Constants.PATHFINDER_GRID_SIZE - p.getY()) > collision / Constants.PATHFINDER_GRID_SIZE + 1){
				queue.offer(p);
			}

			tempPositiveX++;
			tempNegativeX--;
			tempPositiveY++;
			tempNegativeY--;

			tempPoint = queue.poll();
			tempX = tempPoint.x;
			tempY = tempPoint.y;
		}

		if(structure.getClass().equals(House.class) && home == null){
			home = (House) structure;
			home.addOccupant();
		}
	}

	/**
	 * Checks if the character is alive.
	 * @return true if the character is alive, else false.
	 */
	public boolean isAlive() {
		return alive||godMode;
	}

	@Override
	/**{@inheritDoc}*/
	public void updateTimeable() {
		//Updates counter with one but doesn't exceed 60.
		/*updateCounter = (updateCounter+1) % (Constants.CHARACTER_UPDATE_INTERVAL+1);
		if(updateCounter == 0)
			updateCounter++;*/
		updateCounter++;
//		if(waitingFrames>0){
//			waitingFrames--;
//		}else{
//			waiting = false;
//		}

		if(updateCounter % hungerUpdate == 0){
			changeHunger(-Constants.CHARACTER_HUNGER_CHANGE + (social/Constants.CHARACTER_SOCIAL_NEEDS_MODIFIER));
		}
		if(updateCounter % energyUpdate == 0){
			changeEnergy(-Constants.CHARACTER_ENERGY_CHANGE + (social/Constants.CHARACTER_SOCIAL_NEEDS_MODIFIER));
		}
		if(updateCounter % Constants.CHARACTER_THIRST_UPDATE == 0){
			changeThirst(-Constants.CHARACTER_THIRST_CHANGE + (social/Constants.CHARACTER_SOCIAL_NEEDS_MODIFIER));
		}
		if(updateCounter % Constants.CHARACTER_SOCIAL_UPDATE == 0){
			changeSocial(-Constants.CHARACTER_SOCIAL_CHANGE);
		}
		if(updateCounter % Constants.CHARACTER_AGE_UPDATE == 0){
			age++;
			if(age >= Constants.CHARACTER_MIN_DEATH_AGE){
				int kill = (int)(Math.random()*(Constants.CHARACTER_DEATH_AGE_SPANN) + Constants.CHARACTER_MIN_DEATH_AGE);
				if(age >= kill){
					this.alive = false;
				}
			}
		}

		updateAlive();
	}

//------------------------------------------------RENDER METHODS------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public RenderObject getRenderObject() {
		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}

	@Override
	/**{@inheritDoc}*/
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return renderObjectEnum;
	}

	/**
	 * Returns a list containing all information necessary to view this characters inventory.
	 * @return A list of InventoryRender, one for every item in the inventory.
	 */
	public LinkedList<InventoryRender> getRenderInventory(){
		LinkedList<InventoryRender> list = new LinkedList<>();

		for(IItem item : inventory.getItems()){
			InventoryRender tmp = new InventoryRender();
			tmp.amount=item.getAmount();
			tmp.type=item.getType();
			list.add(tmp);
		}

		return list;
	}

//--------------------------------------ICharacterHandle methods------------------------------------------------------\\

	//TODO implement, change type....
	@Override
	/**{@inheritDoc}*/
	public int[] getNeeds() {
		return new int[]{hunger, thirst, energy};
	}

	public int[] getSecondaryNeeds() {
		return new int[]{social, intimacy, attention};
	}

	//TODO implement, change type....
	@Override
	/**{@inheritDoc}*/
	public int[] getSkills() {
		return new int[]{};
	}

	//TODO implement, change type....
	@Override
	/**{@inheritDoc}*/
	public int[] getTraits() {
		return new int[]{gluttony, sloth, lust, pride, greed, envy, wrath};
	}

	@Override
	/**{@inheritDoc}*/
	public void moveUp() {
		pcs.firePropertyChange("",0,1);
		this.yPos -= this.stepLength;
	}

	@Override
	/**{@inheritDoc}*/
	public void moveDown() {
		pcs.firePropertyChange("",0,1);
		this.yPos += this.stepLength;
	}

	@Override
	/**{@inheritDoc}*/
	public void moveLeft() {
		pcs.firePropertyChange("",0,1);
		this.xPos -= this.stepLength;
	}

	@Override
	/**{@inheritDoc}*/
	public void moveRight() {
		pcs.firePropertyChange("",0,1);
		this.xPos += this.stepLength;
	}

	@Override
	/**{@inheritDoc}*/
	public List<ICollidable> getSurroundings() {
		return this.surroundings;
	}

	@Override
	/**{@inheritDoc}*/
	public List<ICollidable> getInteractables() {
		return this.interactables;
	}

	@Override
	/**{@inheritDoc}*/
	public List<IItem> getInventory(){
		return inventory.getItems();
	}

	//TODO implement
	@Override
	/**{@inheritDoc}*/
	public Outcome getOutcomeInventory(int inventoryIndex) {
		for(IItem item : inventory.getItems()){
			//list.add(i.getOutcome());
		}

		return new Outcome();
	}

	//TODO implement
	@Override
	/**{@inheritDoc}*/
	public Outcome getOutcomeInteractables(int interactablesIndex) {
		return null;
	}

	@Override
	/**{@inheritDoc}*/
	public void startRunning(){
		stepLength = Constants.CHARACTER_RUN_SPEED;
	}

	@Override
	/**{@inheritDoc}*/
	public void stopRunning(){
		stepLength = Constants.CHARACTER_WALK_SPEED;
	}

	@Override
	/**{@inheritDoc}*/
	public void interactObject(int index){
		pcs.firePropertyChange("",0,1);
		if(this.interactables.size()>index){
			this.interactables.get(index).interacted(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void attackObject(int index){
		pcs.firePropertyChange("",0,1);
		if(this.interactables.size() > index){
			this.interactables.get(index).attacked(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void consumeObject(int index){
		pcs.firePropertyChange("",0,1);
		if(this.interactables.size() > index){
			this.interactables.get(index).consumed(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void interactItem(int index){
		pcs.firePropertyChange("",0,1);
		if(this.getInventory().size()>index){
			this.getInventory().get(index).interacted(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void attackItem(int index){
		pcs.firePropertyChange("",0,1);
		if(this.getInventory().size()>index){
			this.getInventory().get(index).attacked(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void consumeItem(int index){
		pcs.firePropertyChange("", 0, 1);
		if(this.getInventory().size()>index){
			this.getInventory().get(index).consumed(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	/**{@inheritDoc}*/
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}

	@Override
	/**{@inheritDoc}*/
	public void sleep() {
		pcs.firePropertyChange("",0,1);
		this.energy = 100;
	}

	@Override
	/**{@inheritDoc}*/
	public void build(IStructure.StructureType type){
		pcs.firePropertyChange("",0,1);
		this.typeToSpawn = type;
		this.spawning = true;
	}

	public void setWaiting(boolean w){
		waiting = w;
	}

	public boolean isWaiting(){
		return waiting;
	}


	@Override
	public boolean hasHome() {
		return home != null;
	}

	@Override
	public ICollidable getHome() {
		return home;
	}
}
