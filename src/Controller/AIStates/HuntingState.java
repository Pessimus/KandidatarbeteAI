package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.Animal;
import Model.Character;
import Model.ICollidable;
import Model.IResource;

import java.util.LinkedList;

/**
 * Created by Tobias on 2016-04-25.
 */
public class HuntingState implements IState{
	private final ArtificialBrain brain;

	private LinkedList<PathStep> currentPath = null;
	private double currentDirection = 0;

	public HuntingState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		boolean foundObject = false;
		for(ICollidable o : brain.getBody().getSurroundings()){
			if(o.getClass().equals(Animal.class)){
				foundObject = true;
				brain.setObjectToFollow(o);
				break;
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
			brain.stackResourceToGather(IResource.ResourceType.MEAT);
			brain.stackState(brain.getGatherMeatState());
			brain.stackState(brain.getFollowState());
			brain.setState(brain.getIdleState());
		}
	}
}
