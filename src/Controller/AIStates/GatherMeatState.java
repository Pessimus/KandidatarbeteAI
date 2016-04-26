package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.Animal;
import Model.ICollidable;
import Model.IResource;
import Model.ResourcePoint;

import java.util.Iterator;

/**
 * Created by Oskar on 2016-04-01.
 */
public class GatherMeatState implements IState {
    private final ArtificialBrain brain;

    private int waitUpdates = 0;
    private boolean waiting = false;
    private int bestIndex = -1;

    public GatherMeatState(ArtificialBrain brain) {
        this.brain = brain;
    }

    @Override
    public void run() {
		Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
		int i = 0;
		boolean found = false;
		while (iterator.hasNext()) {
			ICollidable next = iterator.next();
			if(next.getClass().equals(Animal.class)){
				brain.getBody().attackObject(i);
				brain.getGatherStack().remove();
				found = true;
				break;
			}

			i++;
		}

		if(!found){
			brain.setState(brain.getGatherState());
		} else {
			brain.setState(brain.getIdleState());
		}
    }
}
