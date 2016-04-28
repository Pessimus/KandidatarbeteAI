package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Model.IResource;
import Model.ResourcePoint;
import Utility.RenderObject;

import java.util.Iterator;

/**
 * Created by Victor on 2016-04-04.
 */
public class GatherStoneState implements IState {
    private final ArtificialBrain brain;

    private int waitUpdates = 0;
    private boolean waiting = false;
    private int bestIndex = -1;

    public GatherStoneState(ArtificialBrain b){
        brain = b;
    }

    @Override
    public void run() {
		Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
		int i = 0;
		boolean found = false;
		while (iterator.hasNext()) {
			ICollidable next = iterator.next();
			if(next.getClass().equals(ResourcePoint.class)){
				ResourcePoint tempPoint = (ResourcePoint) next;
				if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.STONE)) {
					brain.getBody().interactObject(i);
					brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.PICKING);
					brain.getGatherStack().remove();
					found = true;
					break;
				}
			}

			i++;
		}

		if(!found){
			brain.setState(brain.getGatherState());
		} else {
			brain.setState(brain.getIdleState());
		}
    }
}
