package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ResourcePoint implements ICollidable {
	IResource resource;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

	private RenderObject latestRenderObject;

	//TODO toBeRemoved the parameter renderEnum, and get it from the renderType.
	public ResourcePoint(IResource resourceType, RenderObject.RENDER_OBJECT_ENUM renderEnum, float x, float y, double radius){
		resource = resourceType;
		xPos = x;
		yPos = y;
		collisionRadius = radius;
		interactionRadius = radius;
		surroundingRadius = radius;
		renderObjectEnum = renderEnum;
	}

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
		return collisionRadius;
	}

	@Override
	public double getSurroundingRadius() {
		return surroundingRadius;
	}

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
		//TODO implement
	}

	@Override
	public void consumed(Character rhs){
		//TODO implement
	}

	@Override
	public void attacked(Character rhs){
		//TODO implement
	}


	//TODO implement
	@Override
	public boolean toBeRemoved() {
		return resource.getResourcesLeft()==0;
	}

	public String getResourceName(){
		return resource.getResourceName();
	}

	public IItem gatherResource(){
		return resource.gatherResource();
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
}
