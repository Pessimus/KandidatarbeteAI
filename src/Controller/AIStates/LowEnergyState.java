package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICharacterHandle;

import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class LowEnergyState implements IState{
	private final ArtificialBrain brain;
	private List<PathStep> pathToHome;

	public LowEnergyState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		/*if(brain.getBody().hasHome()) {
			int homeX = brain.getBody().getHome().getX();
			int homeY = brain.getBody().getHome().getY();

			Constants.PATHFINDER_OBJECT
			brain.setPath(Constants.PATHFINDER_OBJECT.getPath(brain.getBody().getX(), brain.getBody().getY(), homeX, homeY));
			brain.queueState(brain.getMovingState());
			brain.setState(brain.getStateQueue().poll());
		} else {
			brain.setState(brain.getSleepState());
		}
		 */
	}
}