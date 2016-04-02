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

	public SleepingState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		/*
		if(brain.getBody().isHome()) {
			brain.getBody().sleep();
		}
		*/
	}
}