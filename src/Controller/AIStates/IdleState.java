package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Model.IStructure;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

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
		int[] secondaryNeedsArray = brain.getBody().getSecondaryNeeds();
		int minVal = needsArray[0];
		int minindex = 0;
		// Critical levels of Hunger, Thirst and Energy which
		// needs to be dealt with immediately
		int minimumNeed = 0; // = Math.min(Math.min(needsArray[0], needsArray[1]), needsArray[2]);

		if(brain.getStateQueue().isEmpty()){
			if (!brain.getBody().hasHome()){
				brain.stackStructureToBuild(IStructure.StructureType.HOUSE);
				brain.stackState(brain.getBuildState());
			} else{
				if(secondaryNeedsArray[0] < 99){
					brain.stackState(brain.getSocializeState());
				} else {
					Random r = new Random();
					double d = r.nextDouble();
					if(d > 0.8) {
						brain.stackStructureToBuild(IStructure.StructureType.STOCKPILE);
						brain.stackState(brain.getBuildState());
					} else if(d > 0.6){
						brain.stackStructureToBuild(IStructure.StructureType.FARM);
						brain.stackState(brain.getBuildState());
					} else{
						brain.stackState(brain.getSocializeState());
					}
				}
				for (int i = 0; i < needsArray.length ; i++) {
					if (needsArray[i] < minVal) {
						minVal = needsArray[i];
						minindex = i;
					}
				}
			}
		}

		if (needsArray[2] <= 70){
			if(!brain.getStateQueue().contains(brain.getLowEnergyState()))
				brain.stackState((brain.getLowEnergyState()));
		}
		if(needsArray[0] <= 70){
			if(!brain.getStateQueue().contains(brain.getHungryState()))
				brain.stackState((brain.getHungryState()));
		}
		if(needsArray[1] <= 70){
			if(!brain.getStateQueue().contains(brain.getThirstyState()))
				brain.stackState((brain.getThirstyState()));
		}

		brain.setState(brain.getStateQueue().poll());
	}
}
