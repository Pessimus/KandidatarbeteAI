package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Utility.RenderObject;

import java.awt.*;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.*;

/**
 * Created by Tobias on 2016-03-29.
 */
public class BuildState implements IState{
	private final ArtificialBrain brain;
	RenderObject closestWood = null;
	RenderObject closestStone = null;
	double cdx = 0;
	double cdy = 0;
	double odx= 0;
	double ody = 0;

	public BuildState(ArtificialBrain brain){
		this.brain = brain;
	}

	private EnumMap<IItem.Type, Integer> getRemainingMaterials(IStructure.StructureType structureType){
		List<IItem> cost = StructureFactory.getCost(structureType);

		// Works if the inventory is of the type Inventory (and not List<IItem>)
		/*for(IItem itemCost : cost){
			if(!brain.getBody().getInventory().contains(itemCost)){
				remainingItems.add(itemCost);
			}
		}*/

		EnumMap<IItem.Type, Integer> map = new EnumMap<>(IItem.Type.class);

		for (IItem item : cost) {
			boolean add = true;
			int haveAmount = 0;
			for (IItem tmpItem : brain.getBody().getInventory()) {
				if (tmpItem.getType().equals(item.getType()) && tmpItem.getAmount() >= item.getAmount()) {
					add = false;

				}
			}

			if(add) {
				if(map.containsKey(item.getType())) {
					map.put(item.getType(), map.get(item.getType()) + item.getAmount());
				} else{
					map.put(item.getType(), item.getAmount());
				}
			}
		}

		return map;
	}

	@Override
	public void run() {
		/*
			*We know what to build when we enter this state
			* Check: Can we build? Do we have the resources?
			* Check: Are we in a spot where it would make sense to build this structure?
			* Checks passed? Enter the correct buildState and start building
		 */

		//CHECK WHAT MATERIALS WE NEED FOR nextStructureToBuild, DO WE HAVE THEM?
		IStructure.StructureType type = brain.peekStructureStack();
		EnumMap<IItem.Type, Integer> remaining = getRemainingMaterials(type);

		if(!remaining.isEmpty()){
			//NO?
			//FIND OUT WHAT WE ARE MISSING AND GO GATHER
			brain.stackState(this);
			for(IItem.Type item : remaining.keySet()){
				switch (item) {
					case MEAT_ITEM:
						brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.MEAT, remaining.get(item)));
						break;
					case FISH_ITEM:
						brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.FISH, remaining.get(item)));
						break;
					case WATER_ITEM:
						brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.WATER, remaining.get(item)));
						break;
					case WOOD_ITEM:
						brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.WOOD, remaining.get(item)));
						break;
					case STONE_ITEM:
						brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.STONE, remaining.get(item)));
						break;
					case GOLD_ITEM:
						brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.GOLD, remaining.get(item)));
						break;
					case CROPS_ITEM:
						brain.stackResourceToGather(new ResourceTuple(IResource.ResourceType.CROPS, remaining.get(item)));
						break;
				}

				brain.stackState(brain.getGatherState());
			}
		} else{
			brain.stackState(brain.getBuildingState());

			if(brain.getBody().hasHome()) {
				//brain.findPathTo(brain.getBody().getHome());
				brain.stackResource(brain.getBody().getHome());
				brain.stackState(brain.getMovingState());
			}
		}

		brain.setState(brain.getIdleState());
	}
}
