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
			brain.stackResourceToGather(IResource.ResourceType.FOOD);
			brain.stackState(brain.getEatState());
			brain.stackState(brain.getGatherState());
			brain.setState(brain.getStateQueue().poll());

			/*
			// TODO: Pathfinding to nearest/best food-resource
			// TODO: Queue MovingState correctly
			//TODO: Add different kinds of foods to look for and gather
			for(RenderObject o : brain.map.getRenderObjects()) {
				if(o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.CROPS)) {
					if (closestCrop == null) {
						closestCrop = o;
					} else {
						cdx = Math.abs(brain.getBody().getX() - closestCrop.getX());
						cdy = Math.abs(brain.getBody().getY() - closestCrop.getY());
						odx = Math.abs(brain.getBody().getX() - o.getX());
						ody = Math.abs(brain.getBody().getY() - o.getY());
						if (Math.sqrt(cdx) + Math.sqrt(cdy) > Math.sqrt(odx) + Math.sqrt(ody))
							closestCrop = o;
					}
				}

			}
			brain.findPathTo(closestCrop.getX(), closestCrop.getY());
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getGatherCropsState());
			brain.queueState(brain.getEatState());
			brain.setState(brain.getStateQueue().poll());
			*/
		}
		else{
			brain.setState(brain.getEatState());
		}
	}
}
