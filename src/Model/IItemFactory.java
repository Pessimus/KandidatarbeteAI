package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IItemFactory {
	IItem createItem(IResource.ResourceType resource);
}
