package Model;


/**
 * Created by Tobias on 2016-03-04.
 */
public class Water extends InfiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
	public static final ResourceType resourceType = ResourceType.WATER;
	public static final String resourceName = "Water";

	private ResourceType type;
	private int yield;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public Water(int resources, int yield){
		this.type = ResourceType.WATER;
		this.yield = yield;
	}

	//---------------------------------------Interaction methods----------------------------------------------------------\\
	@Override
	public IItem gatherResource() {
		return ItemFactory.createItem(type, yield);
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	public ResourceType getResourceType() {
		return resourceType;
	}

	@Override
	public String getResourceName(){
		return resourceName;
	}

	@Override
	public int getResourcesLeft() {
		return 1;
	}
	
}
