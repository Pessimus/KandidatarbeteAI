package Model;

import Toolkit.RenderObject;


/**
 * Created by Martin on 04/04/2016.
 */
public class Farm implements IStructure, ITimeable{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
	public static final StructureType structureType = StructureType.FARM;
	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.FARM;

	private int integrity;

	private float xPos;
	private float yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	private boolean spawning;
	private int nbrOfSpawnPoints;
	private Crops[] spawnPoints;
	private float[] spawnPointsXpos;
	private float[] spawnPointsYpos;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\

	public Farm(float x, float y){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.FARM_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;

		this.integrity = 10;

		this.spawning = false;
		nbrOfSpawnPoints = 8;
		spawnPoints = new Crops[nbrOfSpawnPoints];
		spawnPointsXpos = new float[nbrOfSpawnPoints];
		spawnPointsYpos = new float[nbrOfSpawnPoints];

		spawnPointsXpos[0] = (float)(x-collisionRadius);
		spawnPointsYpos[0] = (float)(y-collisionRadius);

		spawnPointsXpos[1] = x;
		spawnPointsYpos[1] = (float)(y-collisionRadius);

		spawnPointsXpos[2] = (float)(x+collisionRadius);
		spawnPointsYpos[2] = (float)(y-collisionRadius);

		spawnPointsXpos[3] = (float)(x+collisionRadius);
		spawnPointsYpos[3] = y;

		spawnPointsXpos[4] = (float)(x+collisionRadius);
		spawnPointsYpos[4] = (float)(y+collisionRadius);

		spawnPointsXpos[5] = x;
		spawnPointsYpos[5] = (float)(y+collisionRadius);

		spawnPointsXpos[6] = (float)(x-collisionRadius);
		spawnPointsYpos[6] = (float)(y+collisionRadius);

		spawnPointsXpos[7] = (float)(x-collisionRadius);
		spawnPointsYpos[7] = y;

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
		int i = 0;
		while(i < nbrOfSpawnPoints){
			if(spawnPoints[i] != null && spawnPoints[i].getResourcesLeft() == 0){
				spawnPoints[i] = null;
			}
			i++;
		}
	}

	@Override
	/**{@inheritDoc}*/
	public boolean toBeRemoved() {
		return integrity <= 0;
	}

	@Override
	public boolean isSpawning(){
		return this.spawning;
	}

	@Override
	public void spawn(World rhs) {
		int i = 0;
		while(i < nbrOfSpawnPoints) {
			if(spawnPoints[i] == null) {
				Crops crops = new Crops(100, 10);
				spawnPoints[i] = crops;
				rhs.addFiniteResourcePoint(crops, RenderObject.RENDER_OBJECT_ENUM.CROPS, spawnPointsXpos[i], spawnPointsYpos[i], 20);
				spawning = false;
				return;
			}
			i++;
		}
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
