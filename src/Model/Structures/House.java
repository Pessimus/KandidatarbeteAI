package Model.Structures;

import Model.*;
import Model.Character;
import Model.Tasks.AttackTask;
import Model.Tasks.InteractTask;
import Utility.Constants;
import Utility.RenderObject;

/**
 * Created by Oskar on 2016-04-01.
 */
public class House implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.HOUSE;
	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.HOUSE;

    private int capacity;
    private int occupants;
    private boolean isFull = false;

	private int integrity;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	private CollidableBlocker exit;

//	private int buildingPercent;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\
    /**
     * A class representing the structure "House".
     */
    public House(float x, float y){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.HOUSE_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;

		this.integrity = 10;

		this.exit = new CollidableBlocker(xPos,(float)(yPos+collisionRadius+Constants.CHARACTER_COLLISION_RADIUS+1),(float)(collisionRadius+Constants.CHARACTER_COLLISION_RADIUS+1));

		this.capacity=Constants.HOUSE_MAX_CAPACITY;
//		this.buildingPercent = 0;
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public float getX() {
		return xPos;
	}

	@Override
	/**{@inheritDoc}*/
	public float getY() {
		return yPos;
	}

	public float getDoorPositionX(){
		return  this.exit.getX();
	}
	public float getDoorPositionY(){
		//TODO add empty collidable;
		return this.exit.getY();
		//return (float)(this.yPos+collisionRadius+Constants.CHARACTER_COLLISION_RADIUS+1);
	}
	public ICollidable getDoor(){
		return this.exit;
	}

	@Override
	/**{@inheritDoc}*/
	public double getCollisionRadius() {
		return collisionRadius;
	}

	@Override
	/**{@inheritDoc}*/
	public double getInteractionRadius() {
		return interactionRadius;
	}

	@Override
	/**{@inheritDoc}*/
	public double getSurroundingRadius() {
		return surroundingRadius;
	}

	@Override
	public StructureType getStructureType() {
		return structureType;
	}

//	@Override
//	public int getConstructionStatus() {
//		return buildingPercent;
//	}

	/** Returns the max capacity of the house. */
    public int getCapacity(){
        return capacity;
    }

	/** Return boolean that states if the house is full or not.*/
    public boolean getIsFull(){
        return isFull;
    }

	@Override
	/**{@inheritDoc}*/
	public boolean isImovable(){
		return true;
	}

//----------------------------------------ADD & REMOVE OCCUPANTS------------------------------------------------------\\

    /** Method to call when adding a character to this house. */
    public void addOccupant(){
        occupants++;
        if(occupants>=capacity)
        {
            isFull=true;
        }
    }
    /** Method to call when removing a character from this house. */
    public void removeOccupant(){
        occupants--;
        isFull=false;
    }

//---------------------------------------Collision Methods------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableX(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableY(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void checkInteractables() {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingX(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingY(ICollidable rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void checkSurroundings() {
		//TODO implement
	}

//---------------------------------------Interaction methods----------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public void interacted(Model.Character rhs) {
		rhs.changeEnergy(Constants.CHARACTER_ENERGY_MAX);
		rhs.enterHouse(this);
		Schedule.addTask(new InteractTask(this,rhs,20*60));
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs) {
		this.integrity--;
		if(integrity <= 0){
			this.exit.setRemove(true);
		}
		Schedule.addTask(new AttackTask(this,rhs,1*60));
	}

	@Override
	public void interactedCommand(Character rhs) {
		rhs.changeEnergy(Constants.CHARACTER_ENERGY_MAX);
		rhs.exitHouse(this);
	}

	@Override
	public void consumedCommand(Character rhs) {
		//TODO implement
	}

	@Override
	public void attackedCommand(Character rhs) {
		this.integrity --;
		if(integrity <= 0){
			this.exit.setRemove(true);
		}
	}

	@Override
	public void interactedInterrupted(Character rhs) {
		rhs.exitHouse(this);
	}

	@Override
	public void consumedInterrupted(Character rhs) {
		//TODO implement
	}

	@Override
	public void attackedInterrupted(Character rhs) {
		//TODO implement
	}

//------------------------------------------Update METHODS------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public boolean toBeRemoved() {
		return integrity == 0;
	}

//------------------------------------------------RENDER METHODS------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public RenderObject getRenderObject() {
		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}

	@Override
	/**{@inheritDoc}*/
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return renderObjectEnum;
	}

}

