package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.Character;
import Model.ICharacterHandle;
import Model.ICollidable;
import Utility.Constants;

import java.awt.*;
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
		if(brain.getCurrentInteraction() != null) {
			if (brain.getCurrentInteraction().isCharacterActive(brain.getBody().hashCode()) && brain.getCurrentInteraction().isActive()) {
				if (brain.getCurrentInteraction().talk()) {
					brain.getCurrentInteraction().endInteraction();
				} else {
					brain.stackState(this);
					double dx = Math.abs(brain.getInteractionCharacter().getX() - brain.getBody().getX());
					double dy = Math.abs(brain.getInteractionCharacter().getY() - brain.getBody().getY());

					brain.stackPoint(new Point((int) (brain.getBody().getX() + dx), (int) (brain.getBody().getY() + dy)));
					brain.stackState(brain.getMovingState());
				}
			}
		}

		brain.setState(brain.getIdleState());
	}
}