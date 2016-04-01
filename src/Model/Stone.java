package Model;

/**
 * Created by Tobias on 2016-03-10.
 */
public class Stone extends FiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
	public static final ResourceType resourceType = ResourceType.STONE;
	public static final String resourceName = "Stone";

	private int yield;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing the resource "Stone".
	 * @param initial the starting amount of resources.
	 * @param yield The amount of items gained from this resource at a time.
	 */
	public Stone(int initial, int yield){
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
