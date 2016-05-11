package Model.Structures;

import Model.*;
import Model.Character;
import Model.Items.WoodItem;
import Model.Tasks.AttackTask;
import Model.Tasks.InteractTask;
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

	private double xPos;
	private double yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

//	private int buildingPercent;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\

	public Stockpile(double x, double y){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.STOCKPILE_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;

		this.integrity = Constants.MAX_INTEGRETY_STOCKPILE;

		inventory = new Inventory();

	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	@Override
	/**{@inheritDoc}*/
	public double getX() {
		return xPos;
	}

	@Override
	/**{@inheritDoc}*/
	public double getY() {
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
	/**{@inheritDoc}*/
	public boolean isImovable(){
		return true;
	}

	public Inventory getInventory(){
		return this.inventory;
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
		Schedule.addTask(new InteractTask(this,rhs,Constants.STOCKPILE_INTERACTION_TIME));
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs) {
		Schedule.addTask(new AttackTask(this,rhs,Constants.STOCKPILE_ATTACKED_TIME));
	}

	@Override
	public void interactedCommand(Character rhs) {
		StockpileInteraction i = new StockpileInteraction(rhs, this);
		rhs.startStockpileInteraction(this, i);
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

	@Override
	public void setRenderType(RenderObject.RENDER_OBJECT_ENUM type) {
		renderObjectEnum = type;
	}
}


