package Model;


import Toolkit.RenderObject;

/**
 * Created by Martin on 24/02/2016.
 */
public interface ICollidable {
	float getX();
	float getY();
	double getCollisionRadius();
	double getInteractionRadius();
	double getSurroundingRadius();

	void addToInteractableX(ICollidable rhs);
	void addToInteractableY(ICollidable rhs);
	void checkInteractables();

	void addToSurroundingX(ICollidable rhs);
	void addToSurroundingY(ICollidable rhs);
	void checkSurroundings();

	//TODO maby change this to work for other collidables than characters
	void interacted(Character rhs);
	void consumed(Character rhs);
	void attacked(Character rhs);

	boolean toBeRemoved();

	RenderObject getRenderObject();
	RenderObject.RENDER_OBJECT_ENUM getRenderType();
}
