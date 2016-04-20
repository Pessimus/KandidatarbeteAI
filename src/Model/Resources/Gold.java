package Model.Resources;

import Model.Character;
import Utility.Constants;
import Model.IItem;
import Model.ItemFactory;

/**
 * Created by Oskar on 2016-03-10.
 */
public class Gold extends FiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

    public static final ResourceType resourceType = ResourceType.GOLD;
    public static final String resourceName = "Gold";
	public static final int gatheringTime = Constants.GATHER_GOLD_STATE_TIME;

	private int yield;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\'

	/**
	 * A class representing the resource "Gold".
	 * @param initial the initial amount of the resource.
	 * @param yield the amount of the resource returned by gatherResource.
	 */
    public Gold(int initial, int yield){
        super(initial);
		this.yield = yield;
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

	@Override
	public void interacted(Character rhs) {
		rhs.addToInventory(this.gatherResource());
	}

	@Override
	public void consumed(Character rhs) {
		this.gatherResource().consumed(rhs);
	}

	@Override
	public void attacked(Character rhs) {
		this.setResourcesLeft(0);
	}

	@Override
	public int getInteractedTime() {
		return Constants.GOLD_INTERACTED_TIME;
	}

	@Override
	public int getConsumedTime() {
		return Constants.GOLD_CONSUMED_TIME;
	}

	@Override
	public int getAttackedTime() {
		return Constants.GOLD_ATTACKED_TIME;
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

	@Override
	public int getGatheringTime() {
		return gatheringTime;
	}

}
