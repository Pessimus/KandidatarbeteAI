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
		MEAT
	}

	IItem gatherResource();
	ResourceType getResourceType();
	String getResourceName();
	int getResourcesLeft();
	void setResourcesLeft(int amount);
}
