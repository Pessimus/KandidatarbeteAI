package Model;

import Model.Tasks.AttackTask;
import Model.Tasks.InteractTask;
import Utility.Constants;
import Utility.RenderObject;

import java.util.LinkedList;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Animal implements ICollidable, ITimeable {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	// TODO: Enum type for animal
	private RenderObject.RENDER_OBJECT_ENUM renderObjectEnum = RenderObject.RENDER_OBJECT_ENUM.CHARACTER;

	private float xPoss;
	private float yPoss;

	private float territoryMinX;
	private float territoryMaxX;
	private float territoryMinY;
	private float territoryMaxY;

	private IResource resource;

	private boolean alive;

	private double collisionRadius;
	private double interactionRadius;
	private double surroundingRadius;
	private LinkedList<ICollidable> surroundingX;
	private LinkedList<ICollidable> surroundingY;
	private LinkedList<ICollidable> surroundings;
	private LinkedList<ICollidable> interactableX;
	private LinkedList<ICollidable> interactableY;
	private LinkedList<ICollidable> interactables;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing a animal in the world.
	 * @param xPoss the position on the x-axis
	 * @param yPoss the position on the y-axis
	 * @param resourceType the resource that can be gathered from this animal
	 */
	public Animal(float xPoss, float yPoss, IResource resourceType, float territoryMinX, float territoryMinY, float territoryMaxX, float territoryMaxY){
		this.xPoss = xPoss;
		this.yPoss = yPoss;

		this.resource = resourceType;

		this.territoryMinX = territoryMinX;
		this.territoryMaxX = territoryMaxX;
		this.territoryMinY = territoryMinY;
		this.territoryMaxY = territoryMaxY;

		this.alive = true;

		this.collisionRadius = Constants.ANIMAL_COLLISION_RADIUS*10;
		this.interactionRadius = Constants.ANIMAL_INTERACTION_RADIUS;
		this.surroundingRadius = Constants.ANIMAL_SURROUNDING_RADIUS;

		//Initialize collision detection lists
		this.surroundingX = new LinkedList<>();
		this.surroundingY = new LinkedList<>();
		this.surroundings = new LinkedList<>();

		this.interactableX = new LinkedList<>();
		this.interactableY = new LinkedList<>();
		this.interactables = new LinkedList<>();
	}

//---------------------------------------Collision Methods------------------------------------------------------------\\

	@Override
	public float getX() {
		return xPoss;
	}

	@Override
	public float getY() {
		return yPoss;
	}

	@Override
	public double getCollisionRadius() {
		return collisionRadius;
	}

	@Override
	public double getInteractionRadius() {
		return interactionRadius;
	}

	@Override
	public double getSurroundingRadius() {
		return surroundingRadius;
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingX(ICollidable rhs) {
		this.surroundingX.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void addToSurroundingY(ICollidable rhs) {
		this.surroundingY.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void checkSurroundings() {
		LinkedList<ICollidable> tmp = new LinkedList<>();
		for(ICollidable c : this.surroundingX){
			if(this.surroundingY.contains(c)){
				tmp.add(c);
			}
		}
		this.surroundingX.clear();
		this.surroundingY.clear();
		this.surroundings = tmp;
	}

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableX(ICollidable rhs) {
		this.interactableX.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void addToInteractableY(ICollidable rhs) {
		this.interactableY.add(rhs);
	}

	@Override
	/**{@inheritDoc}*/
	public void checkInteractables() {
		LinkedList<ICollidable> tmp = new LinkedList<>();
		for(ICollidable c : this.interactableX){
			if(this.interactableY.contains(c)){
				tmp.add(c);
			}
		}
		this.interactableX.clear();
		this.interactableY.clear();
		this.interactables = tmp;
	}

	@Override
	/**{@inheritDoc}*/
	public boolean isImovable(){
		return false;
	}

//---------------------------------------Interaction methods----------------------------------------------------------\\

	@Override
	public void interacted(Character rhs) {
		Schedule.addTask(new InteractTask(this,rhs,20*60));
	}

	@Override
	public void consumed(Character rhs) {
		Schedule.addTask(new AttackTask(this,rhs,5*60));
	}

	@Override
	public void attacked(Character rhs) {
		Schedule.addTask(new AttackTask(this,rhs,5*60));
	}

	@Override
	public void interactedCommand(Character rhs) {
		rhs.changeSocial(10);
	}

	@Override
	public void consumedCommand(Character rhs) {
		rhs.changeHunger(Constants.ANIMAL_HUNGER_CHANGE_CONSUME);
		rhs.changeEnergy(Constants.ANIMAL_ENERGY_CHANGE_CONSUME);
		rhs.changeThirst(Constants.ANIMAL_THIRST_CHANGE_CONSUME);
		this.alive = false;
	}

	@Override
	public void attackedCommand(Character rhs) {
		rhs.changeHunger(Constants.ANIMAL_HUNGER_CHANGE_ATTACK);
		rhs.changeEnergy(Constants.ANIMAL_ENERGY_CHANGE_ATTACK);
		rhs.changeThirst(Constants.ANIMAL_THIRST_CHANGE_ATTACK);
		rhs.addToInventory(resource.gatherResource());
		this.alive = false;
	}

	@Override
	public void interactedInterrupted(Character rhs) {

	}

	@Override
	public void consumedInterrupted(Character rhs) {

	}

	@Override
	public void attackedInterrupted(Character rhs) {

	}

//------------------------------------------UPDATE METHODS------------------------------------------------------------\\

	int xAxisSteps = 0;
	int yAxisSteps = 0;
	boolean moveLeft = false;
	boolean moveRight = false;
	boolean moveUp = false;
	boolean moveDown = false;

	private boolean moveToLeft(){
		if(this.xPoss-this.collisionRadius-1<this.territoryMinX){
			return false;
		}
		for(ICollidable collidable: this.surroundings){
			if(!(Math.abs(this.xPoss-collidable.getX())>(this.collisionRadius+collidable.getCollisionRadius()+1)
					|| Math.abs(this.yPoss-collidable.getY())>(this.collisionRadius+collidable.getCollisionRadius())
					|| this.xPoss < collidable.getX())){
				//Cant move!
				return false;
			}
		}
			this.xPoss--;
			return true;
	}

	private boolean moveToRight(){
		if(this.xPoss+this.collisionRadius+1>this.territoryMaxX){
			return false;
		}
		for(ICollidable collidable: this.surroundings){
			if(!(Math.abs(this.xPoss-collidable.getX())>(this.collisionRadius+collidable.getCollisionRadius()+1)
					|| Math.abs(this.yPoss-collidable.getY())>(this.collisionRadius+collidable.getCollisionRadius())
					|| this.xPoss > collidable.getX())){
				//Cant move!
				return false;
			}
		}
			this.xPoss++;
			return true;
	}

	private boolean moveToUp(){
		if(this.yPoss-this.collisionRadius-1<this.territoryMinY){
			return false;
		}
		for(ICollidable collidable: this.surroundings){
			if(!(Math.abs(this.xPoss-collidable.getX())>(this.collisionRadius+collidable.getCollisionRadius())
					|| Math.abs(this.yPoss-collidable.getY())>(this.collisionRadius+collidable.getCollisionRadius()+1)
					|| this.yPoss < collidable.getY())){
				//Cant move!
				return false;
			}
		}
			this.yPoss--;
			return true;
	}

	private boolean moveToDown(){
		if(this.yPoss+this.collisionRadius+1>this.territoryMaxY){
			return false;
		}
		for(ICollidable collidable: this.surroundings){
			if(!(Math.abs(this.xPoss-collidable.getX())>(this.collisionRadius+collidable.getCollisionRadius())
					|| Math.abs(this.yPoss-collidable.getY())>(this.collisionRadius+collidable.getCollisionRadius()+1)
					|| this.yPoss > collidable.getY())){
				//Cant move!
				return false;
			}
		}
			this.yPoss++;
			return true;
	}

	@Override
	public void updateTimeable() {

		boolean movedX = false;
		boolean movedY = false;

		if(xAxisSteps != 0) {
			xAxisSteps--;
			if (moveLeft) {
				movedX = this.moveToLeft();
			} else if (moveRight) {
				movedX = this.moveToRight();
			}
		}else{
			movedX = true;
			xAxisSteps = (int)(Math.random()*60*5+10*60);
			int movement = (int)(Math.random()*3);
			switch (movement){
				case 0:
					moveLeft = true;
					moveRight = false;
					break;
				case 1:
					moveLeft = false;
					moveRight = true;
					break;
				default:
					moveLeft = false;
					moveRight = false;
					break;
			}
		}

		if(yAxisSteps != 0) {
			yAxisSteps--;
			if (moveUp) {
				movedY = this.moveToUp();
			} else if (moveDown) {
				movedY = this.moveToDown();
			}
		}else{
			movedY = true;
			yAxisSteps = (int)(Math.random()*60*5+3*60);
			int movement = (int)(Math.random()*3);
			switch (movement){
				case 0:
					moveUp = true;
					moveDown = false;
					break;
				case 1:
					moveUp = false;
					moveDown = true;
					break;
				default:
					moveUp = false;
					moveDown = false;
					break;
			}
		}

		if(!movedY && !movedX && (moveUp || moveDown || moveLeft || moveRight)){
			xAxisSteps = 0;
			yAxisSteps = 0;
		}

	}

	@Override
	public boolean toBeRemoved() {
		return !alive;
	}

	@Override
	public boolean isSpawning() {
		return false;
	}

	@Override
	public void spawn(World rhs) {

	}

//------------------------------------------------RENDER METHODS------------------------------------------------------\

	@Override
	public RenderObject getRenderObject() {
		return new RenderObject(getX(), getY(), getCollisionRadius(), renderObjectEnum);
	}

	@Override
	public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
		return renderObjectEnum;
	}
}
