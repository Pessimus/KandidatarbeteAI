package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Tobias on 2016-03-29.
 */
public class IdleState implements IState {
	private final ArtificialBrain brain;

	public IdleState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		int[] needsArray = brain.getBody().getNeeds();

		// Critical levels of Hunger, Thirst and Energy which
		// needs to be dealt with immediately
		int minimumNeed = Math.min(Math.min(needsArray[0], needsArray[1]), needsArray[2]);
		if(needsArray[0] == minimumNeed && needsArray[0] <= 100){
			brain.setState(brain.getHungryState());
		}
		else if(needsArray[1] == minimumNeed && needsArray[1] <= 20){
			brain.setState(brain.getThirstyState());
		}
		else if(needsArray[2] == minimumNeed && needsArray[2] <= 20){
			brain.setState(brain.getSleepyState());
		}
		else{
			/*
			if(!body.hasHome()){
				brain.setState(brain.getBuildHouseState());
			}
			*/
		}
	}
}
