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
		addCharacter(700,700,2);

		addCharacter(600,450,2);
		addCharacter(500,500,3);

		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		/*for (int i = 5; i < 500; i += 1) {
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
				characters.remove(character.getKey(), character);
				collidables.remove(character);
				collidablesR.remove(character);
				timeables.remove(character);
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
		//try{
			this.collidables.handleCollision();//TODO Collision in Y-axis is not working yet.
		//}catch (Exception e){
			//e.printStackTrace();
		//}
		//System.out.println("World: run() - 3:rd");

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
		characters.get(Constants.PLAYER_CHARACTER_KEY).startWalkingUp();
	}

	public void movePlayerDown(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).startWalkingDown();
	}

	public void movePlayerLeft(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).startWalkingLeft();
	}

	public void movePlayerRight(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).startWalkingRight();
	}

	public void stopPlayerUp(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).stopWalkingUp();
	}

	public void stopPlayerDown(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).stopWalkingDown();
	}

	public void stopPlayerRight(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).stopWalkingRight();
	}

	public void stopPlayerLeft(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).stopWalkingLeft();
	}

	public void playerRunning(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).startRunning();
	}

	public void playerWalking(){
		characters.get(Constants.PLAYER_CHARACTER_KEY).stopRunning();
	}

	public LinkedList<InventoryRender> displayPlayerInventory(){
		return characters.get(Constants.PLAYER_CHARACTER_KEY).getInventory();
	}


}


