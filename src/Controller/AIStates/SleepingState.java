package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Model.ICollidable;

import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SleepingState implements IState{
	private final ArtificialBrain brain;

	private int waitUpdates = 0;
	private boolean waiting = false;
	private int bestIndex = -1;

	public SleepingState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		brain.getBody().sleep();
		//TODO: Make so that the character sleeps until his energy is restored
		brain.queueState(brain.getIdleState());
		brain.setState(brain.getStateQueue().poll());
		/*
		if(brain.getBody().isHome()) {
			brain.getBody().sleep();
		}
		*/
	}
}