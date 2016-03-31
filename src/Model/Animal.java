package Model;

import Toolkit.RenderObject;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Animal implements ICollidable {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	// TODO: Enum type for animal
	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;

	private float xPoss;
	private float yPoss;

	private IResource resource;

	private boolean alive;

	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing a animal in the world.
	 * @param xPoss the position on the x-axis
	 * @param yPoss the position on the y-axis
	 * @param resourceType the resource that can be gathered from this animal
	 */
	public Animal(float xPoss, float yPoss, IResource resourceType){
		this.xPoss = xPoss;
		this.yPoss = yPoss;

		this.resource = resourceType;

		this.alive = true;

		this.collisionRadius = Constants.ANIMAL_COLLISION_RADIUS;
		this.interactionRadius = Constants.ANIMAL_INTERACTION_RADIUS;
		this.surroundingRadius = Constants.ANIMAL_SURROUNDING_RADIUS;
	}

//---------------------------------------Collision Methods------------------------------------------------------------\\

	@Override
	public float getX() {
		return xPoss;
	}

	@Override
	public float getY() {
		return yPoss;
	}

	@Override
	public double getCollisionRadius() {
		return collisionRadius;
	}

	@Override
	public double getInteractionRadius() {
		return interactionRadius;
	}

	@Override
	public double getSurroundingRadius() {
		return surroundingRadius;
	}

	@Override
	public void addToInteractableX(ICollidable rhs) {
		//TODO implement, if AI for animal
	}

	@Override
	public void addToInteractableY(ICollidable rhs) {
		//TODO implement, if AI for animal
	}

	@Override
	public void checkInteractables() {
		//TODO implement, if AI for animal
	}

	@Override
	public void addToSurroundingX(ICollidable rhs) {
		//TODO implement, if AI for animal
	}

	@Override
	public void addToSurroundingY(ICollidable rhs) {
		//TODO implement, if AI for animal
	}

	@Override
	public void checkSurroundings() {
		//TODO implement, if AI for animal
	}

//---------------------------------------Interaction methods----------------------------------------------------------\\

	@Override
	public void interacted(Character rhs) {
		//TODO social need should be updated
	}

	@Override
	public void consumed(Character rhs) {
		rhs.changeHunger(Constants.ANIMAL_HUNGER_CHANGE_CONSUME);
		rhs.changeEnergy(Constants.ANIMAL_ENERGY_CHANGE_CONSUME);
		rhs.changeThirst(Constants.ANIMAL_THIRST_CHANGE_CONSUME);
		this.alive = false;
	}

	@Override
	public void attacked(Character rhs) {
		rhs.changeHunger(Constants.ANIMAL_HUNGER_CHANGE_ATTACK);
		rhs.changeEnergy(Constants.ANIMAL_ENERGY_CHANGE_ATTACK);
		rhs.changeThirst(Constants.ANIMAL_THIRST_CHANGE_ATTACK);
		rhs.addToInventory(resource.gatherResource());
		this.alive = false;
	}

//------------------------------------------UPDATE METHODS------------------------------------------------------------\\

	@Override
	public boolean toBeRemoved() {
		return !alive;
	}

//------------------------------------------------RENDER METHODS------------------------------------------------------\

	@Override
	public RenderObject getRenderObject() {
		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}

	@Override
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return renderObjectEnum;
	}
}
