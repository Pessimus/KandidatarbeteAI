package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IResource {
	IItemFactory getItemFactory();
	IItem gatherResource();
	String getResourceName();
}
