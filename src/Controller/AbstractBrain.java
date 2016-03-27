package Controller;

import Model.Character;
import Model.ICharacterHandler;

/**
 * Created by Gustav on 2016-03-23.
 */
public interface AbstractBrain {

    void step(); // running this allows the brain to control its character
	void updateCharacterState();

	void setBody(ICharacterHandler character);
	ICharacterHandler getBody();
}
