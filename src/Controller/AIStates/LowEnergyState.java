package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;

/**
 * Created by Tobias on 2016-03-29.
 */
public class LowEnergyState implements IState{
	private final ArtificialBrain brain;

	public LowEnergyState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		if(brain.getBody().getNeeds()[2] < 95) {
			brain.stackState(this);

			if (brain.getBody().hasHome()) {
				ICollidable home = brain.getBody().getHome();

				brain.findPathTo(home);
				brain.stackState(brain.getSleepingState());
				brain.setState(brain.getMovingState());
			} else {
				brain.setState(brain.getRestingState());
			}
		} else {
			brain.setState(brain.getIdleState());
		}
	}
}