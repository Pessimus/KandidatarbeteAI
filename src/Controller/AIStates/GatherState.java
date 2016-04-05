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

	/**
	 * If there is no explicit resource to be gathered next,
	 * find the most interesting resource to gather, and queue
	 * states in order to gather that resource.
	 */
	private void gatherInterestingResource(){
		List<IItem> inventory = brain.getBody().getInventory();
		IItem.Type lowestType = null;
		int lowestAmount = 0;

		HashMap<IItem.Type, Integer> itemMap = new HashMap<>();

		for (IItem item : inventory) {
			if (itemMap.get(item.getType()) != null) {
				itemMap.put(item.getType(), itemMap.get(item.getType()) + item.getAmount());
			} else {
				itemMap.put(item.getType(), itemMap.get(item.getType()));
			}
		}

		for (IItem.Type type : IItem.Type.values()) {
			if (itemMap.get(type) == null) {
				itemMap.put(type, 0);
				lowestType = type;
				lowestAmount = 0;
			} else if (itemMap.get(type) < lowestAmount) {
				lowestType = type;
				lowestAmount = itemMap.get(type);
			}
		}

		if (lowestType == null) {
			brain.queueState(brain.getIdleState());
		} else {
			switch (lowestType) {
				case MEAT_ITEM:
					brain.setNextResourceToGather(IResource.ResourceType.MEAT);
					break;
				case FISH_ITEM:
					brain.setNextResourceToGather(IResource.ResourceType.FISH);
					break;
				case WATER_ITEM:
					brain.setNextResourceToGather(IResource.ResourceType.WATER);
					break;
				case WOOD_ITEM:
					brain.setNextResourceToGather(IResource.ResourceType.WOOD);
					break;
				case STONE_ITEM:
					brain.setNextResourceToGather(IResource.ResourceType.STONE);
					break;
				case GOLD_ITEM:
					brain.setNextResourceToGather(IResource.ResourceType.GOLD);
					break;
				case CROPS_ITEM:
					brain.setNextResourceToGather(IResource.ResourceType.CROPS);
					break;
			}

			brain.queueState(brain.getGatherState());
		}
	}


	/**
	 * Will try to gather a resource at all costs. This could be a predetermined
	 * resource, if 'nextResourceToGather' is set in the brain, or a resource
	 * the character has the smallest amount of.
	 */
	@Override
	public void run() {
		if(brain.getNextResourceToGather() == null) {
			gatherInterestingResource();
		}else{
			Point p = brain.getClosestResourcePoint(brain.getNextResourceToGather());;
			switch (brain.getNextResourceToGather()){
				case FOOD:
					// TODO
				case MEAT:
					if(p == null){
						Random r = new Random();
						p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherState());
						brain.stackState(brain.getMovingState());
					} else {
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherMeatState());
						brain.stackState(brain.getMovingState());
					}
					break;
				case FISH:
					if(p == null){
						Random r = new Random();
						p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherState());
						brain.stackState(brain.getMovingState());
					} else {
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherFishState());
						brain.stackState(brain.getMovingState());
					}
					break;
				case CROPS:
					if(p == null){
						Random r = new Random();
						p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherState());
						brain.stackState(brain.getMovingState());
					} else {
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherCropsState());
						brain.stackState(brain.getMovingState());
					}
					break;
				case WATER:
					if(p == null){
						Random r = new Random();
						p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherState());
						brain.stackState(brain.getMovingState());
					} else {
						System.out.println(p);
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherWaterState());
						brain.stackState(brain.getMovingState());
					}
					break;
				case STONE:
					if(p == null){
						Random r = new Random();
						p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherState());
						brain.stackState(brain.getMovingState());
					} else {
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherStoneState());
						brain.stackState(brain.getMovingState());
					}
					break;
				case GOLD:
					if(p == null){
						Random r = new Random();
						p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherState());
						brain.stackState(brain.getMovingState());
					} else {
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherGoldState());
						brain.stackState(brain.getMovingState());
					}
					break;
				case WOOD:
					if(p == null){
						Random r = new Random();
						p = new Point(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherState());
						brain.stackState(brain.getMovingState());
					} else {
						brain.findPathTo(p.getX(), p.getY());
						brain.stackState(brain.getGatherWoodState());
						brain.stackState(brain.getMovingState());
					}
					break;
			}
		}
		brain.setNextResourceToGather(null);
		brain.setState(brain.getStateQueue().poll());
	}
}