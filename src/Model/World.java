package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
		this.collidables = new LinkedList<ICollidable>();
		this.timeables = new LinkedList<ITimeable>();
		this.characters = new HashMap<>();
	}

	public void update(){

		for(Character character : characters.values()){
			//TODO IF x
			character.moveX();
			//END TODO IF x
			//TODO IF y
			character.moveY();
			//END TODO IF y
		}

		for(ITimeable timedObj : timeables){
			timedObj.update();
		}

	}

	public Character addCharacter(double xPoss, double yPoss, int key){
		Character character = new Character(xPoss, yPoss);

		this.collidables.add(character);
		this.characters.put(key,character);

		return character;
	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){return false;}

	public LinkedList<RenderObject> getRenderObjects(){
		LinkedList<RenderObject> renderObjects = new LinkedList<>();
		for(ICollidable visible : collidables){
			RenderObject tmp = new RenderObject(visible.getX(), visible.getY(), visible.getCollisionRadius(), RenderObject.RENDER_OBJECT_ENUM.CHARACTER);
//			tmp.xPos = visible.getX();
//			tmp.yPos = visible.getY();
//			tmp.radius = visible.getCollisionRadius();
			//TODO ENUM for type
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

	/*
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	private Character character = new Character(100, 100);
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
