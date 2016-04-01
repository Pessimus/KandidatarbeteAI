package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class RenewableResource implements ITimeable, IResource{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private int resourcesLeft;
	private int maxResources;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A abstract class for representing a renewable resource.
	 * @param initial the starting amount of resources.
	 * @param maxResources The maximum amount of resources this object can contain at a time.
	 */
	protected RenewableResource(int initial, int maxResources) {
		resourcesLeft = initial;
		this.maxResources = maxResources;
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public int getResourcesLeft(){
		return resourcesLeft;
	}

	@Override
	/**{@inheritDoc}*/
	public void setResourcesLeft(int amount){
		if(amount > maxResources){
			resourcesLeft = maxResources;
		}else if(amount >= 0) {
			resourcesLeft = amount;
		}
	}

	/** @return the maximum amount of resources this object can contain at a time. */
	public int getMaxResources(){
		return maxResources;
	}

}
