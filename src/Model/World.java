package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * Created by Martin on 23/02/2016.
 */
public class World implements Runnable{

	PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private HashMap<Integer,Character> characters;
	private LinkedList<ICollidable> collidablesR; //TODO custom (dual) list supporting bubblesort and incertionsort
	private CollisionList collidables;
	private LinkedList<ITimeable> timeables;
	private LinkedList<ICollidable> statics; //List containing all collidables that does not move (or get destroyed or created too often)
	//--------Remove lists--------------
	private LinkedList<ICollidable> collidablestoberemoved = new LinkedList<>();
	private LinkedList<ICollidable> collideablesrtoberemoved = new LinkedList<>();
	private LinkedList<ITimeable> timeablestoberemoved = new LinkedList<>();

	private LinkedList<Character> characterstoberemoved = new LinkedList<>();
	private double width;
	private double height;


	private Semaphore sema = new Semaphore(1);

	public World (double width, double height){
		this.width = width;
		this.height = height;
		this.collidables = new CollisionList();
		this.collidablesR = new LinkedList<>();
		this.timeables = new LinkedList<>();
		this.characters = new HashMap<>();
		addCharacter(450,600,1);
		addCharacter(450,700,2);

		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		/*for (int i = 0; i < 10; i += 1) {
			int rx = (int) (Math.random()*1000);
			int ry = (int) (Math.random()*1000);
			addCharacter(rx, ry, i);
		}*/
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
	}

	/**
	 * Update characters
	 * Update timeables
	 * Update collidables
	 */
/*	public void update(){
		try {
			sema.acquire();

			//this.collidables.handleCollision();//TODO Collision in Y-axis is not working yet. 

			for (Character character : characters.values()) {
				character.update();

				*//*if (character.getKey() < 5) {
					System.out.println(character.getHunger());
				}*//*

				if (!character.isAlive()) {
					characters.remove(character.getKey(), character);
					collidables.remove(character);
					collidablesR.remove(character);
					timeables.remove(character);
					//character = null;
				} else {
					//TODO IF x

					character.moveX();
					//END TODO IF x
					//TODO IF y
					character.moveY();
					//END TODO IF y
				}
			}
			sema.release();

			sema.acquire();
			for (ITimeable timedObj : timeables) {
				timedObj.updateTimeable();
			}

			sema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "interrupted when removing a dead character!", e);
		}

		firePropertyChange("update", 1);
	}*/

	@Override
	public void run() {
		//System.out.println("World: run()");
/*		try {
			sema.acquire();

			//this.collidables.handleCollision();//TODO Collision in Y-axis is not working yet.

			for (Character character : characters.values()) {
				character.update();

				*//*if (character.getKey() < 5) {
					System.out.println(character.getHunger());
				}*//*

				if (!character.isAlive()) {
					characters.remove(character.getKey(), character);
					collidables.remove(character);
					collidablesR.remove(character);
					timeables.remove(character);
					//character = null;
				} else {
					//TODO IF x

					character.moveX();
					//END TODO IF x
					//TODO IF y
					character.moveY();
					//END TODO IF y
				}
			}
			sema.release();

			sema.acquire();
			for (ITimeable timedObj : timeables) {
				timedObj.updateTimeable();
			}

			sema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "interrupted when removing a dead character!", e);
		}*/
		for (ITimeable timedObj : timeables) {
			timedObj.updateTimeable();
		}

		//System.out.println("World: run() - 2:nd");
		for (Character character : characters.values()) {
			character.update();

			if (!character.isAlive()) {
				collidablestoberemoved.add(character);
				collideablesrtoberemoved.add(character);
				timeablestoberemoved.add(character);
				characterstoberemoved.add(character);
				//System.out.println("World: run() - dead");
				//character = null;
			} else {
				//TODO IF x

				character.moveX();
				//END TODO IF x
				//TODO IF y
				character.moveY();
				//END TODO IF y
				//System.out.println("World: run() - move");
			}
		}

		//System.out.println("World: run() - 3:rd");
		removeObjects();
		firePropertyChange("update", 1);
	}

	public Character addCharacter(float xPoss, float yPoss, int key){
		Character character = new Character(xPoss, yPoss, key);

		/*
		try{
			sema.acquire();
			this.collidablesR.add(character);
			this.collidables.add(character);
			this.timeables.add(character);
			this.characters.put(key,character);
			sema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "interrupted when adding new character!", e);
		}
		*/

		this.collidablesR.add(character);
		this.collidables.add(character);
		this.timeables.add(character);
		this.characters.put(key,character);

		return character;
	}

	public void removeObjects() {
		System.out.print("Collidables to remove: " + collidablestoberemoved.size() + "\n");
		System.out.print("Characters to remove: " + characterstoberemoved.size() + "\n");
		System.out.print("Timeables to remove: " + timeablestoberemoved.size() + "\n");
		System.out.print("CollidablesR to remove: " + collideablesrtoberemoved.size() + "\n");
		System.out.print("\n");
		if(collidablestoberemoved != null ) {
			for (ICollidable collidable : this.collidablestoberemoved) {
				collidables.remove(collidable);
			}
			collidablestoberemoved.clear();
		}
		if(timeablestoberemoved != null ) {
			for (ITimeable timeable : this.timeablestoberemoved) {
				timeables.remove(timeable);
			}
			timeablestoberemoved.clear();
		}

		if (characters != null) {
			for (Character character : this.characterstoberemoved) {
				characters.remove(character.getKey());
			}
			characterstoberemoved.clear();
		}

		if (characters != null) {
			for (ICollidable collidabler : this.collideablesrtoberemoved) {
				collidablesR.remove(collidabler);
			}
			collideablesrtoberemoved.clear();
		}

		System.out.print("Collidables: " + collidables.getSize() + "\n");
		System.out.print("Characters: " + characters.size() + "\n");
		System.out.print("Timeables: " + timeables.size() + "\n");
		System.out.print("CollidablesR: " + collidablesR.size() + "\n");
		System.out.print("\n");



	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){return false;}


	public RenderObject[] getRenderObjects(){
		//System.out.println("World: getRenderObjects()");
		RenderObject[] renderObjects = new RenderObject[collidables.getSize()];

/*		try {
			sema.acquire();

			for (ICollidable visible : collidablesR) {
				renderObjects.add(visible.getRenderObject());
			}

			sema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "interrupted when sending render objects!", e);
		}*/

		for (int i = 0; i < collidablesR.size(); i++) {
			renderObjects[i] = collidablesR.get(i).getRenderObject();
		}
		return renderObjects;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}

	private void firePropertyChange(String type, Object property){
		pcs.firePropertyChange(type, 0, property);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	/*
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	private Character character;
	public void moveCharacterTo(int x, int y){
		character.setPosition(x, y);
	}
	public List<RenderObject> getCharacter(){
		LinkedList<RenderObject> list = new LinkedList<>();
		list.add(new RenderObject(character.getX(), character.getY(), character.getCollisionRadius(), RenderObject.RENDER_OBJECT_ENUM.CHARACTER));
		return list;
	}
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	*/

	public void movePlayerUp(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.startWalkingUp();
			}
		}
	}

	public void movePlayerDown(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.startWalkingDown();
			}
		}
	}

	public void movePlayerLeft(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.startWalkingLeft();
			}
		}
	}

	public void movePlayerRight(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.startWalkingRight();
			}
		}
	}

	public void stopPlayerUp(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.stopWalkingUp();
			}
		}
	}

	public void stopPlayerDown(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.stopWalkingDown();
			}
		}
	}

	public void stopPlayerRight(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.stopWalkingRight();
			}
		}
	}

	public void stopPlayerLeft(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.stopWalkingLeft();
			}
		}
	}

	public void playerRunning(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.startRunning();
			}
		}
	}

	public void playerWalking(){
		for(Character c : characters.values()){
			if(c.getKey() == 1){
				c.stopRunning();
			}
		}
	}
}


