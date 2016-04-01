package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Toolkit.RenderObject;

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
				case CROPS_ITEM: //TODO: CHANGE FISH TO FOOD
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
			brain.setPath(Constants.PATHFINDER_OBJECT.getPath(brain.getBody().getX(), brain.getBody().getY(), closestCrop.getX(), closestCrop.getY()));
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getGatherCropsState());
			brain.queueState(brain.getEatState());
			brain.setState(brain.getStateQueue().poll());
			/*brain.setPath();
			brain.queueState(brain.getMovingState());
			brain.setNextResourceToGather(IResource.ResourceType.CROPS);
			brain.queueState(brain.getGatherCropsState());*/
		}
		else{
			brain.setState(brain.getEatState());
		}
		/*
		if(pathToResource == null) {
			Iterator<IItem> iterator = brain.getBody().getInventory().iterator();
			IItem best = null;
			int hungerAmount = -1;

			loop:while(iterator.hasNext()){
				IItem current = iterator.next();
				switch (current.getType()) {
					case FISH_ITEM: //TODO: CHANGE FISH TO FOOD
						best = current;
					if(best == null){
						best = current;
						thirstAmount = best.getOutcome().getThirst();
					}
					else if(best.getOutcome().getHunger() < current.getOutcome().getHunger()){
						best = current;
						thirstAmount = best.getOutcome().getHunger();
					}
						break loop;
				}
			}

			if(best == null){
				// TODO: Pathfinding to nearest/best food-resource
				// TODO: Enter GatherMaterialState
				// TODO: Queue MovingState
				// TODO: Queue GatherState

			}
			else{
				brain.setState(brain.getEatState());
			}

		}
					*/
	}
}
