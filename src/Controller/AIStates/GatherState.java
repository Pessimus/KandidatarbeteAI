package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class GatherState implements IState{

	private final ArtificialBrain brain;

	public GatherState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		List<IItem> inventory = brain.getBody().getInventory();
		IItem.Type lowestType = null;
		int lowestAmount = 0;

		HashMap<IItem.Type, Integer> itemMap = new HashMap<>();

		for(IItem item : inventory){
			if(itemMap.get(item.getType()) != null){
				itemMap.put(item.getType(), itemMap.get(item.getType()) + item.getAmount());
			} else{
				itemMap.put(item.getType(), itemMap.get(item.getType()));
			}
		}

		for(IItem.Type type : IItem.Type.values()){
			if(itemMap.get(type) == null){
				itemMap.put(type, 0);
				lowestType = type;
				lowestAmount = 0;
			} else if(itemMap.get(type) < lowestAmount){
				lowestType = type;
				lowestAmount = itemMap.get(type);
			}
		}

		if(lowestType == null){
			brain.queueState(brain.getIdleState());
		} else {
			switch (lowestType) {
				case MEAT_ITEM:
				case FISH_ITEM:
				case WATER_ITEM:
				case WOOD_ITEM:
				case CROPS_ITEM:
					gatherResource("crops");
					break;
				default:
					brain.queueState(brain.getIdleState());
					break;
			}
		}

		brain.setState(brain.getStateQueue().poll());
	}

	private void gatherResource(String resource){
		Point p = brain.getClosestResourcePoint(resource);
		if(p == null){
			Random r = new Random();
			p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
			brain.findPathTo(p.getX(), p.getY());
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getGatherState());
		} else {
			brain.findPathTo(p.getX(), p.getY());
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getGatherCropsState());
		}
	}
}