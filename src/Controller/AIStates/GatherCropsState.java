package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Utility.RenderObject;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-03-31.
 */
public class GatherCropsState implements IState {
	private final ArtificialBrain brain;

	private int waitUpdates = 0;
	private boolean waiting = false;
	private int bestIndex = -1;

	public GatherCropsState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		int cropsAmount = brain.getBody().getInventory()
				.stream()
				.filter(o -> o.getType().equals(IItem.Type.CROPS_ITEM))
				.mapToInt(IItem::getAmount)
				.sum();

		if(cropsAmount < brain.getNextResourceToGather().resourceAmount) {
			Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
			int i = 0;
			boolean found = false;
			while (iterator.hasNext()) {
				ICollidable next = iterator.next();
				if(next.getClass().equals(ResourcePoint.class)){
					ResourcePoint tempPoint = (ResourcePoint) next;
					if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.CROPS)) {
						brain.getBody().interactObject(i);
						brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.FARMING);
						found = true;
						break;
					}
				}

				i++;
			}

			if (!found) {
				brain.setState(brain.getGatherState());
			}
		} else {
			brain.getGatherStack().remove();
			brain.setState(brain.getIdleState());
		}
	}
}
