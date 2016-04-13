package Controller.AIStates;

import Controller.ArtificialBrain;
import Toolkit.Constants;
import Model.IItem;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-03-29.
 */
public class DrinkState implements IState{
	private final ArtificialBrain brain;

	private int waitUpdates = 0;
	private boolean waiting = false;
	private int bestIndex = -1;

	public DrinkState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		if(waiting){
			if((waitUpdates = (++waitUpdates % Constants.DRINK_STATE_TIME)) == 0) {
				brain.getBody().consumeItem(bestIndex);

				waiting = false;
				bestIndex = -1;

				if (brain.getStateQueue().isEmpty()) {
					brain.setState(brain.getIdleState());
				} else {
					brain.setState(brain.getStateQueue().poll());
				}
			}
		} else {
			Iterator<IItem> iterator = brain.getBody().getInventory().iterator();
			int currentIndex = 0;

			loop:while (iterator.hasNext()) {
				IItem current = iterator.next();
				switch (current.getType()) {
					case WATER_ITEM:
						if (bestIndex == -1) {
							waiting = true;
							bestIndex = currentIndex;


							break loop;
						}
						/*
						if(best == null){
							best = current;
							hungerAmount = best.getOutcome().getHunger();
						}
						else if(best.getOutcome().getHunger() < current.getOutcome().getHunger()){
							best = current;
							thirstAmount = best.getOutcome().getHunger();
						}
						*/
				}

				currentIndex++;
			}
		}
	}
}
