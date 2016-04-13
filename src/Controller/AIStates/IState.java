package Controller.AIStates;

/**
 * Created by Tobias on 2016-03-29.
 */
public interface IState {

	/**
	 * Will execute the actions needed for the state to be fulfilled.
	 * This method will be executed once per cycle, per AI.
	 */
	void run();
}
