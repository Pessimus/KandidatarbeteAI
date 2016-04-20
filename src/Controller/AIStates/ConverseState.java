package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.Character;
import Model.ICharacterHandle;
import Model.ICollidable;
import Utility.Constants;

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
		if(brain.getCurrentInteraction().isCharacterActive(brain.getBody().getKey()))
			if(brain.getCurrentInteraction().talk()){
				brain.getCurrentInteraction().endInteraction();
			} else{
				brain.stackState(this);
				double dx = brain.getInteractionCharacter().getX() - brain.getBody().getX();
				double dy = brain.getInteractionCharacter().getY() - brain.getBody().getY();
				double signX = Math.signum(dx);
				double signY = Math.signum(dy);

				brain.findPathTo((int)(brain.getBody().getX() + signX * (Math.abs(dx) - Constants.CHARACTER_COLLISION_RADIUS)), (int)(brain.getBody().getY() + signY * (Math.abs(dy) - Constants.CHARACTER_COLLISION_RADIUS)));
				brain.stackState(brain.getMovingState());
			}

		brain.setState(brain.getIdleState());
	}
}