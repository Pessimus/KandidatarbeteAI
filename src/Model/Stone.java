package Model;

/**
 * Created by Tobias on 2016-03-10.
 */
public class Stone extends FiniteResource {

	public static final ResourceType resourceType = ResourceType.STONE;
	public static final String resourceName = "Wood";

	public Stone(int resources){
		super(resources);
	}

	@Override
	public ItemFactory getItemFactory() {
		return null;
	}

	@Override
	public IItem gatherResource() {
		return null;
	}

	@Override
	public ResourceType getResourceType() {
		return resourceType;
	}

	@Override
	public String getResourceName() {
		return resourceName;
	}
}
