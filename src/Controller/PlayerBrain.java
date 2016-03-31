package Controller;

import Controller.AIStates.IState;
import Model.Character;
import Model.Constants;
import Model.ICharacterHandle;
import org.newdawn.slick.util.pathfinding.PathFinder;

import java.util.LinkedList;

/**
 * Created by Gustav on 2016-03-23.
 */
public class PlayerBrain implements AbstractBrain {

	private ICharacterHandle body; // The character this Brain controls

	IState currentState;


    private boolean walkingUp = false;
    private boolean walkingDown = false;
    private boolean walkingLeft = false;
    private boolean walkingRight = false;
    private LinkedList<PathStep> pathSteps = new LinkedList<>();

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
		//Pathfinding, if the path to destination isnt null, move towards the next node in the path.
        if(pathSteps != null) {
            if(!pathSteps.isEmpty()) {
                pathSteps.getFirst().stepTowards(body);
				//Remove visited node.
                pathSteps.removeFirst();
            }
        }
    }

	@Override
	public void setBody(ICharacterHandle character) {
		body = character;
	}

	@Override
	public ICharacterHandle getBody() {
		return body;
	}

	@Override
	public void setState(IState state) {
		currentState = state;
	}

	@Override
	public IState getState() {
		return currentState;
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
    public void moveToMouse(float destX, float destY) {
		//Intiates the optimal path to destination when left mouse button is clicked.
      pathSteps = Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), destX, destY);
        //System.out.println(pathSteps == null);
        //System.out.println(pathSteps);
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

	//TODO how to select object from list, aka not always 0
	public void attack(){
		this.body.attackObject(0);
	}

	//TODO how to select object from list, aka not always 0
	public void interact(){
		this.body.interactObject(0);
	}

	//TODO how to select object from list, aka not always 0
	public void consume(){
		this.body.consumeObject(0);
	}
}
