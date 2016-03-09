package Model;

/**
 * Created by Martin on 24/02/2016.
 */
public interface ICollidable {
	float getX();
	float getY();
	double getCollisionRadius();

	public boolean checkCollision(ICollidable rhs);

	//TODO ENUM for type
}
