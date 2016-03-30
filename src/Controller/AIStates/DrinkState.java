package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Model.IItem;
import Model.Character;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-03-29.
 */
public class DrinkState implements IState{
	private ICharacterHandle body;

	private final ArtificialBrain brain;

	public DrinkState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		Iterator<IItem> iterator = body.getInventory().iterator();
		IItem best = null;
		int thirstAmount = -1;

		loop:while(iterator.hasNext()) {
			IItem current = iterator.next();
			switch (current.getType()) {
				case WATER_ITEM:
					best = current;
					/*
					if(best == null){
						best = current;
						thirstAmount = best.getOutcome().getThirst();
					}
					else if(best.getOutcome().getThirst() > thirstAmount){
						best = current;
						thirstAmount = best.getOutcome().getThirst();
					}
					*/
					break loop;
			}
		}
		
		best.consumed((Character)body);
	}
}
