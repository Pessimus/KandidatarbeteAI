package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class RenewableResource implements ITimeable, IResource{
	private final int initialResources;

	private int renewRate;
	private int resourcesLeft;

	protected RenewableResource(int initial) {
		initialResources = initial;
	}

	public int getResourcesLeft(){
		return resourcesLeft;
	}

	public int getRenewRate(){
		return renewRate;
	}
}
