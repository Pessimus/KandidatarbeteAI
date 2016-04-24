package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.Structures.Farm;
import Utility.Constants;
import Utility.UniversalStaticMethods;

/**
 * Created by Tobias on 2016-04-18.
 */
public class WorkFarmState implements IState {
	private final ArtificialBrain brain;

	public WorkFarmState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		Farm f = brain.getBody().getSurroundings().stream()

				.filter(o -> o.getClass().equals(Farm.class))
				.map(o -> (Farm)o)
				.reduce((f1, f2) -> (Math.abs(brain.getBody().getX() - f1.getX()) < Math.abs(brain.getBody().getX() - f2.getX())
										&& Math.abs(brain.getBody().getY() - f1.getY()) < Math.abs(brain.getBody().getX() - f2.getX())) ? f1 : f2)
				.orElseGet(() -> null);

		if(f != null){

		}
	}
}
