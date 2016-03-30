package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class BuildHouseState implements IState{
	private ICharacterHandle body;
	private final AbstractBrain brain;

	public BuildHouseState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		;
	}
}