package Controller.AIStates;

import Controller.ArtificialBrain;
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
		boolean found = false;

		while (iterator.hasNext()) {
			IItem current = iterator.next();
			switch (current.getType()) {
				case CROPS_ITEM: //TODO: MORE ITEMS
				case MEAT_ITEM:
				case FISH_ITEM:
					bestIndex = currentIndex;
					found = true;
					break;
			}

			currentIndex++;
		}

		if(found){
			brain.getBody().consumeItem(bestIndex);
		}

		brain.setState(brain.getIdleState());
	}
}
