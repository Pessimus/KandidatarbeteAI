package Model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Character implements ICollidable, ITimeable {
	//------------------Movement Variables---------------------
	private double xPos;
	private double yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double xSpeed;
	private double ySpeed;


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


	public Character(double xPos, double yPos){
		this.alive = true;
		//Initial position
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSpeed = 0;
		this.ySpeed = 0;

		//Create inventory
		inventory = new Inventory();

		this.hunger = 100;
		this.thirst = 100;
		this.energy = 100;

	}

	@Override
	public double getX() {
		return this.xPos;
	}

	@Override
	public double getY() {
		return this.yPos;
	}

	@Override
	public double getCollisionRadius() {
		return this.collisionRadius;
	}


	@Override
	public void update() {

		//TODO Update needs
		//TODO Implement ageing etc...
		updateNeeds();
		isDead();
	}

	public void updateNeeds() {
		this.hunger -= 1;
		this.thirst -= 2;
		this.energy -= 1;
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
	public double getNextXPossition(){
		return this.xPos+this.xSpeed;
	}


	/*
	Method for checking where the character wants to move
	 */
	public double getNextYPossition(){
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

	/*
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
	*/
}
