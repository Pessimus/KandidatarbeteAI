package Model;

import java.util.List;

/**
 * Created by Tobias on 2016-03-25.
 */
public interface ICharacterHandle {

	int[] getNeeds();
	int[] getSkills();
	int[] getTraits();

	float getX();
	float getY();

	void moveUp();
	void moveDown();
	void moveLeft();
	void moveRight();

/*TODO REMOVE
	void startWalkingUp();
	void startWalkingDown();
	void startWalkingLeft();
	void startWalkingRight();
	void stopWalkingUp();
	void stopWalkingDown();
	void stopWalkingLeft();
	void stopWalkingRight();
*/
	float getSteplength();

	void startRunning();
	void stopRunning();

	List<ICollidable> getSurroundings();
	List<ICollidable> getInteractables();
	List<IItem> getInventory();

	//TODO REMOVE
	//void useItem(int inventoryIndex);
	//boolean interactWith(int interactiblesIndex);

	Outcome getOutcomeInventory(int inventoryIndex);
	Outcome getOutcomeInteractables(int interactablesIndex);

	Outcome interactObject(int index);
	Outcome attackObject(int index);
	Outcome consumeObject(int index);

	Outcome interactItem(int index);
	Outcome attackItem(int index);
	Outcome consumeItem(int index);

	//void applyOutcome(Outcome outcome);
}
