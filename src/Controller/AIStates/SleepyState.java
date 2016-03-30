package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICharacterHandle;

import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SleepyState implements IState{
	private ICharacterHandle body;
	private final ArtificialBrain brain;
	private List<PathStep> pathToHome;

	public SleepyState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		/*if(pathToHome != null) {
			--pathfind home
		} else {
			brain.setState(brain.getSleepState());
		}
		 */
	}
}