package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		for(Map.Entry<IItem.Type, Integer> entry : itemMap.entrySet()){
			if(entry.getValue() != null){
				if(lowestAmount > entry.getValue()){
					lowestAmount = entry.getValue();
					lowestType = entry.getKey();
				}
			}
		}

		switch (lowestType){
			case CROPS_ITEM:
				brain.getClosestResourcePoint(IResource.ResourceType.CROPS);
				brain.queueState(brain.getGatherCropsState());
				break;
			case MEAT_ITEM:
				brain.queueState(brain.getGatherMeatState());
				break;
			case FISH_ITEM:
				brain.queueState(brain.getGatherFishState());
				break;
			case WATER_ITEM:
				brain.queueState(brain.getGatherWaterState());
				break;
			case WOOD_ITEM:
				brain.queueState(brain.getGatherWoodState());
				break;
			default:
				brain.queueState(brain.getIdleState());
				break;
		}

		brain.setState(brain.getStateQueue().poll());
	}
}