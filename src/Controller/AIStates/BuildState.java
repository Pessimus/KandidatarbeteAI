package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Utility.Constants;
import Utility.RenderObject;

import java.util.LinkedList;
import java.util.List;

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

	private List<IItem> getRemainingMaterials(IStructure.StructureType structureType){
		List<IItem> cost = StructureFactory.getCost(structureType);

		List<IItem> remainingItems = new LinkedList<>();

		// Works if the inventory is of the type Inventory (and not List<IItem>)
		/*for(IItem itemCost : cost){
			if(!brain.getBody().getInventory().contains(itemCost)){
				remainingItems.add(itemCost);
			}
		}*/

		for (IItem item : cost) {
			boolean add = true;
			for (IItem tmpItem : brain.getBody().getInventory()) {
				if (tmpItem.getType().equals(item.getType()) && tmpItem.getAmount() >= item.getAmount()) {
					add = false;
				}
			}

			if(add) {
				remainingItems.add(item);
			}
		}

		return remainingItems;
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
		IStructure.StructureType type = brain.getNextStructureToBuild();
		List<IItem> remaining = getRemainingMaterials(type);

		if(!remaining.isEmpty()){
			//NO?
			//FIND OUT WHAT WE ARE MISSING AND GO GATHER
			brain.stackState(this);
			for(IItem item : remaining){
				switch (item.getType()) {
					case MEAT_ITEM:
						brain.stackResourceToGather(IResource.ResourceType.MEAT);
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
					case CROPS_ITEM:
						brain.stackResourceToGather(IResource.ResourceType.CROPS);
						break;
				}

				brain.stackState(brain.getGatherState());
			}
		} else{
			brain.stackState(brain.getBuildingState());

			if(brain.getBody().hasHome()) {
				brain.findPathTo(brain.getBody().getHome());
				brain.stackState(brain.getMovingState());
			}
		}

		brain.setState(brain.getIdleState());
	}
}
