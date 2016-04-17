package Model;

import Utility.Constants;
import Utility.RenderObject;

import java.util.LinkedList;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Animal implements ICollidable, ITimeable {

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
	private LinkedList<ICollidable> surroundingX;
	private LinkedList<ICollidable> surroundingY;
	private LinkedList<ICollidable> surroundings;
	private LinkedList<ICollidable> interactableX;
	private LinkedList<ICollidable> interactableY;
	private LinkedList<ICollidable> interactables;

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

		//Initialize collision detection lists
		this.surroundingX = new LinkedList<>();
		this.surroundingY = new LinkedList<>();
		this.surroundings = new LinkedList<>();

		this.interactableX = new LinkedList<>();
		this.interactableY = new LinkedList<>();
		this.interactables = new LinkedList<>();
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

	@Override
	/**{@inheritDoc}*/
	public boolean isImovable(){
		return false;
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

	@Override
	public void interactedCommand(Character rhs) {

	}

	@Override
	public void consumedCommand(Character rhs) {

	}

	@Override
	public void attackedCommand(Character rhs) {

	}

	@Override
	public void interactedInterrupted(Character rhs) {

	}

	@Override
	public void consumedInterrupted(Character rhs) {

	}

	@Override
	public void attackedInterrupted(Character rhs) {

	}

//------------------------------------------UPDATE METHODS------------------------------------------------------------\\

	@Override
	public void updateTimeable() {
		Character target = null;
		for(ICollidable collidable : this.surroundings){
			if(collidable.getRenderType() == RenderObject.RENDER_OBJECT_ENUM.CHARACTER){
				target = (Character)collidable;
				break;
			}
		}
		if(!(target == null)){
			if(target.getX() > this.getX()){
				this.xPoss++;
			}else if(target.getX() < this.getX()){
				this.xPoss--;
			}
			if(target.getY() > this.getY()){
				this.yPoss++;
			}else if(target.getY() < this.getY()){
				this.yPoss--;
			}
		}
	}

	@Override
	public boolean toBeRemoved() {
		return !alive;
	}

	@Override
	public boolean isSpawning() {
		return false;
	}

	@Override
	public void spawn(World rhs) {

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
