package Controller.AIStates;

import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SleepState implements IState{
	private ICharacterHandle body;

	public SleepState(ICharacterHandle character){
		body = character;
	}

	@Override
	public void setBody(ICharacterHandle character) {

	}

	@Override
	public boolean run() {

	}
}