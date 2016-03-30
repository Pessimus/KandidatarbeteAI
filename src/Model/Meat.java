package Model;

/**
 * Created by Oskar on 2016-03-10.
 */
public class Meat extends FiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

    public static final ResourceType resourceType = ResourceType.MEAT;
    public static final String resourceName = "Meat";

    private int yield;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

    public Meat(int initial, int yield){
        super(initial);
		this.yield = yield;
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

}
