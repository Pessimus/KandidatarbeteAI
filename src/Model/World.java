package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Martin on 23/02/2016.
 */
public class World {

	PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private HashMap<Integer,Character> characters;
	private LinkedList<ICollidable> collidables; //TODO custom (dual) list supporting bubblesort and incertionsort
	private LinkedList<ITimeable> timeables;
	private double width;
	private double height;

	private Semaphore sema = new Semaphore(1);

	public World (double width, double height){
		this.width = width;
		this.height = height;
		this.collidables = new LinkedList<>();
		this.timeables = new LinkedList<>();
		this.characters = new HashMap<>();

		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		Random r = new Random();
		for(int i = 0; i < 50; i++){
			addCharacter(r.nextFloat() * 400 + 1, r.nextFloat() * 400 + 1, i);
		}
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
	}

	public void update(){
		try {
			sema.acquire();
			for (Character character : characters.values()) {
				character.update();

				if (!character.isAlive()) {
					characters.remove(character.getKey(), character);
					collidables.remove(character);
					timeables.remove(character);
					//character = null;
				}
				//TODO IF x

				character.moveX();
				//END TODO IF x
				//TODO IF y
				character.moveY();
				//END TODO IF y
			}

			for (ITimeable timedObj : timeables) {
				timedObj.update();
			}
			sema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "interrupted when removing a dead character!", e);
		}
	}

	public Character addCharacter(float xPoss, float yPoss, int key){
		Character character = new Character(xPoss, yPoss, key);

		try{
			sema.acquire();
			this.collidables.add(character);
			this.timeables.add(character);
			this.characters.put(key,character);
			sema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "interrupted when removing a dead character!", e);
		}

		return character;
	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){return false;}

	public LinkedList<RenderObject> getRenderObjects(){
		LinkedList<RenderObject> renderObjects = new LinkedList<>();

		try {
			sema.acquire();
			for (ICollidable visible : collidables) {
				RenderObject tmp = new RenderObject(visible.getX(), visible.getY(), visible.getCollisionRadius(), RenderObject.RENDER_OBJECT_ENUM.CHARACTER);
				renderObjects.add(tmp);
			}
			sema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
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
}
