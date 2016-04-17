package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SocializeState implements IState{
	private final ArtificialBrain brain;

	public SocializeState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {

		/* *Request interaction
			*Determine type of interaction
			* Enter correct interaction state
			* Go back to idleState
		 */
		if (brain.getStateQueue().isEmpty()) {
			brain.setState(brain.getIdleState());
		} else {
			brain.setState(brain.getStateQueue().poll());
		}
	}
}