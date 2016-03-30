package Model;

/**
 * Created by Oskar on 2016-03-04.
 */
public class Wood extends RenewableResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    //public static final int MAX_TREE_RESOURCES = 150;
    public static final ResourceType resourceType = ResourceType.WOOD;
    public static final String resourceName = "Wood";

	private int yield;
	private int updateCounter;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

    public Wood(int initial, int maxResources, int yield){
        super(initial, maxResources);
		this.yield = yield;
		this.updateCounter = 0;
    }

//---------------------------------------Interaction methods----------------------------------------------------------\\
	@Override
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
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

//------------------------------------------UPDATE METHODS------------------------------------------------------------\\
	//TODO should create new trees.
	@Override
    public void updateTimeable() {
		updateCounter = (updateCounter+1) % Constants.TREE_UPDATE_INTERVALL;
		if(updateCounter == 0 && getResourcesLeft()>0 && getResourcesLeft()<getMaxResources()){
			setResourcesLeft(getResourcesLeft() + Constants.TREE_INCREASE_AMOUNT);
		}
	}

    //TODO implement
    @Override
    public boolean toBeRemoved() {
        return !(getResourcesLeft()>0);
    }
}
