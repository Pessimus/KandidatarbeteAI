package Model;

import java.util.ArrayList;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Character implements ICollidable, ITimeable {
	private double charX;
	private double charY;
	private double collisionRadius;

	private double timeableInterval;

	private Inventory characterInventory;

	public Character(){
		characterInventory = new Inventory();
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public double setX() {	return 0;}

	@Override
	public double setY() {	return 0;}

	@Override
	public double getRadius() {
		return 0;
	}

	@Override
	public double getTimeInterval() {
		return 0;
	}

	@Override
	public void setTimeInterval(double period) {

	}

	@Override
	public void onUpdate() {

	}
}
