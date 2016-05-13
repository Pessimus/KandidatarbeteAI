package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICollidable;
import Model.IResource;
import Model.IStructure;
import Model.ResourcePoint;
import Utility.RenderObject;
import java.util.LinkedList;

/**
 * Created by Tobias on 2016-04-23.
 */
public class FindResourceState implements IState {
	private final ArtificialBrain brain;

	private LinkedList<PathStep> currentPath = null;
	private LinkedList<IState> statesToThrow = new LinkedList<>();
	private LinkedList<ResourceTuple> resourcesToThrow = new LinkedList<>();
	private double currentDirection = 0;

	public FindResourceState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.EXPLORING);
		for(IStructure structure : brain.getStructureMemory()) {
			if (structure.getStructureType() == IStructure.StructureType.FARM && brain.getStructureStack().peek() == IStructure.StructureType.FARM) {
				brain.getStructureStack().removeFirst();

				//brain.getGatherStack().removeFirstOccurrence()

				/*Iterator<IState> iterator = brain.getStateQueue().iterator();

				while(iterator.hasNext()){
					IState tempState = iterator.next();
					if (tempState == brain.getBuildState() || tempState == brain.getGatherState()) {
						iterator.remove();
					}
				}

				Iterator<IResource.ResourceType> iterator2 = brain.getResourceToFindStack().iterator();

				while(iterator2.hasNext()){
					IResource.ResourceType tempType = iterator2.next();
					if (tempType == IResource.ResourceType.WOOD || tempType == IResource.ResourceType.STONE) {
						iterator2.remove();
					}
				}

				iterator2 = brain.getGatherStack().iterator();

				while(iterator2.hasNext()){
					IResource.ResourceType tempType = iterator2.next();
					if (tempType == IResource.ResourceType.WOOD || tempType == IResource.ResourceType.STONE) {
						iterator2.remove();
					}
				}*/
				for (IState state : brain.getStateQueue()) {
					if (state == brain.getBuildState() || state == brain.getGatherState()) {
							statesToThrow.add(state);
					}
				}
				if (statesToThrow != null) {
					if(!statesToThrow.isEmpty()) {
						for (IState state : statesToThrow) {
							brain.getStateQueue().remove(state);
						}
						statesToThrow.clear();
					}
				}

				for (IResource.ResourceType resource : brain.getResourceToFindStack()) {
					if (resource == IResource.ResourceType.WOOD || resource == IResource.ResourceType.STONE) {
						resourcesToThrow.add(new ResourceTuple(resource, 1));
					}
				}

				if (resourcesToThrow != null) {
					if(!resourcesToThrow.isEmpty()) {
						for (ResourceTuple resource : resourcesToThrow) {
							brain.getResourceToFindStack().remove(resource.resourceType);
						}
						resourcesToThrow.clear();
					}
				}


				for (ResourceTuple resource : brain.getGatherStack()) {
					if (resource.resourceType == IResource.ResourceType.WOOD || resource.resourceType == IResource.ResourceType.STONE) {
						resourcesToThrow.add(resource);
					}
				}

				if (resourcesToThrow != null) {
					if(!resourcesToThrow.isEmpty()) {
						for (ResourceTuple resource : resourcesToThrow) {
							brain.getGatherStack().remove(resource);
						}
					}
				}

				brain.setState(brain.getIdleState());
				currentPath = null;

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

	public void clearPath () {
		currentPath = null;
	}

}
