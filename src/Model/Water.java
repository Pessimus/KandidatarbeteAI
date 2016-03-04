package Model;


/**
 * Created by Tobias on 2016-03-04.
 */
public class Water extends InfiniteResource {
	private IItemFactory factory;

	private ResourceType resourceType = ResourceType.WATER;
	private String resourceName = "Water";

	@Override
	public IItemFactory getItemFactory() {
		return factory;
	}

	@Override
	public IItem gatherResource() {
		return getItemFactory().createItem(getResourceType());
	}

	@Override
	public ResourceType getResourceType() {
		return resourceType;
	}

	@Override
	public String getResourceName(){
		return resourceName;
	}
}
