package Model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Character implements ICollidable, ITimeable {
	private double xPoss;
	private double yPoss;
	private double collisionRadius;
	private double interactionRadius;
	private Inventory inventory;
	private double xSpeed;
	private double ySpeed;
	private final double stepLenght = 10;

	//private double timeableInterval;


	public Character(double xPoss, double yPoss){
		this.xPoss = xPoss;
		this.yPoss = yPoss;
		inventory = new Inventory();
		this.xSpeed = 0;
		this.ySpeed = 0;
	}

	@Override
	public double getX() {
		return this.xPoss;
	}

	@Override
	public double getY() {
		return this.yPoss;
	}

	@Override
	public double getCollisionRadius() {
		return this.collisionRadius;
	}

	@Override
	public void update() {
		//TODO Update needs
		//TODO Implement ageing etc...
	}
	/*
	Method for checking where the character wants to move
	 */
	public double getNextXPossition(){
		return this.xPoss+this.xSpeed;
	}
	/*
	Method for checking where the character wants to move
	 */
	public double getNextYPossition(){
		return this.yPoss+this.ySpeed;
	}
	public double moveX(){
		return this.xPoss += this.xSpeed;
	}
	public double moveY(){
		return this.yPoss += this.ySpeed;
	}

	public void walkRight(){
		this.xSpeed += this.stepLenght;
	}

	public void walkLeft(){
		this.xSpeed -= this.stepLenght;
	}

	public void stopRight(){
		this.xSpeed -= this.stepLenght;
	}

	public void stopLeft(){
		this.xSpeed += this.stepLenght;
	}

	public void walkUp(){
		this.ySpeed -= this.stepLenght;
	}

	public void walkDown(){
		this.ySpeed += this.stepLenght;
	}

	public void stopUp(){
		this.ySpeed += this.stepLenght;
	}

	public void stopDown(){
		this.ySpeed -= this.stepLenght;
	}



/*
	@Override
	public double getTimeInterval() {
		return 0;
	}
	@Override
	public void setTimeInterval(double period) {
	}
	@Override
	public void onUpdate() {
	}*/



}
