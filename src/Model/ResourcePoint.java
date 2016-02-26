package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ResourcePoint implements ICollidable {
	IResource resource;

	public ResourcePoint(IResource resourceType){
		resource = resourceType;
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public double getRadius() {
		return 0;
	}
}
