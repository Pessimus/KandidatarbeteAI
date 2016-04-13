package Model;

import Model.Tasks.InteractTask;
import Toolkit.RenderObject;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ResourcePoint implements ICollidable {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private IResource resource;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A collidable object containing a resource that can be gained from it when interacting.
	 * @param resourceType the resource this collidable should contain.
	 * @param renderEnum what type of collidable this object is visually.
	 * @param x the x position of this collidable.
	 * @param y the y position of this collidable.
	 * @param radius the collision radius of this collidable.
	 */
	public ResourcePoint(IResource resourceType, RenderObject.RENDER_OBJECT_ENUM renderEnum, float x, float y, double radius){
		resource = resourceType;
		xPos = x;
		yPos = y;
		collisionRadius = radius;
		interactionRadius = radius;
		surroundingRadius = radius;
		renderObjectEnum = renderEnum;
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public float getX() {
		return xPos;
	}

	@Override
	/**{@inheritDoc}*/
	public float getY() {
		return yPos;
	}

	@Override
	/**{@inheritDoc}*/
	public double getCollisionRadius() {
		return collisionRadius;
	}

	@Override
	/**{@inheritDoc}*/
	public double getInteractionRadius() {
		return interactionRadius;
	}

	@Override
	/**{@inheritDoc}*/
	public double getSurroundingRadius() {
		return surroundingRadius;
	}

	/** @return the name of the resource as a string. */
	public String getResourceName(){
		return resource.getResourceName();
	}

	@Override
	/**{@inheritDoc}*/
	public boolean isImovable(){
		return true;
	}

//---------------------------------------Collision Methods------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableX(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableY(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void checkInteractables() {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingX(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingY(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void checkSurroundings() {
		//TODO implement
	}

//---------------------------------------Interaction methods----------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public void interacted(Character rhs){
//		if(!rhs.isWaiting()) {
//			rhs.wait(resource.getGatheringTime());
			System.out.println("Calling to schedule");
			Schedule.addTask(new InteractTask(this,rhs,1000*3));
			//rhs.addToInventory(resource.gatherResource());
			//System.out.println("interacted" + this);
//		}
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs){
		resource.gatherResource().consumed(rhs);
		//System.out.println("consumed" + this);
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs){
		resource.setResourcesLeft(0);
		System.out.println("attacked" + this);
	}

	@Override
	public void interactedCommand(Character rhs) {
		System.out.println("commanded interaction");
		rhs.addToInventory(resource.gatherResource());
	}

	@Override
	public void consumedCommand(Character rhs) {
		//TODO implement
	}

	@Override
	public void attackedCommand(Character rhs) {
		//TODO implement
	}

//------------------------------------------Update METHODS------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public boolean toBeRemoved() {
		return resource.getResourcesLeft()==0;
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

	public IResource getResource() {
		return resource;
	}
}
