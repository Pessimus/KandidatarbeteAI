package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IResource {

	IItemFactory getItemFactory();
	IItem gatherResource();
	ResourceType getResourceType();
	String getResourceName();

	public enum ResourceType{
		TREE,
		WATER,
		GOLD,
		STONE,
		FOOD
	}

}
