package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Model.IResource;
import Model.ResourcePoint;

import java.util.Iterator;

/**
 * Created by Victor on 2016-04-01.
 */
public class GatherWoodState implements IState {
    private final ArtificialBrain brain;

    private int waitUpdates = 0;
    private boolean waiting = false;
    private int bestIndex = -1;

    public GatherWoodState(ArtificialBrain b){
        brain = b;
    }

	int i = 0;

    @Override
    public void run() {
		Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
		int i = 0;
		boolean found = false;
		while (iterator.hasNext()) {
			ICollidable next = iterator.next();
			if(next.getClass().equals(ResourcePoint.class)){
				ResourcePoint tempPoint = (ResourcePoint) next;
				if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.WOOD)) {
					brain.getBody().interactObject(i);
					brain.getGatherStack().remove();
					found = true;
					break;
				}
			}

			i++;
		}

		if(!found){
			brain.stackState(brain.getGatherState());
		} else {
			brain.setState(brain.getIdleState());
		}
    }
}
