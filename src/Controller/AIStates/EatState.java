package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.Character;
import Model.ICharacterHandle;
import Model.IItem;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-03-29.
 */
public class EatState implements IState{
	private ICharacterHandle body;
	private final ArtificialBrain brain;

	public EatState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		Iterator<IItem> iterator = body.getInventory().iterator();
		IItem best = null;
		loop:while(iterator.hasNext()) {
			IItem current = iterator.next();
			switch (current.getType()) {
				case FISH_ITEM: //TODO: CHANGE TO FOOD_ITEM
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
		}
		best.consumed((Character)body);
		brain.setState(brain.getIdleState());
	}
}
