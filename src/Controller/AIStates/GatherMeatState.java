package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Model.IResource;
import Model.ResourcePoint;

import java.util.Iterator;

/**
 * Created by Oskar on 2016-04-01.
 */
public class GatherMeatState implements IState {
    private final ArtificialBrain brain;

    private int waitUpdates = 0;
    private boolean waiting = false;
    private int bestIndex = -1;

    public GatherMeatState(ArtificialBrain brain) {
        this.brain = brain;
    }

    @Override
    public void run() {
		Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			ICollidable next = iterator.next();
			if(next.getClass().equals(ResourcePoint.class)){
				ResourcePoint tempPoint = (ResourcePoint) next;
				if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.MEAT)) {
					brain.getBody().interactObject(i);
				}
			}

			i++;
		}

		if (brain.getStateQueue().isEmpty()) {
			brain.setState(brain.getIdleState());
		} else {
			brain.setState(brain.getStateQueue().poll());
		}

        /*if(waiting){
            if((waitUpdates = (++waitUpdates % Constants.GATHER_MEAT_STATE_TIME)) == 0) {
                brain.getBody().consumeItem(bestIndex);

                waiting = false;
                bestIndex = -1;

                if (brain.getStateQueue().isEmpty()) {
                    brain.setState(brain.getIdleState());
                } else {
                    brain.setState(brain.getStateQueue().poll());
                }
            }
        } else {
            List<ICollidable> surround = brain.getBody().getInteractables();
            int i = 0;
            for (ICollidable temp : surround) {
                if(temp.getClass().equals(ResourcePoint.class)){
                    ResourcePoint tempPoint = (ResourcePoint) temp;
                    if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.MEAT)) {
                        bestIndex = i;
                        break;
                    }
                }

                i++;
            }

            if(bestIndex > 0){
                waiting = true;
            } else{
                if (brain.getStateQueue().isEmpty()) {
                    brain.setState(brain.getIdleState());
                } else {
                    brain.setState(brain.getStateQueue().poll());
                }
            }
        }*/
    }
}
