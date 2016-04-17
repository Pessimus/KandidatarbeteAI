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

		/*
		if(!brain.getBody().hasHome()){
			if(hasMaterials(Structure.HOUSE)){
				for(IResource.ResourceType type :
			}
		}
		*/

		//CHECK WHAT MATERIALS WE NEED FOR nextStructureToBuild, DO WE HAVE THEM?
		IStructure.StructureType type = brain.getNextStructureToBuild();
		List<IItem> remaining = getRemainingMaterials(type);

		if(!remaining.isEmpty()){
			//NO?
			//FIND OUT WHAT WE ARE MISSING AND GO GATHER
			brain.stackState(this);
			System.out.println("REMAINING:");
			for(IItem item : remaining){
				System.out.println(item.getType());
				switch (item.getType()) {
					case MEAT_ITEM:
						/*brain.stackResourceToGather(IResource.ResourceType.MEAT);
						brain.stackState(brain.getGatherState());
						break;*/
					case FISH_ITEM:
						brain.stackResourceToGather(IResource.ResourceType.FISH);
						brain.stackState(brain.getGatherState());
						break;
					case WATER_ITEM:
						//brain.setNextResourceToGather(IResource.ResourceType.WATER);
						brain.stackResourceToGather(IResource.ResourceType.WATER);
						brain.stackState(brain.getGatherState());
						break;
					case WOOD_ITEM:
						brain.stackResourceToGather(IResource.ResourceType.WOOD);
						brain.stackState(brain.getGatherState());
						break;
					case STONE_ITEM:
						brain.stackResourceToGather(IResource.ResourceType.STONE);
						brain.stackState(brain.getGatherState());
						break;
					case GOLD_ITEM:
						/*brain.stackResourceToGather(IResource.ResourceType.GOLD);
						brain.stackState(brain.getGatherState());
						break;*/
					case CROPS_ITEM:
						brain.stackResourceToGather(IResource.ResourceType.CROPS);
						brain.stackState(brain.getGatherState());
						break;
				}
			}
		} else{
			//YES?
			//ENTER CORRECT BUILD STATE
			// TODO: Check if it's possible to build the structure here, otherwise move!

			/*int x = (int)brain.getBody().getX();
			int y = (int)brain.getBody().getY();

			boolean[][] mask = Constants.PATHFINDER_OBJECT.getMask();
			boolean canBuild = true;

			int collision = 0;

			switch (brain.getStructureStack().peek()){
				case FARM:
					collision = (int) Constants.FARM_COLLISION_RADIUS;
					break;
				case HOUSE:
					collision = (int) Constants.HOUSE_COLLISION_RADIUS;
					break;
				case STOCKPILE:
					collision = (int) Constants.STOCKPILE_COLLISION_RADIUS;
					break;
			}

			firstLoop:
			for (int i = x - collision; i <= x + collision; i++) {
				for (int j = y - collision; j <= y + collision; j++) {
					if (!mask[i/Constants.PATHFINDER_GRID_SIZE][j/Constants.PATHFINDER_GRID_SIZE]) {
						canBuild = false;
						break firstLoop;
					}
				}
			}*/

			brain.stackState(brain.getBuildingState());

			if(brain.getBody().hasHome()) {
				brain.findPathTo(brain.getBody().getHome());
				brain.stackState(brain.getMovingState());
			}
		}

		if(brain.getStateQueue().isEmpty()) {
			brain.setState(brain.getIdleState());
		}
		else{
			brain.setState(brain.getStateQueue().poll());
		}
	}
}
