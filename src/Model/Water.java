package Model;


/**
 * Created by Tobias on 2016-03-04.
 */
public class Water extends InfiniteResource {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
	public static final ResourceType resourceType = ResourceType.WATER;
	public static final String resourceName = "Water";

	private int yield;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing the resource "Water".
	 * @param yield The amount of items gained from this resource at a time.
	 */
	public Water(int yield){
		this.yield = yield;
	}

	//-------------------------------------Interaction methods--------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public IItem gatherResource() {
		return ItemFactory.createItem(resourceType, yield);
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
