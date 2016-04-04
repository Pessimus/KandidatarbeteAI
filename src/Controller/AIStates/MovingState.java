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
		LinkedList<PathStep> tempPath = brain.getPath();

		if(tempPath != null){
			if(!tempPath.isEmpty()){
				tempPath.getFirst().stepTowards(brain.getBody());
				if(tempPath.getFirst().reached(brain.getBody())){
					tempPath.removeFirst();
				}
			}
			else{
				if(brain.getStateQueue().isEmpty()) {
					brain.setState(brain.getIdleState());
				}
				else{
					brain.setState(brain.getStateQueue().poll());
				}
			}
		}
		else{
			if(brain.getStateQueue().isEmpty()) {
				brain.setState(brain.getIdleState());
			}
			else{
				brain.setState(brain.getStateQueue().poll());
			}
		}
	}
}
