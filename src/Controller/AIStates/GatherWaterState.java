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

	public GatherWaterState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		List<ICollidable> surround = brain.getBody().getInteractables();

		for(ICollidable temp : surround){

			// TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!
			if(temp.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.LAKE)){
				// TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!

				if((waitUpdates = (++waitUpdates % Constants.GATHER_WATER_STATE_TIME)) == 0) {
					temp.interacted((Model.Character) brain.getBody());

					if (brain.getStateQueue().isEmpty()) {
						brain.setState(brain.getIdleState());
					} else {
						brain.setState(brain.getStateQueue().poll());
					}
				}
			}
		}
	}
}
