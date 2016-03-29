package Controller.AIStates;

import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
class HungryState implements IState {
	private ICharacterHandle body;

	public HungryState(ICharacterHandle character){
		body = character;
	}

	@Override
	public void setBody() {

	}

	@Override
	public void run() {

	}
}
