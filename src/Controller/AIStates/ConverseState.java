package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.Character;
import Model.ICharacterHandle;
import Model.ICollidable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class ConverseState implements IState{
	private final ArtificialBrain brain;

	public ConverseState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {

		/*
			* Increase social need
			* Exchange information about the world characters between?
			* Increase/Decrease relation
			* Go back to SocializeState
		 */
		// TODO: HARDCODED TO INTERACT WITH FIRST CHARACTER IN INTERACTABLES LIST
		// TODO: Find a way to measure what character is the most interesting to talk to
		brain.getCurrentInteraction().talk();
		brain.getCurrentInteraction().endInteraction();
		brain.setState(brain.getIdleState());
	}
}