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
			*Interact for a set time
			*Increase social need
			*Increase/Decrease relation with character
			* Go back to idleState
		 */
	}
}