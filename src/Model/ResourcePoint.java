package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ResourcePoint implements ICollidable {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	IResource resource;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

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
	public float getX() {
		return xPos;
	}

	@Override
	public float getY() {
		return yPos;
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

	public String getResourceName(){
		return resource.getResourceName();
	}

//---------------------------------------Collision Methods------------------------------------------------------------\\

	@Override
	public void addToInteractableX(ICollidable rhs) {
		//TODO implement
	}

	@Override
	public void addToInteractableY(ICollidable rhs) {
		//TODO implement
	}

	@Override
	public void checkInteractables() {
		//TODO implement
	}

	@Override
	public void addToSurroundingX(ICollidable rhs) {
		//TODO implement
	}

	@Override
	public void addToSurroundingY(ICollidable rhs) {
		//TODO implement
	}

	@Override
	public void checkSurroundings() {
		//TODO implement
	}

//---------------------------------------Interaction methods----------------------------------------------------------\\

	@Override
	public void interacted(Character rhs){
		rhs.addToInventory(resource.gatherResource());
		System.out.println("interacted"+this);
	}

	@Override
	public void consumed(Character rhs){
		resource.gatherResource().consumed(rhs);
		System.out.println("consumed" + this);
	}

	@Override
	public void attacked(Character rhs){
		resource.setResourcesLeft(0);
		System.out.println("attacked" + this);
	}

//------------------------------------------Update METHODS------------------------------------------------------------\\

	@Override
	public boolean toBeRemoved() {
		return resource.getResourcesLeft()==0;
	}

//------------------------------------------------RENDER METHODS------------------------------------------------------\\

	@Override
	public RenderObject getRenderObject() {
		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}

	@Override
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return renderObjectEnum;
	}
}
