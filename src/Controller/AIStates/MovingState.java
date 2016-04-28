package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICharacterHandle;
import Utility.Constants;
import Utility.RenderObject;

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

	@Override
	public void run() {
		if (!brain.getStateQueue().isEmpty()) {
			if (brain.getStateQueue().peek().getClass().equals(GatherWoodState.class)) {
				brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.THINK_FORESTING);
			} else if (brain.getStateQueue().peek().getClass().equals(GatherStoneState.class)) {
				brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.THINK_PICKING);
			}
		}
		LinkedList<PathStep> tempPath = brain.getNextPath();

		if(tempPath != null){
			if(!tempPath.isEmpty()){
				tempPath.getFirst().stepTowards(brain.getBody());
				if(tempPath.getFirst().reached(brain.getBody())){
					tempPath.removeFirst();
				}
			} else{
				brain.getPathStack().remove();
				brain.setState(brain.getIdleState());
			}
		} else{
			brain.getPathStack().remove();
			brain.setState(brain.getIdleState());
		}
	}
}
