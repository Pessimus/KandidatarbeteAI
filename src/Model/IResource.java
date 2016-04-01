package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IResource {

	/*
	enum ResourceType{
		WOOD("Wood"),
		WATER("Water"),
		GOLD("Gold"),
		STONE("Stone"),
		FISH("Fish"),
		CROPS("Crops"),
		MEAT("Meat");

		public String resourceName;

		ResourceType(String name){
			resourceName = name;
		}
	}
	*/

	enum ResourceType{
		WOOD,
		WATER,
		GOLD,
		STONE,
		FISH,
		CROPS,
		MEAT
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

	/**
	 * Sets the amount of resources to the specified amount.
	 * @param amount the amount to set the number of remaining resources to.
	 */
	void setResourcesLeft(int amount);
}
