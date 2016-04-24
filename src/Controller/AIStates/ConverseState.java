package Controller.AIStates;

import Controller.ArtificialBrain;

/**
 * Created by Tobias on 2016-03-29.
 */
public class ConverseState implements IState {
	private final ArtificialBrain brain;

	public ConverseState(ArtificialBrain brain) {
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
			if (brain.getCurrentInteraction().isActive()) {
				if (brain.getCurrentInteraction().talk()) {
					brain.getCurrentInteraction().endInteraction();
				} else {
					double dx = brain.getInteractionCharacter().getX() - brain.getBody().getX();
					double dy = brain.getInteractionCharacter().getY() - brain.getBody().getY();

					brain.findPathTo((int) (brain.getBody().getX() + Math.abs(dx)), (int) (brain.getBody().getY() + Math.abs(dy)));
					brain.setState(brain.getMovingState());
					return;
				}
			} else {
				//brain.getCurrentInteraction().endInteraction();
			}
		}

		brain.setState(brain.getIdleState());
	}
}