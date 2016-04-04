package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.Constants;
import Model.ICollidable;
import Model.ResourcePoint;
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
			ICollidable closest = null;
			int i = 0;
			for (ICollidable temp : surround) {
				if(temp.getClass().equals(ResourcePoint.class)){
					ResourcePoint tempPoint = (ResourcePoint) temp;
					if(tempPoint.getResourceName().toLowerCase().equals("crops")) {
						closest = temp;
						bestIndex = i;
						break;
					}
				}

				i++;
			}

			if(closest != null){
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
