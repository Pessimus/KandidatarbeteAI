package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.IStructure;
import Model.World;

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


		// This 'if'-statement is used to determine what should be done
		// when there is nothing important to do.
		if (brain.getStateQueue().isEmpty()) {
			if (!brain.getBody().hasHome()) {
				brain.stackStructureToBuild(IStructure.StructureType.HOUSE);
				brain.stackState(brain.getBuildState());
			} else {
				if (secondaryNeedsArray[0] < 50) {
					brain.stackState(brain.getSocializeState());
				} else {
					Random r = new Random();
					double d = r.nextDouble();
					if (d > 0.9 && (!brain.getBody().hasFarm())) {
						for (IStructure structure : brain.getStructureMemory()) {
							if (structure.getStructureType() == IStructure.StructureType.FARM) {
								brain.setState(brain.getIdleState());
								brain.getBody().setHasFarm(true);
							}
						}
						brain.stackStructureToBuild(IStructure.StructureType.FARM);
						brain.stackState(brain.getBuildState());
					} else if (d > 0.8 && !brain.getBody().hasStockPile()) {
						brain.stackStructureToBuild(IStructure.StructureType.STOCKPILE);
						brain.stackState(brain.getBuildState());
					} else {
						d = r.nextDouble();
						if (brain.getAnimalTime() < 500 && World.nbrAnimals > 15) {
							brain.stackState(brain.getHuntingState());
						} else if (d > 0.7) {
							brain.stackState(brain.getGatherState());
						} else {
							if (d > 0.4 && d <= 0.7) {
								brain.stackState(brain.getDumpToStockpileState());
							} else {
								brain.stackState(brain.getWorkFarmState());
							}
						}
					}
				}
			}
		}

		brain.setState(brain.getStateQueue().poll());
	}
}
