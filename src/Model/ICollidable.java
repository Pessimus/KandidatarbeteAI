package Model;

/**
 * Created by Martin on 24/02/2016.
 */
public interface ICollidable {
	float getX();
	float getY();
	double getCollisionRadius();

	public void addToCollideX();
	public void addToCollideY();

	public void checkCollision();

	//TODO ENUM for type

	RenderObject getRenderObject();
}
