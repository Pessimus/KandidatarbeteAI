package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Model.IItem;
import Model.IResource;
import Model.ResourcePoint;
import Utility.RenderObject;

import java.util.Iterator;

/**
 * Created by Oskar on 2016-04-01.
 */
public class GatherFishState implements IState {
    private final ArtificialBrain brain;

    private int waitUpdates = 0;
    private boolean waiting = false;
    private int bestIndex = -1;

    public GatherFishState(ArtificialBrain brain) {
        this.brain = brain;
    }

    @Override
    public void run() {
		int fishAmount = brain.getBody().getInventory()
				.stream()
				.filter(o -> o.getType().equals(IItem.Type.FISH_ITEM))
				.mapToInt(IItem::getAmount)
				.sum();

		if(fishAmount < brain.getNextResourceToGather().resourceAmount) {
			Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
			int i = 0;
			boolean found = false;
			while (iterator.hasNext()) {
				ICollidable next = iterator.next();
				if(next.getClass().equals(ResourcePoint.class)){
					ResourcePoint tempPoint = (ResourcePoint) next;
					if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.WATER)) {
						brain.getBody().attackObject(i);
						brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.FISHING);
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
