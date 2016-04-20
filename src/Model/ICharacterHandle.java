package Model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Created by Tobias on 2016-03-25.
 */
public interface ICharacterHandle {

	/*TODO REMOVE
		void startWalkingUp();
		void startWalkingDown();
		void startWalkingLeft();
		void startWalkingRight();
		void stopWalkingUp();
		void stopWalkingDown();
		void stopWalkingLeft();
		void stopWalkingRight();

		void useItem(int inventoryIndex);
		boolean interactWith(int interactiblesIndex);

		void applyOutcome(Outcome outcome);
	*/

	/** @return a array containing the values of the characters needs. */
	int[] getNeeds();
	/** @return a array containing the values of the characters secondary needs. */
	int[] getSecondaryNeeds();
	/** * @return a array containing the values of the characters skills. */
	int[] getSkills();
	/** @return a array containing the values of the characters traits. */
	int[] getTraits();

	int getKey();

	/** @return the position of the character on the x-axis. */
	double getX();
	/** @return the position of the character on the y-axis. */
	double getY();

	/** Increases the characters y position by the characters step-length. */
	void moveUp();
	/** Decreases the characters y position by the characters step-length. */
	void moveDown();
	/** Decreases the characters x position by the characters step-length. */
	void moveLeft();
	/** Increases the characters x position by the characters step-length. */
	void moveRight();

	/** @return the change in position used by the move methods*/
	double getSteplength();


	/** Changes the step-length to the running value in the Constants. */
	void startRunning();
	/** Changes the step-length to the walking value in the Constants. */
	void stopRunning();

	/** @return A list containing all the ICollidables within the distance, defined in Constants, from the character.*/
	List<ICollidable> getSurroundings();
	/** @return A list containing all the ICollidables the character can interact with.*/
	List<ICollidable> getInteractables();
	/** @return A list containing all the items in the characters inventory.*/
	List<IItem> getInventory();

	//TODO add javadock
	Outcome getOutcomeInventory(int inventoryIndex);
	//TODO add javadock
	Outcome getOutcomeInteractables(int interactablesIndex);

	/**
	 * Tells the character to interact with the ICollidable with index 'index' in the list of interactables.
	 * @param index the index of the ICollidable to interact with.
	 */
	void interactObject(int index);
	/**
	 * Tells the character to attack the ICollidable with index 'index' in the list of interactables.
	 * @param index the index of the ICollidable to attack.
	 */
	void attackObject(int index);
	/**
	 * Tells the character to consume the ICollidable with index 'index' in the list of interactables.
	 * @param index the index of the ICollidable to consume.
	 */
	void consumeObject(int index);

	/**
	 * Tells the character to interact with the item with index 'index' in the characters inventory.
	 * @param index the index of the item to interact with.
	 */
	void interactItem(int index);
	/**
	 * Tells the character to attack the item with index 'index' in the characters inventory.
	 * @param index the index of the item to attack.
	 */
	void attackItem(int index);
	/**
	 * Tells the character to consume the item with index 'index' in the characters inventory.
	 * @param index the index of the item to consume.
	 */
	void consumeItem(int index);

	void sleep();

	void build(IStructure.StructureType type);

	boolean isWaiting();

	boolean hasHome();

	ICollidable getHome();

	void setInteractionType(Interaction.InteractionType type);
	Interaction.InteractionType getInteractionType();

	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
}
