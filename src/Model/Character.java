package Model;

import Controller.Pathfinder;
import Controller.PathStep;

import java.util.*;
import java.util.List;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Character implements ICollidable, ITimeable, ICharacterHandle {
	//------------------Movement Variables---------------------
	private float xPos;
	private float yPos;

	//private float xSpeed;//TODO remove
	//private float ySpeed;//TODO remove

	private int updateCounter = 0;

		//TODO Update the way the player moves the character to be the same way as the AI.
		//Tells the update function for each character if they are currently walking in any direction;
		private boolean walkingUp = false;
		private boolean walkingDown = false;
		private boolean walkingLeft = false;
		private boolean walkingRight = false;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;


	private Inventory inventory;

	//----------------Collision------------------
	private double collisionRadius;//TODO make float
	private double interactionRadius;//TODO make float
	private double surroundingRadius;//TODO make float

	private LinkedList<ICollidable> surroundingX;
	private LinkedList<ICollidable> surroundingY;
	private LinkedList<ICollidable> surroundings;

	private LinkedList<ICollidable> interactableX;
	private LinkedList<ICollidable> interactableY;
	private LinkedList<ICollidable> interactables;


	//TESTING
	private Pathfinder pathTest;
	private LinkedList<PathStep> stepTest;

	//---------------NEEDS VARIABLES--------------------

	public enum NEEDS_ENUM{
		HUNGER, THIRST, ENERGY
	}

	private boolean alive;

	//------------BASIC NEEDS--------------------
	//Ranges between 0-100, 100 is good, 0 is bad..
	private int hunger;
	private int thirst;
	private int energy;

	//-------------SECONDARY NEEDS-------------------
	//Ranges between 0-100, 100 is good, 0 is bad..
	private int social;
	private int intimacy;
	private int attention;

	//---------------TRAITS VARIABLES--------------------

	public enum TRAITS_ENUM{
		HUNGER, THIRST, ENERGY
	}

	public enum GENDER_ENUM {
		MAN("man"), WOMAN("woman");
		private  String gender;

		GENDER_ENUM (String g) {gender = g; }

		public String getGender(){
			return gender;
		}
	}
	private int age;
	private String gender;

	private float stepLength = Constants.CHARACTER_WALK_SPEED;

	private int key;


	public Character(float xPos, float yPos, int key){
		this.alive = true;
		//Initial position
		this.xPos = xPos;
		this.yPos = yPos;
		//this.xSpeed = 0;//TODO remove
		//this.ySpeed = 0;//TODO remove
		this.key = key;
		//Generate Gender
		generateGender();

		//Create inventory
		inventory = new Inventory();

		this.hunger = 100;//TODO add to constants
		this.thirst = 100;//TODO add to constants
		this.energy = 100;//TODO add to constants

		this.pathTest = new Pathfinder(16, 9600, 9600, 1, 1.4);
		this.pathTest.updateMask(new CollisionList());
		this.stepTest = null;

		this.surroundingX = new LinkedList<>();
		this.surroundingY = new LinkedList<>();
		this.surroundings = new LinkedList<>();

		this.interactableX = new LinkedList<>();
		this.interactableY = new LinkedList<>();
		this.interactables = new LinkedList<>();

		this.collisionRadius = 5;//TODO add to constants
		this.interactionRadius = 10;//TODO add to constants
		this.surroundingRadius = 20;//TODO add to constants
	}

	//generates a gender for the character.
	public void generateGender() {
		if(Math.random()<= 0.5) {
			this.gender = GENDER_ENUM.MAN.getGender();
		} else {
			this.gender = GENDER_ENUM.WOMAN.getGender();
		}
	}

	@Override
	public float getX() {
		return this.xPos;
	}

	@Override
	public float getY() {
		return this.yPos;
	}

	public int getKey(){ return key;}

	public float getSteplength(){return stepLength;}

	//----------------------------Collision --------------------------------\\

	@Override
	public double getCollisionRadius() {
		return this.collisionRadius;
	}

	@Override
	public double getInteractionRadius(){
		return this.interactionRadius;
	}

	@Override
	public double getSurroundingRadius(){
		return this.surroundingRadius;
	}

	public void setInteractionRadius(double radius){
		this.interactionRadius = radius;
	}

	@Override
	public void addToSurroundingX(ICollidable rhs) {
		this.surroundingX.add(rhs);
	}

	@Override
	public void addToSurroundingY(ICollidable rhs) {
		this.surroundingY.add(rhs);
	}

	@Override
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
	public void addToInteractableX(ICollidable rhs) {
		this.interactableX.add(rhs);
	}

	@Override
	public void addToInteractableY(ICollidable rhs) {
		this.interactableY.add(rhs);
	}

	@Override
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


	//-------------------


	@Override
	public RenderObject getRenderObject() {
		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}

	@Override
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return renderObjectEnum;
	}

	//Check if character is alive
	private void updateAlive() {
		if (hunger <= 0 || thirst <= 0 || energy <= 0) {
			alive = false;
		}
	}

	//Getter for alive
	public boolean isAlive() {
		return alive;
	}

	//TODO--------------------------------------[START of section needing major fixing]-----------------------------------
							@Override
							public void updateTimeable() {

								//TODO Update needs
								//TODO Implement ageing etc...

								//Updates counter with one but doesn't exceed 60.
								updateCounter = (updateCounter+1) % 60;
								//if(updateCounter % 60 == 0) {
								if(walkingUp)
									moveUp();
								if(walkingDown)
									moveDown();
								if(walkingRight)
									moveRight();
								if(walkingLeft)
									moveLeft();
								//}

								updateNeeds();
								//moveAround();
							}

							public void updateNeeds() {

								if(updateCounter % 60 == 0) {
									this.hunger -= 1;
									this.thirst -= 1;
									this.energy -= 1;
								}

								updateAlive();
							}

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
							}

	//TODO--------------------------------------[END of section needing major fixing]-----------------------------------

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

	/*
		Methods used by the AI or Player follows here.
		-----------------------------------------------------------------------------------------------------------
		implementing ICharacterHandle
	 */

	//TODO implement, change type....
	@Override
	public int[] getNeeds() {
		return new int[0];
	}

	//TODO implement, change type....
	@Override
	public int[] getSkills() {
		return new int[0];
	}

	//TODO implement, change type....
	@Override
	public int[] getTraits() {
		return new int[0];
	}

	@Override
	public void moveUp() {
		this.yPos -= this.stepLength;
	}

	@Override
	public void moveDown() {
		this.yPos += this.stepLength;
	}

	@Override
	public void moveLeft() {
		this.xPos -= this.stepLength;
	}

	@Override
	public void moveRight() {
		this.xPos += this.stepLength;
	}

	@Override
	public List<ICollidable> getSurroundings() {
		return this.surroundings;
	}

	@Override
	public List<ICollidable> getInteractables() {
		return this.interactables;
	}

	@Override
	public List<IItem> getInventory(){
		return inventory.getItems();
	}

	//TODO implement
	@Override
	public void useItem(int inventoryIndex) {
		//inventory.getItems().get(inventoryIndex).useItem();
	}

	//TODO implement, visitor pattern
	@Override
	public boolean interactWith(int interactablesIndex) {
		return false;
	}

	//TODO implement
	@Override
	public Outcome getOutcomeInventory(int inventoryIndex) {
		for(IItem item : inventory.getItems()){
			//list.add(i.getOutcome());
		}

		return new Outcome();
	}

	//TODO implement
	@Override
	public Outcome getOutcomeInteractables(int interactablesIndex) {
		return null;
	}

	//TODO Update this part to work the same way that the AI does it.
				public void startWalkingUp(){
					walkingUp=true;
				}

				public void startWalkingDown(){
					walkingDown=true;
				}

				public void startWalkingRight(){
					walkingRight=true;
				}

				public void startWalkingLeft(){
					walkingLeft=true;
				}

				public void stopWalkingUp(){
					walkingUp=false;
				}

				public void stopWalkingDown(){
					walkingDown=false;
				}

				public void stopWalkingRight(){
					walkingRight=false;
				}

				public void stopWalkingLeft(){
					walkingLeft=false;
				}


	//TODO add to interface
	public void startRunning(){
		stepLength = Constants.CHARACTER_RUN_SPEED;
	}

	//TODO add to interface
	public void stopRunning(){
		stepLength = Constants.CHARACTER_WALK_SPEED;
	}


	//TODO remove this testing method
	public void hit() {
		for(ICollidable collidable : interactables){
			if(collidable.getClass().equals(this.getClass())){
				((Character)collidable).alive = false;
			}
		}
	}
}
