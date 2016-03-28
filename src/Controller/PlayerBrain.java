package Controller;

import Model.Character;
import Model.Constants;
import Model.ICharacterHandle;

/**
 * Created by Gustav on 2016-03-23.
 */
public class PlayerBrain implements AbstractBrain {

	private ICharacterHandle body; // The character this Brain controls

	private boolean walkingUp;
	private boolean walkingDown;
	private boolean walkingLeft;
	private boolean walkingRight;

    public PlayerBrain() {
        body = new Character(100, 100, Constants.PLAYER_CHARACTER_KEY);
    }

    public PlayerBrain(ICharacterHandle c) {
        body = c;
    }

    public void update() {
    }

	@Override
	public void setBody(ICharacterHandle character) {
		body = character;
	}

	@Override
	public ICharacterHandle getBody() {
		return body;
	}

	public void movePlayerUp() {
        body.startWalkingUp();
    }
    public void movePlayerDown() {
        body.startWalkingDown();
    }
    public void movePlayerLeft() {
        body.startWalkingLeft();
    }
    public void movePlayerRight() {
        body.startWalkingRight();
    }

    public void stopPlayerUp() {
        body.stopWalkingUp();
    }
    public void stopPlayerLeft() {
        body.stopWalkingLeft();
    }
    public void stopPlayerRight() {
        body.stopWalkingRight();
    }
    public void stopPlayerDown() {
        body.stopWalkingDown();
    }

    public void playerRunning() {
        body.startRunning();
    }
    public void playerWalking() {
        body.stopRunning();
    }

}
