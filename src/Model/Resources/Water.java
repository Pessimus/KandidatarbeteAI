package Model.Resources;


import Model.Character;
import Utility.Constants;
import Model.IItem;
import Model.ItemFactory;

/**
 * Created by Tobias on 2016-03-04.
 */
public class Water extends InfiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
	public static final ResourceType resourceType = ResourceType.WATER;
	public static final ResourceType secondaryResourceType = ResourceType.FISH;
	public static final String resourceName = "Water";
	public static final int gatheringTime = Constants.GATHER_WATER_STATE_TIME;
	public static final int secondaryGatheringTime = Constants.GATHER_FISH_STATE_TIME;

	private int yield;
	private int secondaryYield;

	@Override
	public int getGatheringTime() {
		return gatheringTime;
	}

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing the resource "Water".
	 * @param yield The amount of items gained from this resource at a time.
	 */
	public Water(int yield, int secondaryYield){
		this.yield = yield;
		this.secondaryYield = secondaryYield;
	}

	//-------------------------------------Interaction methods--------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public IItem gatherResource() {
		return ItemFactory.createItem(resourceType, yield);
	}

	private IItem gatherSecondaryResource(){
		return ItemFactory.createItem(secondaryResourceType, secondaryYield);
	}

	@Override
	public void interacted(Character rhs) {
		rhs.addToInventory(this.gatherResource());
	}

	@Override
	public void consumed(Character rhs) {
		this.gatherSecondaryResource().consumed(rhs);
	}

	@Override
	public void attacked(Character rhs) {
		rhs.addToInventory(this.gatherSecondaryResource());
	}

	@Override
	public int getInteractedTime() {
		return Constants.WATER_INTERACTED_TIME;
	}

	@Override
	public int getConsumedTime() {
		return Constants.WATER_CONSUMED_TIME;
	}

	@Override
	public int getAttackedTime() {
		return Constants.WATER_ATTACKED_TIME;
	}

//------------------------------------------Getters & Setters---------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public ResourceType getResourceType() {
		return resourceType;
	}

	@Override
	/**{@inheritDoc}*/
	public String getResourceName(){
		return resourceName;
	}

}
