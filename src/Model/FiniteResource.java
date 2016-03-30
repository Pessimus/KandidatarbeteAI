package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class FiniteResource implements IResource{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private int resourcesLeft;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	protected FiniteResource(int initial) {
		this.resourcesLeft = initial;
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	public int getResourcesLeft(){
		return resourcesLeft;
	}

	public void setResourcesLeft(int amount){resourcesLeft=amount;}
}
