package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
		for (int i = 0; i < 1; i += 1) {
			int rx = (int) (Math.random()*1000);
			int ry = (int) (Math.random()*1000);
			addCharacter(rx, ry, i);
		}
		//character = addCharacter(100, 100, 1337);
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
	}

	public void update(){

		for(ICollidable visible : collidables){
			System.out.println(collidables);
			
		}

		for(Character character : characters.values()) {

			if (character.getKey() < 5) {
				System.out.println(character.getHunger());
			}

			character.update();
			if (!character.isAlive()) {
				characters.remove(character.getKey());
				collidables.remove(character);
				timeables.remove(character);
				character = null;
				/*if (characters.isEmpty()) {
					addCharacter(100, 100, 100);
				}*/
			} else {
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
		}

	}

	public Character addCharacter(float xPoss, float yPoss, int key){
		Character character = new Character(xPoss, yPoss, key);

		this.collidables.add(character);
		this.characters.put(key,character);

		return character;
	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){return false;}

	public LinkedList<RenderObject> getRenderObjects(){
		LinkedList<RenderObject> renderObjects = new LinkedList<>();
		for(ICollidable visible : collidables){
			RenderObject tmp = new RenderObject(visible.getX(), visible.getY(), visible.getCollisionRadius(), RenderObject.RENDER_OBJECT_ENUM.CHARACTER);
			renderObjects.add(tmp);
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
/*	public List<RenderObject> getCharacter(){
		LinkedList<RenderObject> list = new LinkedList<>();
		list.add(new RenderObject(character.getX(), character.getY(), character.getCollisionRadius(), RenderObject.RENDER_OBJECT_ENUM.CHARACTER));
		return list;
	}*/
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!

}
