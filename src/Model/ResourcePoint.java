package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ResourcePoint implements ICollidable {
	IResource resource;

	private float xPos;
	private float yPos;
	private double collisionRadius;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectenum;

	public ResourcePoint(IResource resourceType, RenderObject.RENDER_OBJECT_ENUM renderEnum, float x, float y, double radius){
		resource = resourceType;
		xPos = x;
		yPos = y;
		collisionRadius = radius;
		renderObjectenum = renderEnum;
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

	public RenderObject getRenderObject(){
		return new RenderObject(xPos, yPos, collisionRadius, RenderObject.RENDER_OBJECT_ENUM.TREE);
	}
}
