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
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public double getCollisionRadius() {
		return 0;
	}

	@Override
	public void addToCollideX() {
		//TODO implement
	}

	@Override
	public void addToCollideY() {
		//TODO implement
	}

	@Override
	public void checkCollision() {
		//TODO implement
	}

	@Override
	public RenderObject getRenderObject() {
		return null;
	}
}
