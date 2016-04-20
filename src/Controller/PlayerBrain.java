package Controller;

import Controller.AIStates.IState;
import Model.*;
import Model.Character;
import Utility.Constants;

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

    public PlayerBrain(ICharacterHandle c) {
        body = c;
    }

    public void update() {
		if(pathSteps.isEmpty()) {
			if (walkingUp)
				body.moveUp();
			if (walkingDown)
				body.moveDown();
			if (walkingRight)
				body.moveRight();
			if (walkingLeft)
				body.moveLeft();
		}
		else{
		//Pathfinding, if the path to destination isnt null, move towards the next node in the path.
			pathSteps.getFirst().stepTowards(body);
			//Remove visited node.
			if(pathSteps.getFirst().reached(body)) {
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
    public void moveToMouse(double destX, double destY) {
		//Intiates the optimal path to destination when left mouse button is clicked.
		pathSteps = Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), destX, destY);
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

	public void build(int structureIndex){
		switch (structureIndex){
			case 1:
				body.build(IStructure.StructureType.HOUSE);
				break;
			case 2:
				body.build(IStructure.StructureType.FARM);
				break;
			case 3:
				body.build(IStructure.StructureType.STOCKPILE);
				break;
		}
	}

	public void reproduce() {
		for(ICollidable collidable : body.getInteractables()){
			if(collidable.getClass() == Model.Character.class){
				((Character)collidable).reproduce((Character)body);
			}
		}
	}
}
