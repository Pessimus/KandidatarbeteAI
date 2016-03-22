package Model;

import Controller.Pathfinder;
import Controller.PathStep;
import java.awt.*;
import java.util.ArrayList;
import java.util.*;
/**
 * Created by Tobias on 2016-02-26.
 */
public class Character implements ICollidable, ITimeable {
	//------------------Movement Variables---------------------
	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private float xSpeed;
	private float ySpeed;

	private int updateCounter = 0;
	private final int updateHunger = 20;
	private final int updateThirst = 40;
	private final int updateEnergy = 20;

	// Tells the update function for each character if they are currently walking in any direction;
	private boolean walkingUp = false;
	private boolean walkingDown = false;
	private boolean walkingLeft = false;
	private boolean walkingRight = false;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;


	private Inventory inventory;

	private volatile RenderObject latestRenderObject;

	//----------------Collision------------------
	private LinkedList<ICollidable> collideX;
	private LinkedList<ICollidable> collideY;

	//TESTING
	private Pathfinder pathTest;
	private LinkedList<PathStep> stepTest;

	//---------------NEEDS VARIABLES--------------------

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

	private int stepLength = 6;

	//private double timeableInterval;
	private int key;


	public Character(float xPos, float yPos, int key){
		this.alive = true;
		//Initial position
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.key = key;

		//Create inventory
		inventory = new Inventory();

		this.hunger = 1000;
		this.thirst = 1000;
		this.energy = 1000;

		this.pathTest = new Pathfinder(16, 9600, 9600, 1, 1.4);
		this.pathTest.updateMask(new CollisionList());
		this.stepTest = null;

		this.collideX = new LinkedList<>();
		this.collideY = new LinkedList<>();
		this.collisionRadius = 5;
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

	@Override
	public double getCollisionRadius() {
		return this.collisionRadius;
	}

	@Override
	public void addToCollideX(ICollidable rhs) {
		this.collideX.add(rhs);
	}

	@Override
	public void addToCollideY(ICollidable rhs) {
		this.collideY.add(rhs);
	}

	@Override
	public void checkCollision() {
		for(ICollidable c : this.collideX){
			if(this.collideY.contains(c)){
				System.out.println("Krock med n�t!!!!!!!!!"+this.hashCode());
			}
		}
		this.collideX.clear();
		this.collideY.clear();
	}

	@Override
	public RenderObject getRenderObject() {
		if(latestRenderObject != null) {
			if (latestRenderObject.compare(this)) {
				return latestRenderObject;
			}
		}

		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}

	@Override
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return renderObjectEnum;
	}

	public int getHunger() {return this.hunger;}


	@Override
	public void updateTimeable() {

		//TODO Update needs
		//TODO Implement ageing etc...

		//Updates counter with one but doesn't exceed 60.
		updateCounter = (updateCounter+1) % 60;
		if(updateCounter % 60 == 0) {
			if(walkingUp)
				walkUp();
			if(walkingDown)
				walkDown();
			if(walkingRight)
				walkRight();
			if(walkingLeft)
				walkLeft();
		}
		//updateNeeds();
		//moveAround();
	}

	public void update(){
		//System.out.println("Character: update()");
		//moveAround();
	}

	public void updateNeeds() {

		if(updateCounter % 60 == 0) {
			this.hunger -= 1;
			this.thirst -= 1;
			this.energy -= 1;
			isDead();
		}
	}

	//Check if character is alive
	public void isDead() {
		if (hunger <= 0 || thirst <= 0 || energy <= 0) {
			alive = false;
		}
	}

	//Getter for alive
	public boolean isAlive() {
		return alive;
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

	/*
	Method for checking where the character wants to move
	 */
	public double getNextXPosition(){
		return this.xPos+this.xSpeed;
	}


	/*
	Method for checking where the character wants to move
	 */
	public double getNextYPosition(){
		return this.yPos+this.ySpeed;
	}
	public double moveX(){
		return this.xPos += this.xSpeed;
	}
	public double moveY(){
		return this.yPos += this.ySpeed;
	}

	public void walkRight(){
		this.xPos += this.stepLength;
	}

	public void walkLeft(){
		this.xPos -= this.stepLength;
	}

	public void stopRight(){
		this.xSpeed -= this.stepLength;
	}

	public void stopLeft(){
		this.xSpeed += this.stepLength;
	}

	public void walkUp(){
		this.yPos -= this.stepLength;
	}

	public void walkDown(){
		this.yPos += this.stepLength;
	}

	public void stopUp(){
		this.ySpeed += this.stepLength;
	}

	public void stopDown(){
		this.ySpeed -= this.stepLength;
	}

	public void moveAround(){
		if(updateCounter % 30 == 0) {
			if (Math.random() < 0.1) {
				double endx = 1000;
				double endy = 1000;

				stepTest = pathTest.getPath(xPos, yPos, endx, endy);

			}

			if (stepTest != null) {
				if (stepTest.getFirst().stepTowards(this)) {
					stepTest.removeFirst();
					if (stepTest.isEmpty())  {
						stepTest = null;
					}
				}
			}
		}

	}

	public void displayInventory(){
		if(!inventory.getItems().isEmpty()) {
			for(IItem item : inventory.getItems()){
				System.out.println(item);
			}
		}else{
			System.out.println("No items in inventory!");
		}

	}

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

	public void startRunning(){
		stepLength = 12;
	}

	public void stopRunning(){
		stepLength = 6;
	}

	public int getSteplength(){
		return stepLength;
	}


	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	public void setPosition(int x, int y){
		xPos = x;
		yPos = y;
	}
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!

}
