package Model;


import Utility.RenderObject;

/**
 * Created by Martin on 24/02/2016.
 */
public interface ICollidable {

	/** @return the position of the Collidable on the x-axis. */
	double getX();
	/** @return the position of the Collidable on the y-axis. */
	double getY();
	/** @return the radius of the Collidable. */
	double getCollisionRadius();
	/** @return radius of the Collidables interaction area.*/
	double getInteractionRadius();
	/** @return radius of the Collidables detection area.*/
	double getSurroundingRadius();

	/** @return true if the object should not be allowed to spawn on an other. */
	boolean isImovable();

	/**
	 * A method for detecting what Collidables it can interact with.
	 * Indicates that they are close enough on the x-axis.
	 * @param rhs the Collidable that is close enough to interact with, on the x-axis.
	 */
	void addToInteractableX(ICollidable rhs);
	/**
	 * A method for detecting what Collidables it can interact with.
	 * Indicates that they are close enough on the y-axis.
	 * @param rhs the Collidable that is close enough to interact with, on the y-axis.
	 */
	void addToInteractableY(ICollidable rhs);
	/** Updates the list of Collidables that it can interact with. */
	void checkInteractables();

	/**
	 * A method for detecting what Collidables it can detect.
	 * Indicates that they are close enough on the x-axis.
	 * @param rhs the Collidable that is close enough to interact with, on the x-axis.
	 */
	void addToSurroundingX(ICollidable rhs);
	/**
	 * A method for detecting what Collidables it can detect.
	 * Indicates that they are close enough on the y-axis.
	 * @param rhs the Collidable that is close enough to detect, on the y-axis.
	 */
	void addToSurroundingY(ICollidable rhs);
	/** Updates the list of Collidables that it can detect. */
	void checkSurroundings();

	//TODO maby change this to work for other collidables than characters

	/**
	 * Indicates that a Character is interacting with the Collidable.
	 * @param rhs the character that is interacting with it.
	 */
	void interacted(Character rhs);
	/**
	 * Indicates that a Character is consuming the Collidable.
	 * @param rhs the character that is consuming it.
	 */
	void consumed(Character rhs);
	/**
	 * Indicates that a Character is attacking the Collidable.
	 * @param rhs the character that is attacking it.
	 */
	void attacked(Character rhs);

	/**
	 * Executes the changes that should happen when a character interacts with it.
	 * @param rhs the character that is interacting with it.
	 */
	void interactedCommand(Character rhs);
	/**
	 * Executes the changes that should happen when a character consumes with it.
	 * @param rhs the character that is consuming it.
	 */
	void consumedCommand(Character rhs);
	/**
	 * Executes the changes that should happen when a character attacks with it.
	 * @param rhs the character that is attacking it.
	 */
	void attackedCommand(Character rhs);

	/**
	 * Interrupts a interaction that was about to happen.
	 * @param rhs the character that was about to interact with it.
	 */
	void interactedInterrupted(Character rhs);
	/**
	 * Interrupts a consummation that was about to happen.
	 * @param rhs the character that was about to consume it.
	 */
	void consumedInterrupted(Character rhs);
	/**
	 * Interrupts a attack that was about to happen.
	 * @param rhs the character that was about to attack it.
	 */
	void attackedInterrupted(Character rhs);

	/** @return true if this Collidable should be removed.*/
	boolean toBeRemoved();

	/** @return a RenderObject representing this Collidable */
	RenderObject getRenderObject();

	//TODO remove?
	RenderObject.RENDER_OBJECT_ENUM getRenderType();
	void setRenderType(RenderObject.RENDER_OBJECT_ENUM type);
}
