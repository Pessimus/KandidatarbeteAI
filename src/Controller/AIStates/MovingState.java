package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICharacterHandle;
import Utility.Constants;

import java.awt.*;
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

	private LinkedList<PathStep> currentPath = null;
	private Point currentPoint = null;

	/*@Override
	public void run() {
		Point p = brain.getPointStack().peek();

		if(currentPoint != brain.getPointStack().peek()){
			currentPath = null;
			currentPoint = null;
		}

		if(currentPoint == null){
			if(!brain.getPointStack().isEmpty()){
				currentPoint = brain.getPointStack().peek();
			} else{
				throw new IllegalStateException("No next point in PointStack at 'run()' in MovingState");
			}
		}
		if(currentPath == null){
			currentPath = Constants.PATHFINDER_OBJECT.getPath(brain.getBody().getX(), brain.getBody().getY(), currentPoint.getX(), currentPoint.getY());
		}

		if (!currentPath.isEmpty()) {
			currentPath.getFirst().stepTowards(brain.getBody());
			if (currentPath.getFirst().reached(brain.getBody())) {
				currentPath.removeFirst();
			}
		} else {
			brain.getPointStack().remove();
			currentPoint = null;
			currentPath = null;
		}

		brain.setState(brain.getIdleState());
	}*/

	@Override
	public void run() {
		LinkedList<PathStep> tempPath = brain.getNextPath();

		if(tempPath != null){
			if(!tempPath.isEmpty()){
				tempPath.getFirst().stepTowards(brain.getBody());
				if(tempPath.getFirst().reached(brain.getBody())){
					tempPath.removeFirst();
				}
			}
			else{
				brain.getPathStack().remove();
				brain.setState(brain.getIdleState());
			}
		}
		else{
			brain.setState(brain.getIdleState());
		}
	}
}
