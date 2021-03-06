package Controller.AIStates;

import Controller.ArtificialBrain;
import Utility.RenderObject;

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
		brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.BUILDING);
		brain.setState(brain.getIdleState());
	}
}
