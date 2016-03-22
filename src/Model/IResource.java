package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IResource {
	ItemFactory getItemFactory();
	IItem gatherResource();
	ResourceType getResourceType();
	String getResourceName();

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
