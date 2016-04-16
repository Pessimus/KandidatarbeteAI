package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Model.ICollidable;
import Model.Structures.House;

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
		for(ICollidable temp : interact){
			if(temp.equals(brain.getBody().getHome())){
				brain.getBody().interactObject(index);
				break;
			}

			index++;
		}

		if(index >= interact.size()){
			throw new IllegalStateException("House of Character isn't in Interactables-list when in SleepingState!");
		}

		if (brain.getStateQueue().isEmpty()) {
			brain.setState(brain.getIdleState());
		} else {
			brain.setState(brain.getStateQueue().poll());
		}
	}
}