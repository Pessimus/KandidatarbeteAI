package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Controller.CharacterAction;
import Controller.PathStep;
import Model.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

		loop:while(iterator.hasNext()){
			IItem current = iterator.next();
			switch (current.getType()) {
				case FISH_ITEM: //TODO: CHANGE FISH TO FOOD
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
			// TODO: Pathfinding to nearest/best food-resource
			// TODO: Queue MovingState correctly
			for(RenderObject o : brain.map.getRenderObjects()){
				if(o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.CROPS)){
					brain.setPath(Constants.PATHFINDER_OBJECT.getPath(brain.getBody().getX(), brain.getBody().getY(), o.getX(), o.getY()));
					brain.queueState(brain.getMovingState());
					brain.setNextResourceToGather(IResource.ResourceType.CROPS);
					brain.queueState(brain.getGatherCropsState());
					brain.setState(brain.getStateQueue().poll());
					break;
				}
			}
			/*brain.setPath();
			brain.queueState(brain.getMovingState());
			brain.setNextResourceToGather(IResource.ResourceType.CROPS);
			brain.queueState(brain.getGatherCropsState());*/
		}
		else{
			brain.setState(brain.getEatState());
		}
	}
}
