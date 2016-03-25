package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class FiniteResource implements IResource{
	private final int initialResources;

	private int resourcesLeft;

	protected FiniteResource(int initial) {
		this.initialResources = initial;
	}

	public int getResourcesLeft(){
		return resourcesLeft;
	}

	public void setResourcesLeft(int amount){resourcesLeft=amount;}
}
