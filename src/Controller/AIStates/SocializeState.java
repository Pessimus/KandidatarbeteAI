package Controller.AIStates;

import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SocializeState implements IState{
	private ICharacterHandle body;

	public SocializeState(ICharacterHandle character){
		body = character;
	}

	@Override
	public void setBody() {

	}

	@Override
	public void run() {

	}
}