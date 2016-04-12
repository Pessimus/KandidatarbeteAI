package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.*;
import Toolkit.RenderObject;

import java.awt.*;
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

		for(IItem itemCost : cost){
			if(!brain.getBody().getInventory().contains(itemCost)){
				remainingItems.add(itemCost);
				/*
				canPay = false;
				break;
				*/
			}
		}

		return remainingItems;

		/*
		for (IStructure.StructureBuildingMaterialTuple tuple : structure.getBuildingMaterials()){
			for(IItem item : brain.getBody().getInventory()){
				if(item.getType().equals(tuple.getResourceType()) && item.getAmount() >= tuple.getResourceAmount()){
					returns = true;
				}
			}
			if(returns == false){
				if (tuple.getResourceType() == IItem.Type.WOOD_ITEM) {
					brain.setNextResourceToGather(IResource.ResourceType.WOOD);
				}
				if (tuple.getResourceType() == IItem.Type.STONE_ITEM) {
					brain.setNextResourceToGather(IResource.ResourceType.STONE);
				}
				brain.queueState(brain.getGatherState());
				return false;
			} else{
				returns = false;
			}
		}

		return true;*/
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
			//YES?
				//ENTER CORRECT BUILD STATE
			//NO?
				//FIND OUT WHAT WE ARE MISSING AND GO GATHER

		// IF WE DONT HAVE ENOUGH WOOD
			//FIND WOOD AND GATHER
		
			if(!(getRemainingMaterials(brain.getNextStructureToBuild()).isEmpty())) {
				// TODO: Change so this works for all resource-types
				//StructureFactory.getCost()
				brain.setNextResourceToGather(IResource.ResourceType.WOOD);
				Point p = brain.getClosestResourcePoint(IResource.ResourceType.WOOD);
				brain.findPathTo(p.getX(), p.getY());
				brain.queueState(brain.getMovingState());
				brain.queueState(brain.getGatherWoodState());
				brain.setState(brain.getStateQueue().poll());
			}


			/*for(RenderObject o : brain.map.getRenderObjects()) {
				if((o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.WOOD)) ||(o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.WOOD2))) {
					if (closestWood == null) {
						closestWood = o;
					} else {
						cdx = Math.abs(brain.getBody().getX() - closestWood.getX());
						cdy = Math.abs(brain.getBody().getY() - closestWood.getY());
						odx = Math.abs(brain.getBody().getX() - o.getX());
						ody = Math.abs(brain.getBody().getY() - o.getY());
						if (Math.sqrt(cdx) + Math.sqrt(cdy) > Math.sqrt(odx) + Math.sqrt(ody))
							closestWood = o;
					}
				}
			}
			brain.findPathTo(closestWood.getX(), closestWood.getY());
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getGatherWoodState());
			brain.setState(brain.getStateQueue().poll());*/

		// IF WE DONT HAVE ENOUGH STONE
			//FIND STONE AND GATHER

		for(RenderObject o : brain.map.getRenderObjects()) {
			if((o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.STONE)) ||(o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.STONE2 ))) {
				if (closestStone == null) {
					closestStone = o;
				} else {
					cdx = Math.abs(brain.getBody().getX() - closestStone.getX());
					cdy = Math.abs(brain.getBody().getY() - closestStone.getY());
					odx = Math.abs(brain.getBody().getX() - o.getX());
					ody = Math.abs(brain.getBody().getY() - o.getY());
					if (Math.sqrt(cdx) + Math.sqrt(cdy) > Math.sqrt(odx) + Math.sqrt(ody))
						closestStone = o;
				}
			}
		}
		brain.findPathTo(closestStone.getX(), closestStone.getY());
		brain.queueState(brain.getMovingState());
		brain.queueState(brain.getGatherWoodState());
		brain.setState(brain.getStateQueue().poll());

		}
}
