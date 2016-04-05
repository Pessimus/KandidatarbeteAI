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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
			//DO WE NEED TO INCREASE ANY OF OUR NEEDS?
			int[] needsArray = brain.getBody().getNeeds();
			int minVal = needsArray[0];
			int minindex = 0;
			// Critical levels of Hunger, Thirst and Energy which
			// needs to be dealt with immediately
			int minimumNeed = 0; // = Math.min(Math.min(needsArray[0], needsArray[1]), needsArray[2]);

			for (int i = 0; i < needsArray.length ; i++) {
				if (needsArray[i] < minVal) {
					minVal = needsArray[i];
					minindex = i;
				}
			}

			if(needsArray[0] <= 90){
				brain.stackState((brain.getHungryState()));
			}

			if(needsArray[1] <= 90){

				brain.stackState((brain.getThirstyState()));
			}
			if (needsArray[2] <= 90){
				brain.stackState((brain.getLowEnergyState()));
			}
			if(brain.getStateQueue().isEmpty()){
				brain.queueState(brain.getGatherState());
				/*
				if(!body.hasHome()){
					brain.setState(brain.getBuildHouseState());
				}
				*/
			}
		brain.setState(brain.getStateQueue().poll());
	}
}
