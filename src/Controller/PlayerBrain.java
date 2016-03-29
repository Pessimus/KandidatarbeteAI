package Controller;

import Model.Character;
import Model.Constants;
import Model.ICharacterHandle;

/**
 * Created by Gustav on 2016-03-23.
 */
public class PlayerBrain implements AbstractBrain {

	private ICharacterHandle body; // The character this Brain controls


    private boolean walkingUp = false;
    private boolean walkingDown = false;
    private boolean walkingLeft = false;
    private boolean walkingRight = false;

    public PlayerBrain() {
        body = new Character(100, 100, Constants.PLAYER_CHARACTER_KEY);
    }

    public PlayerBrain(ICharacterHandle c) {
        body = c;
    }

    public void update() {
		if(walkingUp)
			body.moveUp();
		if(walkingDown)
			body.moveDown();
		if(walkingRight)
			body.moveRight();
		if(walkingLeft)
			body.moveLeft();

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
		this.walkingUp = true;
    }
    public void movePlayerDown() {
		this.walkingDown = true;
    }
    public void movePlayerLeft() {
		this.walkingLeft = true;
    }
    public void movePlayerRight() {
		this.walkingRight = true;
    }

    public void stopPlayerUp() {
		this.walkingUp = false;
    }
    public void stopPlayerLeft() {
		this.walkingLeft = false;
    }
    public void stopPlayerRight() {
		this.walkingRight = false;
    }
    public void stopPlayerDown() {
		this.walkingDown = false;
    }

    public void playerRunning() {
        body.startRunning();
    }
    public void playerWalking() {
        body.stopRunning();
    }

	//TODO remove this test method
	public void interact(){
		((Character)body).interact();
	}

}
