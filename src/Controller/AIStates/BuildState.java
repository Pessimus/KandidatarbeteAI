package Controller.AIStates;

import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class BuildState implements IState{
	private ICharacterHandle body;

	public BuildState(ICharacterHandle character){
		body = character;
	}

	@Override
	public void setBody(ICharacterHandle character) {

	}

	@Override
	public boolean run() {

	}
}
