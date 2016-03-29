package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IResource {
	//ItemFactory getItemFactory();//TODO remove
	IItem gatherResource();
	ResourceType getResourceType();
	String getResourceName();
	int getResourcesLeft();

	enum ResourceType{
		WOOD,
		WATER,
		GOLD,
		STONE,
		FISH,
		CROPS,
		MEAT
	}
}
