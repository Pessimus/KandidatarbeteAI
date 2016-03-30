package Controller.AIStates;

import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public interface IState {
	void setBody(ICharacterHandle character);

	/**
	 * Will execute the actions needed for the state to be fulfilled
	 * @return true if the state has been fulfilled, otherwise false
	 */
	void run();
}
