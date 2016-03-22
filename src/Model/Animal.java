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

	public Animal(){
		this.collisionRadius = 5;
		this.interactionRadius = 10;
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
	public void addToCollideX(ICollidable rhs) {
		//TODO implement
	}

	@Override
	public void addToCollideY(ICollidable rhs) {
		//TODO implement
	}

	@Override
	public void checkCollision() {
		//TODO implement
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
