package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Model.IStructure;
import Model.Interaction;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Tobias on 2016-03-29.
 */
public class IdleState implements IState {
	private final ArtificialBrain brain;

	public IdleState(ArtificialBrain brain) {
		this.brain = brain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		int[] secondaryNeedsArray = brain.getBody().getSecondaryNeeds();


		if(brain.getStateQueue().isEmpty()){
			if (!brain.getBody().hasHome()){
				brain.stackStructureToBuild(IStructure.StructureType.HOUSE);
				brain.stackState(brain.getBuildState());
			} else{
			/*	if(secondaryNeedsArray[0] < 99){
					brain.stackState(brain.getSocializeState());
				} else {*/
					Random r = new Random();
					double d = r.nextDouble();
					if(d > 0.8) {
						brain.stackStructureToBuild(IStructure.StructureType.STOCKPILE);
						brain.stackState(brain.getBuildState());
					} else if(d > 0.6){
						brain.stackStructureToBuild(IStructure.StructureType.FARM);
						brain.stackState(brain.getBuildState());
					} else{
						brain.stackState(brain.getGatherState());
					}
				}
			//}
		}

			brain.setState(brain.getStateQueue().poll());
		}
	}
