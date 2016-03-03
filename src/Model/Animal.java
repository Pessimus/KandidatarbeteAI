package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Animal implements ICollidable {
	IResource resource;

	public Animal(){
		;
	}

	public Animal(IResource resourceType){
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
