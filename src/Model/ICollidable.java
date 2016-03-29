package Model;


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

	boolean remove();

	RenderObject getRenderObject();
	RenderObject.RENDER_OBJECT_ENUM getRenderType();
}
