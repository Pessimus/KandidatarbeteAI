package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICharacterHandle;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Tobias on 2016-03-31.
 */
public class MovingState implements IState {

	private final ArtificialBrain brain;

	public MovingState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		Queue<PathStep> tempPath = brain.getPath();

		if(tempPath != null){
			if(!tempPath.isEmpty()){
				tempPath.poll().stepTowards(brain.getBody());
			}
			else{
				brain.setPath(null);
				if(brain.getStateQueue().isEmpty()) {
					brain.setState(brain.getIdleState());
				}
				else{
					brain.getStateQueue().poll();
				}
			}
		}
		else{
			//FOR TESTING PURPOSES: MAKES THE AI FIND A NEW PATH TO A RANDOM LOCATION IF IT HAS NO PATH CURRENTLY.
			brain.getNewPath(Math.random()*9600, Math.random()*9600);
			/* COMMENTED OUT TO TEST AI PATHFINDING
			if(brain.getStateQueue().isEmpty()) {
				brain.setState(brain.getIdleState());
			}
			else{
				brain.getStateQueue().poll();
			}
			*/
		}
	}
}
