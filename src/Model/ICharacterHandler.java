package Model;

import java.util.List;

/**
 * Created by Tobias on 2016-03-25.
 */
public interface ICharacterHandler {

	int[] getNeeds();
	int[] getSkills();
	int[] getTraits();

	float getX();
	float getY();

	void moveUp();
	void moveDown();
	void moveLeft();
	void moveRight();

	List<ICollidable> getSurroundings();
	List<ICollidable> getInteractables();
	List<IItem> getInventory();

	void useItem(int inventoryIndex);
	boolean interactWith(int interactablesIndex);

	Outcome getOutcomeInventory(int inventoryIndex);
	Outcome getOutcomeInteractables(int interactablesIndex);
}
