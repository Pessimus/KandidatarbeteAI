package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IResource {

	enum ResourceType{
		WOOD,
		WATER,
		GOLD,
		STONE,
		FISH,
		CROPS,
		MEAT,
		FOOD
	}

	/**
	 *  Decreases the amount of resources left, and returns a item with amount equal to the resources yield.
	 * @return a item of the type given by the type of the resource.
	*/
	IItem gatherResource();

	/** @return the type of the resource. */
	ResourceType getResourceType();

	/** @return the name of the resource. */
	String getResourceName();

	/** @return the remaining amount of resources at this point. */
	int getResourcesLeft();

	/** @return the time how long it takes to gather this resource. */
	int getGatheringTime();

	/**
	 * Sets the amount of resources to the specified amount.
	 * @param amount the amount to set the number of remaining resources to.
	 */
	void setResourcesLeft(int amount);


	void interacted(Character rhs);

	void consumed(Character rhs);

	void attacked(Character rhs);

	int getInteractedTime();

	int getConsumedTime();

	int getAttackedTime();

}
