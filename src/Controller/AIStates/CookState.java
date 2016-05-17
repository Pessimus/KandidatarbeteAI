package Controller.AIStates;

import Controller.ArtificialBrain;

/**
 * Created by Tobias on 2016-03-29.
 */
public class CookState implements IState{
	private final ArtificialBrain brain;

	public CookState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		/*
			*Check: Are we near a place where we can cook?
			*Yes?
			 	*Turn one piece of raw food into one piece of cooked food
			 *No?
			  	*Find nearest cooking place
			  	*Walk there
			 *Go back to previous state/idle
		 */
	}
}