package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IResource {
	ItemFactory getItemFactory();
	IItem gatherResource();
	ResourceType getResourceType();
	String getResourceName();
	int getResourcesLeft();

	//TODO maby change this to work for other collidables than characters
	void interacted(Character rhs);
	void consumed(Character rhs);
	void attacked(Character rhs);

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
