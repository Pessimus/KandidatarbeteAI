package Model;

/**
 * Created by Tobias on 2016-03-04.
 */
public class Water extends InfiniteResource {
	private IItemFactory factory;

	private String resourceName;

	@Override
	public IItemFactory getItemFactory() {
		return factory;
	}

	@Override
	public IItem gatherResource() {
		return null;
	}

	@Override
	public String getResourceName() {
		return null;
	}
}
