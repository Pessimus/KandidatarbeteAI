package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.Character;
import Model.ICollidable;
import java.util.LinkedList;

/**
 * Created by Tobias on 2016-04-23.
 */
public class FindCharacterState implements IState {
	private final ArtificialBrain brain;

	private LinkedList<PathStep> currentPath = null;
	private double currentDirection = 0;

	public FindCharacterState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		boolean foundObject = false;
		for(ICollidable o : brain.getBody().getSurroundings()){
			if(o.getClass().equals(Character.class) && !brain.getBlackList().contains(o)){
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
			brain.stackState(brain.getFollowState());
			brain.setState(brain.getIdleState());
		}
	}

	public void clearPath () {
		currentPath = null;
	}


}
