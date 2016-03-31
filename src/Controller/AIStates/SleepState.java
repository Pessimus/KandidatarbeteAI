package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SleepState implements IState{
	private final ArtificialBrain brain;

	public SleepState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		/*if(brain.getBody().isHome()) {
			brain.sleep;
		}
		 */
	}
}