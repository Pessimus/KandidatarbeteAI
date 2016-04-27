package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICollidable;
import Model.IResource;
import Model.IStructure;
import Model.ResourcePoint;
import Model.Structures.Farm;
import Utility.Constants;

import java.util.LinkedList;

/**
 * Created by Tobias on 2016-04-23.
 */
public class FindResourceState implements IState {
	private final ArtificialBrain brain;

	private LinkedList<PathStep> currentPath = null;
	private double currentDirection = 0;

	public FindResourceState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		for(IStructure structure : brain.getStructureMemory()) {
			if (structure.getStructureType() == IStructure.StructureType.FARM && brain.getStructureStack().peek() == IStructure.StructureType.FARM) {
				brain.getStructureStack().removeFirst();
				for (IState state : brain.getStateQueue()) {
					if (state == brain.getBuildState() || state == brain.getGatherState()) {
						brain.getStateQueue().remove(state);
					}
				}
				for (IResource.ResourceType resource : brain.getResourceToFindStack()) {
					if (resource == IResource.ResourceType.WOOD || resource == IResource.ResourceType.STONE) {
						brain.getResourceToFindStack().remove(resource);
					}
				}

				for (IResource.ResourceType resource : brain.getGatherStack()) {
					if (resource == IResource.ResourceType.WOOD || resource == IResource.ResourceType.STONE) {
						brain.getGatherStack().remove(resource);
					}
				}
				brain.setState(brain.getIdleState());
			}
		}
		boolean foundObject = false;

		for(ICollidable o : brain.getBody().getSurroundings()){
			if(o.getClass().equals(ResourcePoint.class)){
				ResourcePoint p = (ResourcePoint) o;
				if(p.getResource().getResourceType().equals(brain.getResourceToFindStack().peek())) {
					foundObject = true;
					brain.getResourceToFindStack().pop();
					break;
				}
			}
		}

		if(!foundObject) {
			if (currentPath != null) {
				//System.out.println("'currentPath != null");
				if (!currentPath.isEmpty()) {
					currentPath.getFirst().stepTowards(brain.getBody());
					if (currentPath.getFirst().reached(brain.getBody())) {
						currentPath.removeFirst();
					}
				} else{
					currentDirection += 25 * (1 - (Math.random() * 2));
					brain.findPathTo(brain.getBody().getX() + 75 * Math.sin(Math.toRadians(currentDirection)), brain.getBody().getY() + 50 * Math.cos(Math.toRadians(currentDirection)));
					currentPath = brain.getPathStack().poll();
				}
			} else{
				currentDirection += 25 * (1 - (Math.random() * 2));
				brain.findPathTo(brain.getBody().getX() + 75 * Math.sin(Math.toRadians(currentDirection)), brain.getBody().getY() + 50 * Math.cos(Math.toRadians(currentDirection)));
				currentPath = brain.getPathStack().poll();
			}
		} else {
			currentPath = null;
			brain.setState(brain.getIdleState());
		}
	}
}
