package Model;

import Toolkit.InventoryRender;
import Toolkit.RenderObject;

import java.util.*;
import java.util.List;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Character implements ICollidable, ITimeable, ICharacterHandle {
	//TODO-------------------------------????-------------------------------------------------------------------------\\

	/*TODO REMOVE depricated methods
	//---------NEED REPLENESHING METHODS--------------
	public void eat() {
		this.hunger += 25;
	}
	public void drink() {
		this.thirst += 10;
	}
	public void sleep() {
		this.energy = 100;
	}
	public void rest() {
		this.energy += 20;
	}*/

//	TODO REMOVE, after checking why they existed in the first place
//		/*
//		Method for checking where the character wants to move
//		 */
//		public double getNextXPosition(){
//			return this.xPos+this.xSpeed;
//		}
//
//
//		/*
//		Method for checking where the character wants to move
//		 */
//		public double getNextYPosition(){
//			return this.yPos+this.ySpeed;
//		}
	//---------------TRAITS VARIABLES--------------------

//	public enum TRAITS_ENUM{
//		HUNGER, THIRST, ENERGY
//	}

//	public enum GENDER_ENUM {
//		MAN("man"), WOMAN("woman");
//		private  String gender;
//
//		GENDER_ENUM (String g) {gender = g; }
//
//		public String getGender(){
//			return gender;
//		}
//	}
//	private String gender;


	//TESTING
//	private Pathfinder pathTest;
//	private LinkedList<PathStep> stepTest;


//	public enum NEEDS_ENUM{
//		HUNGER, THIRST, ENERGY
//	}


	//generates a gender for the character.
//	public void generateGender() {
//		if(Math.random()<= 0.5) {
//			this.gender = GENDER_ENUM.MAN.getGender();
//		} else {
//			this.gender = GENDER_ENUM.WOMAN.getGender();
//		}
//	}


	//---SECONDARY NEEDS---\\
	//Ranges between 0-100, 100 is good, 0 is bad..
//	private int social;
//	private int intimacy;
//	private int attention;

	//TODO-------------------------------END ????---------------------------------------------------------------------\\

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	//------------------Functionality-------------------\\
	private int updateCounter = 0;

	private int key;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;

	private Inventory inventory;

	//--------------------Collision---------------------\\
	private float xPos;
	private float yPos;

	private float stepLength = Constants.CHARACTER_WALK_SPEED;

	private LinkedList<ICollidable> surroundingX;
	private LinkedList<ICollidable> surroundingY;
	private LinkedList<ICollidable> surroundings;

	private LinkedList<ICollidable> interactableX;
	private LinkedList<ICollidable> interactableY;
	private LinkedList<ICollidable> interactables;

	//-----------------NEEDS VARIABLES--------------------\\

	//TODO toBeRemoved after testing
	public boolean godMode = false;

	private boolean alive;
	private int age;

	//---BASIC NEEDS---\\
	//Ranges between 0-100, 100 is good, 0 is bad..
	private int hunger;
	private int thirst;
	private int energy;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing a human in the world.
	 * @param xPos the position on the x-axis
	 * @param yPos the position on the y-axis
	 * @param key a unique value to identify this character
	 */
	public Character(float xPos, float yPos, int key){
		this.alive = true;
		this.age = 0;
		this.inventory = new Inventory();
		this.key = key;

		//Initial position
		this.xPos = xPos;
		this.yPos = yPos;

		//Initialize needs
		this.hunger = Constants.CHARACTER_HUNGER_MAX;
		this.thirst = Constants.CHARACTER_THIRST_MAX;
		this.energy = Constants.CHARACTER_ENERGY_MAX;

		//Initialize collision detection lists
		this.surroundingX = new LinkedList<>();
		this.surroundingY = new LinkedList<>();
		this.surroundings = new LinkedList<>();

		this.interactableX = new LinkedList<>();
		this.interactableY = new LinkedList<>();
		this.interactables = new LinkedList<>();

		//TODO check if this should be removed
		//this.pathTest = new Pathfinder(16, 9600, 9600, 1, 1.4);
		//this.pathTest.updateMask(new CollisionList());
		//this.stepTest = null;

		//TODO check if this should be removed
		//Generate Gender
		//generateGender();

	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	/**
	 * @return the key identifying this character.
	 */
	public int getKey(){
		return key;
	}

	@Override
	/**{@inheritDoc}*/
	public float getX() {
		return this.xPos;
	}

	@Override
	/**{@inheritDoc}*/
	public float getY() {
		return this.yPos;
	}

	/**
	 * @return the distance moved by this character in one update.
	 */
	public float getSteplength(){
		return stepLength;
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
		return Constants.CHARACTER_SURROUNDING_RADIUS;
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

	@Override
	/**{@inheritDoc}*/
	public void interacted(Character rhs){
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs){
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs){
		//TODO implement
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
	public void changeHunger(int change){
		if(hunger+change >= Constants.CHARACTER_HUNGER_MAX){
			hunger = Constants.CHARACTER_HUNGER_MAX;
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
		}else{
			this.energy = energy + change;
		}
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
		updateCounter = (updateCounter+1) % Constants.CHARACTER_UPDATE_INTERVAL;
		if(updateCounter % Constants.CHARACTER_HUNGER_UPDATE == 0){
			changeHunger(-Constants.CHARACTER_HUNGER_CHANGE);
		}
		if(updateCounter % Constants.CHARACTER_ENERGY_UPDATE == 0){
			changeEnergy(-Constants.CHARACTER_ENERGY_CHANGE);
		}
		if(updateCounter % Constants.CHARACTER_THIRST_UPDATE == 0){
			changeThirst(-Constants.CHARACTER_THIRST_CHANGE);
		}
		if(updateCounter % Constants.CHARACTER_AGE_UPDATE == 0){
			age++;
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

	//TODO implement, change type....
	@Override
	/**{@inheritDoc}*/
	public int[] getSkills() {
		return new int[0];
	}

	//TODO implement, change type....
	@Override
	/**{@inheritDoc}*/
	public int[] getTraits() {
		return new int[0];
	}

	@Override
	/**{@inheritDoc}*/
	public void moveUp() {
		this.yPos -= this.stepLength;
	}

	@Override
	/**{@inheritDoc}*/
	public void moveDown() {
		this.yPos += this.stepLength;
	}

	@Override
	/**{@inheritDoc}*/
	public void moveLeft() {
		this.xPos -= this.stepLength;
	}

	@Override
	/**{@inheritDoc}*/
	public void moveRight() {
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
		if(this.interactables.size()>index){
			this.interactables.get(index).interacted(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void attackObject(int index){
		if(this.interactables.size()>index){
			this.interactables.get(index).attacked(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void consumeObject(int index){
		if(this.interactables.size()>index){
			this.interactables.get(index).consumed(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void interactItem(int index){
		if(this.getInventory().size()>index){
			this.getInventory().get(index).interacted(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void attackItem(int index){
		if(this.getInventory().size()>index){
			this.getInventory().get(index).attacked(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void consumeItem(int index){
		if(this.getInventory().size()>index){
			this.getInventory().get(index).consumed(this);
			if(this.getInventory().get(index).getAmount() <= 0){
				this.getInventory().remove(index);
			}
		}
	}

}
