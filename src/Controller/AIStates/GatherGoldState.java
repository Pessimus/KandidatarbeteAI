package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.Constants;
import Model.ICollidable;
import Model.IResource;
import Model.ResourcePoint;
import Toolkit.RenderObject;

import java.util.List;

/**
 * Created by Tobias on 2016-04-05.
 */
public class GatherGoldState implements IState {
	private final ArtificialBrain brain;

	private int waitUpdates = 0;
	private boolean waiting = false;
	private int bestIndex = -1;

	public GatherGoldState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		if(waiting){
			if((waitUpdates = (++waitUpdates % Constants.GATHER_GOLD_STATE_TIME)) == 0) {
				brain.getBody().interactObject(bestIndex);

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
					if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.GOLD)) {
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
		}
	}
}
