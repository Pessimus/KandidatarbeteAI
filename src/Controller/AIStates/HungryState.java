package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
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
		Iterator<IItem> iterator = brain.getBody().getInventory().iterator();
		IItem best = null;
		int hungerAmount = -1;
		RenderObject closestCrop = null;
		double cdx = 0;
		double cdy = 0;
		double odx= 0;
		double ody = 0;

		loop:while(iterator.hasNext()){
			IItem current = iterator.next();
			switch (current.getType()) {
				case FISH_ITEM:
				case MEAT_ITEM:
				case CROPS_ITEM: //TODO: CHANGE FISH TO FOOD
					best = current;
					break loop;
			}
		}

		if(best == null){
			brain.stackState(brain.getEatState());
			brain.stackResourceToGather(IResource.ResourceType.FOOD);
			brain.stackState(brain.getGatherState());
			brain.setState(brain.getStateQueue().poll());
		}
		else{
			brain.setState(brain.getEatState());
		}
	}
}
