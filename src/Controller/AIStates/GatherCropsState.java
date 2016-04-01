package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Toolkit.RenderObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tobias on 2016-03-31.
 */
public class GatherCropsState implements IState {
	private final ArtificialBrain brain;

	public GatherCropsState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		List<ICollidable> surround = brain.getBody().getInteractables();

		for(ICollidable temp : surround){

			// TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!
			if(temp.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.CROPS)){
			// TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!

				temp.interacted((Model.Character) brain.getBody());
			}
		}
	}
}
