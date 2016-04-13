package Controller.AIStates;

import Controller.ArtificialBrain;

/**
 * Created by Tobias on 2016-03-29.
 */
public class LowEnergyState implements IState{
	private final ArtificialBrain brain;

	public LowEnergyState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		brain.queueState(brain.getSleepingState());
		brain.setState(brain.getStateQueue().poll());
		/*
		if(brain.getBody().hasHome()) {
			int homeX = brain.getBody().getHome().getX();
			int homeY = brain.getBody().getHome().getY();

			brain.findPathTo(homeX, homeY);
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getSleepingState());
			brain.setState(brain.getStateQueue().poll());
		} else {
			brain.setState(brain.getRestingState());
		}
		*/
	}
}