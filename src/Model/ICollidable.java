package Model;

import java.util.LinkedList;

/**
 * Created by Martin on 24/02/2016.
 */
public interface ICollidable {
	float getX();
	float getY();
	double getCollisionRadius();
	double getInteractionRadius();

	void addToCollideX(ICollidable rhs);
	void addToCollideY(ICollidable rhs);

	void checkCollision();

	//TODO ENUM for type

	RenderObject getRenderObject();
	RenderObject.RENDER_OBJECT_ENUM getRenderType();
}
