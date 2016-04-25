package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Model.IStructure;
import Model.ResourcePoint;
import Model.Structures.Farm;
import Utility.Constants;
import Utility.UniversalStaticMethods;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-04-18.
 */
public class WorkFarmState implements IState {
	private final ArtificialBrain brain;

	public WorkFarmState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		Iterator<ICollidable> iteratorC = brain.getBody().getInteractables().iterator();
		int i = -1;
		boolean hasFound = false;
		while(iteratorC.hasNext()) {
			i++;
			ICollidable tempC = iteratorC.next();
			if(tempC.getClass().equals(Farm.class)){
				System.out.println("Interacted with farm!");
				brain.getBody().interactObject(i);
				hasFound = true;
				break;
			}
		}

		if(!hasFound){
			for (IStructure tempC : brain.getStructureMemory()) {
				if (tempC.getClass().equals(Farm.class)) {
					Farm tempF = (Farm) tempC;
					brain.stackState(this);
					brain.findPathTo(tempF);
					brain.setState(brain.getMovingState());
					return;
				}
			}

			brain.stackState(this);
			brain.stackStructureToBuild(IStructure.StructureType.FARM);
			brain.setState(brain.getBuildState());
			return;
		}

		brain.setState(brain.getIdleState());
	}
}
