package Controller.AIStates;

import Controller.ArtificialBrain;
import Utility.Constants;
import Model.IItem;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-03-29.
 */
public class EatState implements IState{
	private final ArtificialBrain brain;

	private int waitUpdates = 0;
	private boolean waiting = false;
	private int bestIndex = -1;

	public EatState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		Iterator<IItem> iterator = brain.getBody().getInventory().iterator();
		int currentIndex = 0;

		while (iterator.hasNext()) {
			IItem current = iterator.next();
			switch (current.getType()) {
				case CROPS_ITEM: //TODO: MORE ITEMS
					bestIndex = currentIndex;
					brain.getBody().consumeItem(bestIndex);
					return;
				case MEAT_ITEM:
					break;
				case FISH_ITEM:
					break;
			}

			currentIndex++;
		}

		if(brain.getStateQueue().isEmpty()) {
			brain.setState(brain.getIdleState());
		}
		else{
			brain.setState(brain.getStateQueue().poll());
		}
	}
}
