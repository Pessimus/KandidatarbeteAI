package Model;

/**
 * Created by Oskar on 2016-03-10.
 */
public class Fish extends FiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final ResourceType resourceType = ResourceType.FISH;
    public static final String resourceName = "Fish";

	private ResourceType type;
	private int yield;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

    public Fish(int initial, int yield){
        super(initial);
		this.type = ResourceType.CROPS;
		this.yield = yield;
    }

//---------------------------------------Interaction methods----------------------------------------------------------\\
	@Override
	public IItem gatherResource() {
		int resourceLeft = getResourcesLeft();
		if(resourceLeft>yield){
			setResourcesLeft(resourceLeft-yield);
			return ItemFactory.createItem(type, yield);
		}else if(resourceLeft>0){
			setResourcesLeft(0);
			return ItemFactory.createItem(type, resourceLeft);
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
