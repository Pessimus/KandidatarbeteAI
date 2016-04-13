package Model.Resources;

import Model.IResource;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class InfiniteResource implements IResource {

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public int getResourcesLeft(){
		return 1;
	}

	@Override
	/**{@inheritDoc}*/
	public void setResourcesLeft(int amount){
		;//Left empty on purpose
	}

}
