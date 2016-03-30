package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class GatherMaterialState implements IState{
	private ICharacterHandle body;
	private final AbstractBrain brain;

	public GatherMaterialState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		;
	}
}
