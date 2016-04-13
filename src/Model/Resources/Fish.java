package Model.Resources;

import Model.Constants;
import Model.FiniteResource;
import Model.IItem;
import Model.ItemFactory;

/**
 * Created by Oskar on 2016-03-10.
 */
public class Fish extends FiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

    public static final ResourceType resourceType = ResourceType.FISH;
    public static final String resourceName = "Fish";
	public static final int gatheringTime = Constants.GATHER_FISH_STATE_TIME;

	private int yield;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing the resource "Fish".
	 * @param initial the initial amount of the resource.
	 * @param yield the amount of the resource returned by gatherResource.
	 */
    public Fish(int initial, int yield){
        super(initial);
		this.yield = yield;
    }

	@Override
	public int getGatheringTime() {
		return gatheringTime;
	}

//---------------------------------------Interaction methods----------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public IItem gatherResource() {
		int resourceLeft = getResourcesLeft();
		if(resourceLeft>yield){
			setResourcesLeft(resourceLeft-yield);
			return ItemFactory.createItem(resourceType, yield);
		}else if(resourceLeft>0){
			setResourcesLeft(0);
			return ItemFactory.createItem(resourceType, resourceLeft);
		}else{
			return null;
		}
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

    @Override
	/**{@inheritDoc}*/
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
	/**{@inheritDoc}*/
    public String getResourceName() {
        return resourceName;
    }

}
