package Controller;

import Controller.AIStates.IState;
import Model.ICharacterHandle;

/**
 * Created by Gustav on 2016-03-23.
 */
public interface AbstractBrain {

    void update(); // running this allows the brain to control its character

	void setBody(ICharacterHandle character);
	ICharacterHandle getBody();

	void setState(IState state);
	IState getState();
}
