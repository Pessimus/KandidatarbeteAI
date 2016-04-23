package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICollidable;
import Model.ResourcePoint;
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
		boolean foundObject = false;

		//Constants.PATHFINDER_OBJECT.getMask()[x/Constants.PATHFINDER_GRID_SIZE][y/Constants.PATHFINDER_GRID_SIZE]

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
			//Have a resource to look for
			//System.out.println("Not found object");
			/*if(currentPath != brain.getPathStack().peek()){
				brain.setState(brain.getIdleState());
				return;
			} else{
				currentPath = brain.getPathStack().poll();
			}*/

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
