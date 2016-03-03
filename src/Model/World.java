package Model;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Martin on 23/02/2016.
 */
public class World {

	private HashMap<Integer,Character> characters;
	private LinkedList<ICollidable> collidables; //TODO custom (dual) list supporting buublesort and incrtionsort
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

	public LinkedList<Vissible> getVisibles(){
		LinkedList<Vissible> vissibles = new LinkedList<>();
		for(ICollidable visible : collidables){
			Vissible tmp = new Vissible();
			tmp.xPoss = visible.getX();
			tmp.yPoss = visible.getY();
			tmp.radius = visible.getCollisionRadius();
			//TODO ENUM for type
			vissibles.add(tmp);
		}
		return vissibles;
	}

}
