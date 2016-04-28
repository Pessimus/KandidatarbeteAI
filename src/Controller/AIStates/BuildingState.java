package Controller.AIStates;

import Controller.ArtificialBrain;
import Utility.Constants;
import Utility.RenderObject;

import java.util.Random;

/**
 * Created by Tobias on 2016-04-17.
 */
public class BuildingState implements IState {

	private final ArtificialBrain brain;

	public BuildingState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		brain.getBody().build(brain.getStructureStack().pop());
		brain.setState(brain.getIdleState());
	}
}
