package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Utility.RenderObject;
import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SleepingState implements IState{
	private final ArtificialBrain brain;

	private int waitUpdates = 0;
	private boolean waiting = false;
	private int bestIndex = -1;

	public SleepingState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		List<ICollidable> interact = brain.getBody().getInteractables();
		int index = 0;
		boolean foundHouse = false;
		for(ICollidable temp : interact){
			if(temp.equals(brain.getBody().getHome())){
				brain.getBody().interactObject(index);
				brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.SLEEPING);
				foundHouse = true;
				break;
			}

			index++;
		}

		if(!foundHouse){
			brain.stackState(brain.getLowEnergyState());
		}

		brain.setState(brain.getIdleState());
	}
}