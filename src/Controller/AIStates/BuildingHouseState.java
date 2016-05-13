package Controller.AIStates;

import Controller.ArtificialBrain;

/**
 * Created by Tobias on 2016-03-29.
 */
public class BuildingHouseState implements IState{
	private final ArtificialBrain brain;

	public BuildingHouseState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		// TODO: Find out what resources are needed to build the house
		// TODO: Gather the remaining resources for the house
		// TODO: Build the house

		brain.setState(brain.getIdleState());
	}
}