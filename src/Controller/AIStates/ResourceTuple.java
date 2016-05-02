package Controller.AIStates;

import Model.IResource;

/**
 * Created by Tobias on 2016-04-28.
 */
public final class ResourceTuple {
	public final IResource.ResourceType resourceType;
	public final int resourceAmount;

	public ResourceTuple(IResource.ResourceType type, int amount){
		resourceType = type;
		resourceAmount = amount;
	}
}
