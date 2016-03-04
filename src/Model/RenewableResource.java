package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class RenewableResource implements ITimeable, IResource{
	private int renewRate;
	private int resourcesLeft;

	private final int maxResources;

	protected RenewableResource(int initial, int maxResources) {
		resourcesLeft = initial;
		this.maxResources = maxResources;
	}

	public int getResourcesLeft(){

		return resourcesLeft;
	}

	public void setResourceLeft(int amount){
		resourcesLeft = amount;
	}

	public int getRenewRate(){

		return renewRate;
	}
}
