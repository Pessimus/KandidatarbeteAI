package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class RenewableResource implements ITimeable, IResource{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private int resourcesLeft;
	private int maxResources;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	protected RenewableResource(int initial, int maxResources) {
		resourcesLeft = initial;
		this.maxResources = maxResources;
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	public int getResourcesLeft(){
		return resourcesLeft;
	}

	public void setResourcesLeft(int amount){
		if(amount > maxResources){
			resourcesLeft = maxResources;
		}else if(amount >= 0) {
			resourcesLeft = amount;
		}
	}

	public int getMaxResources(){
		return maxResources;
	}

}
