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

	@Override
	public void run() {
		Point p = brain.getPointStack().peek();

		if(currentPoint != p){
			currentPath = null;
			currentPoint = null;
		}

		if(currentPoint == null){
			if(p != null){
				currentPoint = p;
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
	}
}
