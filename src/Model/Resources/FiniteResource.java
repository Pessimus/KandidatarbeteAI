package Model.Resources;

import Model.IResource;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class FiniteResource implements IResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private int resourcesLeft;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A abstract class for representing a finite resource.
	 * @param initial the initial amount of the resource.
	 */
	protected FiniteResource(int initial) {
		this.resourcesLeft = initial;
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public int getResourcesLeft(){
		return resourcesLeft;
	}

	@Override
	/**{@inheritDoc}*/
	public void setResourcesLeft(int amount){resourcesLeft=amount;}

}
