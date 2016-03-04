package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ResourcePoint implements ICollidable {
	IResource resource;

	private double xPos;
	private double yPos;
	private double collisionRadius;

	public ResourcePoint(IResource resourceType, double x, double y, double radius){
		resource = resourceType;
		xPos = x;
		yPos = y;
		collisionRadius = radius;
	}

	@Override
	public double getX() {
		return xPos;
	}

	@Override
	public double getY() {
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
