package Model;


/**
 * Created by Tobias on 2016-03-04.
 */
public class Water extends InfiniteResource {
	private ItemFactory factory;

	public static final ResourceType resourceType = ResourceType.WATER;
	public static final String resourceName = "Water";

	@Override
	public ItemFactory getItemFactory() {
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
