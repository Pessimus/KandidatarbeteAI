package Model;

import Toolkit.RenderObject;

/**
 * Created by Oskar on 2016-04-01.
 */
public class Stockpile implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.STOCKPILE;

    private static final StructureBuildingMaterialTuple[] buildingMaterials = new StructureBuildingMaterialTuple[]
            {       new StructureBuildingMaterialTuple(IItem.Type.WOOD_ITEM, 5),
                    new StructureBuildingMaterialTuple(IItem.Type.STONE_ITEM, 20)
            };

    private Inventory inventory;

	private int integrity;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\
	public Stockpile(RenderObject.RENDER_OBJECT_ENUM renderEnum, float x, float y, int capacity){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.STOCKPILE_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;
		this.renderObjectEnum = renderEnum;

		this.integrity = 100;
	}

//---------------------------------------------INTERACTION METHODS----------------------------------------------------\\
   /* *//**
     * Adds the specified amount of the specified item to this characters inventory.
     * @param item The item to be added.
     *//*
    public void addToInventory(IItem item){
        inventory.addItem(item);
    }*/

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

//----------------------------------------ADD & REMOVE OCCUPANTS------------------------------------------------------\\



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
		//TODO implement
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


