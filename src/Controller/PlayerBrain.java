package Controller;

import Model.Character;
import Model.Constants;

/**
 * Created by Gustav on 2016-03-23.
 */
public class PlayerBrain extends AbstractBrain {

    private Character body;

    public PlayerBrain() {
        body = new Character(100, 100, Constants.PLAYER_CHARACTER_KEY);
    }

    public PlayerBrain(Character c) {
        body = c;
    }

    public void step() {}

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
        body.startWalkingRight();
    }
    public void stopPlayerDown() {
        body.startWalkingDown();
    }

    public void playerRunning() {
        body.startRunning();
    }
    public void playerWalking() {
        body.stopRunning();
    }

}
