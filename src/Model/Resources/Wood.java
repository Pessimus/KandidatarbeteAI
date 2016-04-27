package Model.Resources;

import Model.*;
import Model.Character;
import Utility.Constants;
import Utility.RenderObject;

/**
 * Created by Oskar on 2016-03-04.
 */
public class Wood extends RenewableResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

    public static final ResourceType resourceType = ResourceType.WOOD;
    public static final String resourceName = "Wood";
	public static final int gatheringTime = Constants.GATHER_WOOD_STATE_TIME;

	private final double xPoss;
	private final double yPoss;

	private int yield;
	private int updateCounter;
	private boolean spawning;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing the resource "Wood".
	 * @param initial  the starting amount of resources.
	 * @param maxResources The maximum amount of resources this object can contain at a time.
	 * @param yield The amount of items gained from this resource at a time.
	 */
    public Wood(int initial, int maxResources, int yield, double xPoss, double yPoss){
        super(initial, maxResources);
		this.yield = yield;
		this.updateCounter = (int)(Math.random()*Constants.TREE_UPDATE_INTERVAL);
		this.spawning = false;
		this.xPoss = xPoss;
		this.yPoss = yPoss;
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
		this.gatherResource().consumedEffect(rhs);
	}

	@Override
	public void attacked(Character rhs) {
		this.setResourcesLeft(0);
	}

	@Override
	public int getInteractedTime() {
		return Constants.WOOD_INTERACTED_TIME;
	}

	@Override
	public int getConsumedTime() {
		return Constants.WOOD_CONSUMED_TIME;
	}

	@Override
	public int getAttackedTime() {
		return Constants.WOOD_ATTACKED_TIME;
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

//------------------------------------------UPDATE METHODS------------------------------------------------------------\\

	//TODO should create new trees.
	@Override
	/**{@inheritDoc}*/
    public void updateTimeable() {
		updateCounter = (updateCounter+1) % Constants.TREE_UPDATE_INTERVAL;
		if(updateCounter == 0 && getResourcesLeft() > 0){// && getResourcesLeft()<getMaxResources()){
			//setResourcesLeft(getResourcesLeft() + Constants.TREE_INCREASE_AMOUNT);
			this.spawning = true;
		}
	}

    @Override
	/**{@inheritDoc}*/
    public boolean toBeRemoved() {
        return !(getResourcesLeft()>0);
    }

	@Override
	public boolean isSpawning() {
		return spawning;
	}

	@Override
	public void spawn(World rhs) {
		double xDiff = (Math.random() * Constants.TREE_SPAWN_RADIUS) - Constants.TREE_SPAWN_RADIUS/2;
		double yDiff = (Math.random() * Constants.TREE_SPAWN_RADIUS) - Constants.TREE_SPAWN_RADIUS/2;
		Wood wood = new Wood(Constants.WOOD_INITIAL_RESOURCES, Constants.WOOD_MAX_RESOURCES, Constants.WOOD_RESOURCE_GAIN, xPoss + xDiff, yPoss + yDiff);
		rhs.addRenewableResourcePoint(wood, RenderObject.RENDER_OBJECT_ENUM.WOOD, xPoss + xDiff, yPoss + yDiff, Constants.TREE_COLLISION_RADIUS);
		this.spawning = false;
	}

}
