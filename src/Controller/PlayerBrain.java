package Controller;

import Model.Character;
import Model.Constants;
import Model.ICharacterHandler;

/**
 * Created by Gustav on 2016-03-23.
 */
public class PlayerBrain implements AbstractBrain {

	private ICharacterHandler body; // The character this Brain controls

	private boolean walkingUp;
	private boolean walkingDown;
	private boolean walkingLeft;
	private boolean walkingRight;

    public PlayerBrain() {
        body = new Character(100, 100, Constants.PLAYER_CHARACTER_KEY);
    }

    public PlayerBrain(ICharacterHandler c) {
        body = c;
    }

    public void step() {

    }

    @Override
    public void updateCharacterState() {
		;
    }

	@Override
	public void setBody(ICharacterHandler character) {
		body = character;
	}

	@Override
	public ICharacterHandler getBody() {
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
