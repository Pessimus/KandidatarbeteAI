package Controller.AIStates;

import Controller.CharacterAction;
import Model.ICharacterHandle;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Tobias on 2016-03-29.
 */
class HungryState implements IState {
	private ICharacterHandle body;
	private Queue<CharacterAction> actionQueue = new LinkedList<>();
	public HungryState(ICharacterHandle character){
		body = character;
	}

	@Override
	public void setBody(ICharacterHandle character) {

	}

	@Override
	public boolean run() {
		if(actionQueue.isEmpty()){
			return true;
		}

		return false;
	}
}
