package Model.Structures;

import Model.*;
import Model.Character;
import Model.Tasks.AttackTask;
import Utility.Constants;
import Utility.RenderObject;

/**
 * Created by Oskar on 2016-04-01.
 */
public class Stockpile implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.STOCKPILE;
	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.STOCKPILE;

    /*private static final StructureBuildingMaterialTuple[] buildingMaterials = new StructureBuildingMaterialTuple[]
            {       new StructureBuildingMaterialTuple(IItem.Type.WOOD_ITEM, 5),
                    new StructureBuildingMaterialTuple(IItem.Type.STONE_ITEM, 20)
            };*/

    private Inventory inventory;

	private int integrity;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

//	private int buildingPercent;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\

	public Stockpile(float x, float y){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.STOCKPILE_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;

		this.integrity = 10;

		inventory = new Inventory();

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

	/*@Override
	public StructureBuildingMaterialTuple[] getBuildingMaterials() {
		return buildingMaterials;
	}*/

//----------------------------------------ADD & REMOVE OCCUPANTS------------------------------------------------------\\


//	@Override
//	public int getConstructionStatus() {
//		return buildingPercent;
//	}

	@Override
	/**{@inheritDoc}*/
	public boolean isImovable(){
		return true;
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
		Schedule.addTask(new AttackTask(this,rhs,1*60));
	}

	@Override
	public void interactedCommand(Character rhs) {
		//TODO implement
	}

	@Override
	public void consumedCommand(Character rhs) {
		//TODO implement
	}

	@Override
	public void attackedCommand(Character rhs) {
		this.integrity --;
	}

	@Override
	public void interactedInterrupted(Character rhs) {
		//TODO implement
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


