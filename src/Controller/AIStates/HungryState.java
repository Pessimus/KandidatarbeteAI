package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Controller.CharacterAction;
import Model.ICharacterHandle;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Tobias on 2016-03-29.
 */
public class HungryState implements IState {
	private ICharacterHandle body;
	private final AbstractBrain brain;

	private Queue<CharacterAction> actionQueue = new LinkedList<>();

	public HungryState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		if(actionQueue.isEmpty()){
		}
	}
}
