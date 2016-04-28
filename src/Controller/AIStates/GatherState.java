package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Model.Structures.Farm;
import Utility.Constants;
import Utility.RenderObject;

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
			System.out.println("SHOULD NEVER RUN!");
			brain.setState(brain.getIdleState());
		} else {
			switch (lowestType) {
				case MEAT_ITEM:
					brain.stackResourceToGather(IResource.ResourceType.MEAT);
					break;
				case CROPS_ITEM:
					brain.stackResourceToGather(IResource.ResourceType.CROPS);
					break;
				case FISH_ITEM:
					brain.stackResourceToGather(IResource.ResourceType.FISH);
					break;
				case WATER_ITEM:
					brain.stackResourceToGather(IResource.ResourceType.WATER);
					break;
				case WOOD_ITEM:
					brain.stackResourceToGather(IResource.ResourceType.WOOD);
					break;
				case STONE_ITEM:
					brain.stackResourceToGather(IResource.ResourceType.STONE);
					break;
				case GOLD_ITEM:
					brain.stackResourceToGather(IResource.ResourceType.GOLD);
					break;
			}

			brain.setState(brain.getGatherState());
		}
	}

	private void gatherSpecificResource(IResource.ResourceType type){
		ResourcePoint p = brain.getClosestResourcePoint(type);

		if(p == null) {
			switch (type) {
				case MEAT:
					brain.setState(brain.getHuntingState());
					break;
				case CROPS:
					brain.stackState(brain.getGatherState());
					brain.setState(brain.getWorkFarmState());
					break;
				/*case WATER:
				case FISH:
				case STONE:
				case GOLD:
				case WOOD:*/
				default:
					brain.stackState(brain.getGatherState());
					brain.stackResourceToFind((type.equals(IResource.ResourceType.FOOD)) ? IResource.ResourceType.WATER : type);
					brain.setState(brain.getFindResourceState());
					break;
			}
		} else {
			IResource.ResourceType selectType = p.getResource().getResourceType();

			brain.findPathTo(p);

			switch (selectType) {
				case MEAT:
					// TODO: Since Animals currently aren't ResourcePoints, this shouldn't run!!
					System.out.println("No action to do here...");
					break;
				case CROPS:
					brain.stackState(brain.getGatherCropsState());
					break;
				case WATER:
					// Since both Fish and Water are gathered from Lakes, some
					// 'hacks' are needed to understand what what resource should be gathered.
					if(type.equals(IResource.ResourceType.FOOD)){
						brain.stackState(brain.getGatherFishState());
					} else {
						brain.stackState(brain.getGatherWaterState());
					}
					break;
				case FISH:
					brain.stackState(brain.getGatherFishState());
					break;
				case STONE:
					brain.stackState(brain.getGatherStoneState());
					brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.THINK_PICKING);
					break;
				case GOLD:
					brain.stackState(brain.getGatherGoldState());
					brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.THINK_PICKING);
					break;
				case WOOD:
					brain.stackState(brain.getGatherWoodState());
					brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.THINK_FORESTING);
					break;
			}

			brain.setState(brain.getMovingState());
		}
	}


	/**
	 * Will try to gather a resource at all costs. This could be a predetermined
	 * resource, if 'nextResourceToGather' is set in the brain, or a resource
	 * the character has the smallest amount of.
	 */
	@Override
	public void run() {
		IResource.ResourceType resource = brain.getNextResourceToGather();

		if(resource == null) {
			gatherInterestingResource();
		}else{
			gatherSpecificResource(resource);
		}
	}
}