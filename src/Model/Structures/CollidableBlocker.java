package Model.Structures;

import Model.*;
import Model.Character;
import Utility.RenderObject;

/**
 * Created by Martin on 19/04/2016.
 */
public class CollidableBlocker implements ICollidable{

	private double xPos;
	private double yPos;
	private double radius;
	private boolean remove;

	public CollidableBlocker(double xPos, double yPos, double radius){
		this.xPos = xPos;
		this.yPos = yPos;
		this.radius = radius;
		this.remove = false;
	}

	@Override
	public double getX() {
		return this.xPos;
	}

	@Override
	public double getY() {
		return this.yPos;
	}

	@Override
	public double getCollisionRadius() {
		return this.radius;
	}

	@Override
	public double getInteractionRadius() {
		return 0;
	}

	@Override
	public double getSurroundingRadius() {
		return 0;
	}

	@Override
	public boolean isImovable() {
		return true;
	}

	@Override
	public void addToInteractableX(ICollidable rhs) {}
	@Override
	public void addToInteractableY(ICollidable rhs) {}
	@Override
	public void checkInteractables() {}
	@Override
	public void addToSurroundingX(ICollidable rhs) {}
	@Override
	public void addToSurroundingY(ICollidable rhs) {}
	@Override
	public void checkSurroundings() {}
	@Override
	public void interacted(Model.Character rhs) {}
	@Override
	public void consumed(Character rhs) {}
	@Override
	public void attacked(Character rhs) {}
	@Override
	public void interactedCommand(Character rhs) {}
	@Override
	public void consumedCommand(Character rhs) {}
	@Override
	public void attackedCommand(Character rhs) {}
	@Override
	public void interactedInterrupted(Character rhs) {}
	@Override
	public void consumedInterrupted(Character rhs) {}
	@Override
	public void attackedInterrupted(Character rhs) {}
	@Override
	public boolean toBeRemoved() {
		return this.remove;
	}
	public void setRemove(boolean value){
		this.remove = value;
	}

	@Override
	public RenderObject getRenderObject() {
		//TODO change render image.
		return new RenderObject(getX(), getY(), getCollisionRadius(), RenderObject.RENDER_OBJECT_ENUM.EMPTY);
	}

	@Override
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return RenderObject.RENDER_OBJECT_ENUM.EMPTY;
	}

	@Override
	public void setRenderType(RenderObject.RENDER_OBJECT_ENUM type) {
		;
	}
}
