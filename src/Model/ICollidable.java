package Model;

/**
 * Created by Martin on 24/02/2016.
 */
public interface ICollidable {
	float getX();
	float getY();
	double getCollisionRadius();

	public void addToCollideX(ICollidable rhs);
	public void addToCollideY(ICollidable rhs);

	public void checkCollision();

	//TODO ENUM for type

	RenderObject getRenderObject();
}
