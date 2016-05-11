package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Utility.Constants;
import Utility.RenderObject;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-03-29.
 */
public class HungryState implements IState {
	private final ArtificialBrain brain;

	public HungryState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		if(brain.getBody().getNeeds()[0] < 95) {
			Iterator<IItem> iterator = brain.getBody().getInventory().iterator();
			IItem best = null;

			loop:
			while (iterator.hasNext()) {
				IItem current = iterator.next();
				switch (current.getType()) {
					case FISH_ITEM:
					case MEAT_ITEM:
					case CROPS_ITEM:
						best = current;
						break loop;
				}
			}

			brain.stackState(this);

			if (best == null) {
				if (brain.getAnimalTime() < 16 && World.nbrAnimals > Constants.MIN_ANIMAL_COUNT) {
					brain.setState(brain.getHuntingState());
				} else {
					brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.FOOD, 1));
					brain.setState(brain.getGatherState());
				}
			} else {
				brain.setState(brain.getEatState());
			}
		} else {
			brain.setState(brain.getIdleState());
		}
	}
}
