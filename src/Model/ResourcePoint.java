package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ResourcePoint implements ICollidable {
	IResource resource;

	private float xPos;
	private float yPos;
	private double collisionRadius;

	public ResourcePoint(IResource resourceType, float x, float y, double radius){
		resource = resourceType;
		xPos = x;
		yPos = y;
		collisionRadius = radius;
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

	public String getResourceName(){
		return resource.getResourceName();
	}

	public IItem gatherResource(){
		return resource.gatherResource();
	}
}
