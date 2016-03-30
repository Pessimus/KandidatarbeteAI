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
					/*
					//Do we have a current best item?
					if(best == null){
					//Assign current item to best
						best = current;
						//Check the thirstAmount we get back from drinking our current item
						thirstAmount = best.getOutcome().getThirst();
					}
					//

					else if(current.getOutcome().getThirst() > best.getOutcome().getThirst()){
						best = current;
						thirstAmount = best.getOutcome().getThirst();
					}
					*/
					break loop;
			}
		}
		
		best.consumed((Character)body);
		brain.setState(brain.getIdleState());
	}
}
