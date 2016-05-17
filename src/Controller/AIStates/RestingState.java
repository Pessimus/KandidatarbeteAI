package Controller.AIStates;

import Controller.ArtificialBrain;
import Utility.RenderObject;

/**
 * Created by Tobias on 2016-03-29.
 */
public class RestingState implements IState{
	private final ArtificialBrain brain;

	public RestingState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.SLEEPING);
		brain.getBody().sleep();

		brain.setState(brain.getIdleState());
	}
}