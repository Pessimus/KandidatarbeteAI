package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class TradeState implements IState{
	private final ArtificialBrain brain;

	public TradeState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {


		brain.setState(brain.getIdleState());
	}
}
