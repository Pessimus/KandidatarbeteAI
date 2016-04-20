package Model.Structures;

import Model.*;
import Model.Character;
import Model.Resources.Crops;
import Model.Tasks.AttackTask;
import Model.Tasks.InteractTask;
import Utility.Constants;
import Utility.RenderObject;


/**
 * Created by Martin on 04/04/2016.
 */
public class Farm implements IStructure, ITimeable {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
	public static final StructureType structureType = StructureType.FARM;
	/*public static final StructureBuildingMaterialTuple[] buildingMaterials = new StructureBuildingMaterialTuple[]
			{       new StructureBuildingMaterialTuple(IItem.Type.WOOD_ITEM, Constants.WHEAT_COLLISION_RADIUS),
					new StructureBuildingMaterialTuple(IItem.Type.STONE_ITEM, 5)
			};*/

	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.FARM;

	private int integrity;

	private double xPos;
	private double yPos;
	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;

	private boolean spawning;
	private final int nbrOfSpawnPoints = 8;
	private Crops[] spawnPoints;
	private double[] spawnPointsXpos;
	private double[] spawnPointsYpos;

	//private int buildingPercent;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\

	public Farm(double x, double y){
		this.xPos = x;
		this.yPos = y;
		this.collisionRadius = Constants.FARM_COLLISION_RADIUS;
		this.interactionRadius = 0;
		this.surroundingRadius = 0;

		this.integrity = Constants.MAX_INTEGRETY_FARM;

		this.spawning = false;
		spawnPoints = new Crops[nbrOfSpawnPoints];
		spawnPointsXpos = new double[nbrOfSpawnPoints];
		spawnPointsYpos = new double[nbrOfSpawnPoints];

		spawnPointsXpos[0] = x-(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);
		spawnPointsYpos[0] = y-(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);

		spawnPointsXpos[1] = x;
		spawnPointsYpos[1] = y-(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);

		spawnPointsXpos[2] = x+(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);
		spawnPointsYpos[2] = y-(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);

		spawnPointsXpos[3] = x+(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);
		spawnPointsYpos[3] = y;

		spawnPointsXpos[4] = x+(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);
		spawnPointsYpos[4] = y+(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);

		spawnPointsXpos[5] = x;
		spawnPointsYpos[5] = y+(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);

		spawnPointsXpos[6] = x-(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);
		spawnPointsYpos[6] = y+(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);

		spawnPointsXpos[7] = x-(collisionRadius+Constants.WHEAT_COLLISION_RADIUS+1);
		spawnPointsYpos[7] = y;

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
		Schedule.addTask(new InteractTask(this,rhs,Constants.FARM_INTERACTION_TIME));
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs) {
		//TODO implement
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs) {
		Schedule.addTask(new AttackTask(this,rhs,Constants.FARM_ATTACKED_TIME));
	}

	@Override
	public void interactedCommand(Character rhs) {
		this.spawning = true;
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
				Crops crops = new Crops(Constants.CROPS_INITIAL_AMOUNT, Constants.CROPS_YEILD_AMOUNT);
				ResourcePoint rp = rhs.addFiniteResourcePoint(crops, RenderObject.RENDER_OBJECT_ENUM.CROPS, spawnPointsXpos[i], spawnPointsYpos[i], Constants.WHEAT_COLLISION_RADIUS);
				if(rp != null) {
					spawnPoints[i] = crops;
					spawning = false;
					return;
				}
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
