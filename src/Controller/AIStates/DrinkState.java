package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class DrinkState implements IState{
	private ICharacterHandle body;

	private final AbstractBrain brain;

	public DrinkState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void setBody(ICharacterHandle character) {

	}

	@Override
	public void run() {
		;
	}
}
