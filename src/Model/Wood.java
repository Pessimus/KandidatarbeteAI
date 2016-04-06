package Model;

import Toolkit.RenderObject;

/**
 * Created by Oskar on 2016-03-04.
 */
public class Wood extends RenewableResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

    public static final ResourceType resourceType = ResourceType.WOOD;
    public static final String resourceName = "Wood";

	private final float xPoss;
	private final float yPoss;

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
    public Wood(int initial, int maxResources, int yield, float xPoss, float yPoss){
        super(initial, maxResources);
		this.yield = yield;
		this.updateCounter = 0;
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

//------------------------------------------UPDATE METHODS------------------------------------------------------------\\

	//TODO should create new trees.
	@Override
	/**{@inheritDoc}*/
    public void updateTimeable() {
		updateCounter = (updateCounter+1) % Constants.TREE_UPDATE_INTERVALL;
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
		float xDiff = ((float)Math.random()*500)-250;
		float yDiff = ((float)Math.random()*500)-250;
		Wood wood = new Wood(10, 10, 1, xPoss+xDiff, yPoss+yDiff);
		rhs.addRenewableResourcePoint(wood, RenderObject.RENDER_OBJECT_ENUM.WOOD,xPoss+xDiff,yPoss+yDiff,75);
		this.spawning = false;
	}
}
