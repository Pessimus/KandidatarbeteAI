package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Toolkit.RenderObject;
import Toolkit.UniversalStaticMethods;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.lwjgl.Sys;

import java.util.LinkedList;
import java.util.List;

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
		if(waiting){
			if((waitUpdates = (++waitUpdates % Constants.GATHER_CROPS_STATE_TIME)) == 0) {
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
					if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.CROPS)) {
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
