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

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

	private RenderObject latestRenderObject;

	public ResourcePoint(IResource resourceType, RenderObject.RENDER_OBJECT_ENUM renderEnum, float x, float y, double radius){
		resource = resourceType;
		xPos = x;
		yPos = y;
		collisionRadius = radius;
		interactionRadius = radius;
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
