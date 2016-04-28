package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.*;
import Utility.RenderObject;

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
			brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.EMPTY);
			if (!brain.getBody().hasHome()){
				brain.stackStructureToBuild(IStructure.StructureType.HOUSE);
				brain.stackState(brain.getBuildState());
			} else{
				if(secondaryNeedsArray[0] < 50){
					brain.stackState(brain.getSocializeState());
				} else {
					Random r = new Random();
					double d = r.nextDouble();
					if(d > 0.9  && (!brain.getBody().hasFarm())) {
						for(IStructure structure : brain.getStructureMemory()) {
							if(structure.getStructureType() == IStructure.StructureType.FARM) {
								brain.setState(brain.getIdleState());
							}
						}
						brain.stackStructureToBuild(IStructure.StructureType.FARM);
						brain.stackState(brain.getBuildState());
					} else if(d > 0.8 && !brain.getBody().hasStockPile()){
						brain.stackStructureToBuild(IStructure.StructureType.STOCKPILE);
						brain.stackState(brain.getBuildState());
					} else{
						r = new Random();
						d = r.nextDouble();
						if (d > 0.7){
							brain.stackState(brain.getGatherState());
						} else if(d > 0.3) {
							brain.stackState(brain.getHuntingState());
						}else {
							brain.stackState(brain.getWorkFarmState());
						}
					}
				}
			}
		}
			brain.setState(brain.getStateQueue().poll());
	}
}
