package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Animal implements ICollidable {
	IResource resource;

	// TODO: Enum type for animal
	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;

	private RenderObject latestRenderObject;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	public Animal(){
		this.collisionRadius = 5;
		this.interactionRadius = 10;
		this.surroundingRadius = 20;
	}

	public Animal(IResource resourceType){
		resource = resourceType;
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
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

	//TODO implement
	@Override
	public boolean remove() {
		return false;
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
