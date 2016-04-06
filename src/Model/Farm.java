package Model;

import Toolkit.RenderObject;


/**
 * Created by Martin on 04/04/2016.
 */
public class Farm implements IStructure, ITimeable{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
	public static final StructureType structureType = StructureType.STOCKPILE;

	private Inventory inventory;

	private int integrity;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum;

	private boolean spawning;
//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\

	public Farm(RenderObject.RENDER_OBJECT_ENUM renderEnum, float x, float y){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.STOCKPILE_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;
		this.renderObjectEnum = renderEnum;

		this.integrity = 100;

		this.spawning = false;

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
		this.spawning = true;
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
	public void updateTimeable() {

	}

	@Override
	/**{@inheritDoc}*/
	public boolean toBeRemoved() {
		return integrity == 0;
	}

	@Override
	public boolean isSpawning(){
		return this.spawning;
	}

	@Override
	public void spawn(World rhs) {
		Crops crops = new Crops(100, 10);
		rhs.addFiniteResourcePoint(crops, RenderObject.RENDER_OBJECT_ENUM.CROPS, this.xPos, this.yPos, 20);
		spawning = false;
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
