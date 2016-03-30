package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICharacterHandle;
import Model.IItem;
import Model.IResource;
import Model.ResourcePoint;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class ThirstyState implements IState{
	private ICharacterHandle body;
	private final ArtificialBrain brain;

	private List<PathStep> pathToResource;

	public ThirstyState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		if(pathToResource == null) {
			Iterator<IItem> iterator = body.getInventory().iterator();
			IItem best = null;
			int thirstAmount = -1;

			loop:while(iterator.hasNext()){
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

			if(best == null){
				// TODO: Pathfinding to nearest/best water-resource
			}
			else{
				brain.setState(brain.getDrinkState());
			}

		}
	}
}