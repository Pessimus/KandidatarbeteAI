package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.Constants;
import Model.ICollidable;
import Toolkit.RenderObject;
import org.lwjgl.Sys;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tobias on 2016-03-31.
 */
public class GatherWaterState implements IState {
	private final ArtificialBrain brain;

	private int waitUpdates = 0;
	private boolean waiting = false;
	private int bestIndex = -1;

	public GatherWaterState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		if(waiting){
			if((waitUpdates = (++waitUpdates % Constants.GATHER_WATER_STATE_TIME)) == 0) {
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
				// TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!
				if (temp.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.LAKE)) {
					// TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!
					waiting = true;
					bestIndex = i;
				}

				i++;
			}
		}
	}
}
