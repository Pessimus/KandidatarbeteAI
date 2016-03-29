package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class RenewableResource implements ITimeable, IResource{
	private int resourcesLeft;

	private final int maxResources;

	protected RenewableResource(int initial, int maxResources) {
		resourcesLeft = initial;
		this.maxResources = maxResources;
	}

	public int getResourcesLeft(){
		return resourcesLeft;
	}

	public void setResourcesLeft(int amount){
		resourcesLeft = amount;
	}
}
