package Model;

import Toolkit.RenderObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oskar on 2016-04-01.
 */
public class House implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.HOUSE;

    private static final StructureBuildingMaterialTuple[] buildingMaterials = new StructureBuildingMaterialTuple[]
            {       new StructureBuildingMaterialTuple(IItem.Type.WOOD_ITEM, 10),
                    new StructureBuildingMaterialTuple(IItem.Type.STONE_ITEM, 5)
            };

    private int capacity;
    private int occupants;
    private boolean isFull = false;

	private int integrity;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\
    /**
     * A class representing the structure "House".
     * @param capacity the max capacity of occupants in the house.
     */
    public House(RenderObject.RENDER_OBJECT_ENUM renderEnum, float x, float y, int capacity){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.HOUSE_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;
		this.renderObjectEnum = renderEnum;

		this.integrity = 100;

		this.capacity=capacity;
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

    @Override
    public StructureBuildingMaterialTuple[] getBuildingMaterials() {
        return buildingMaterials;
    }
//---------------------------------------Getters & Setters------------------------------------------------------------\\
    /** Returns the max capacity of the house. */
    public int getCapacity(){
        return capacity;
    }

	/** Return boolean that states if the house is full or not.*/
    public boolean getIsFull(){
        return isFull;
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
	public void interacted(Character rhs) {
		rhs.changeEnergy(Constants.CHARACTER_ENERGY_MAX);
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs) {
		this.integrity --;
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

