package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
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
				Farm tempF = (Farm) tempC;
				brain.getBody().interactObject(i);
				hasFound = true;
				break;
			}
		}

		if(!hasFound){
			Iterator<ResourcePoint> iterator = brain.getResourceMemory().iterator();
			i = -1;
			hasFound = false;
			while(iterator.hasNext()) {
				i++;
				ICollidable tempC = iterator.next();
				if(tempC.getClass().equals(Farm.class)){
					Farm tempF = (Farm) tempC;
					brain.getBody().interactObject(i);
					hasFound = true;
					break;
				}
			}
		}
	}
}
