package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public abstract class InfiniteResource implements IResource {

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	public int getResourcesLeft(){
		return 1;
	}

	@Override
	public void setResourcesLeft(int amount){
		;//Left empty on purpose
	}

}
