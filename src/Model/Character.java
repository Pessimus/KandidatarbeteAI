package Model;

import java.awt.*;
import java.util.ArrayList;

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

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;


	private Inventory inventory;

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

	private final double stepLength = 10;

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
	public RenderObject getRenderObject() {
		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}
	
	public int getHunger() {return this.hunger;}


	@Override
	public void update() {

		//TODO Update needs
		//TODO Implement ageing etc...

		//Updates counter with one but doesn't exceed 60.
		updateCounter = (updateCounter+1) % 60;
		updateNeeds();
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
		this.xSpeed += this.stepLength;
	}

	public void walkLeft(){
		this.xSpeed -= this.stepLength;
	}

	public void stopRight(){
		this.xSpeed -= this.stepLength;
	}

	public void stopLeft(){
		this.xSpeed += this.stepLength;
	}

	public void walkUp(){
		this.ySpeed -= this.stepLength;
	}

	public void walkDown(){
		this.ySpeed += this.stepLength;
	}

	public void stopUp(){
		this.ySpeed += this.stepLength;
	}

	public void stopDown(){
		this.ySpeed -= this.stepLength;
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
