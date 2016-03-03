package Model;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Martin on 23/02/2016.
 */
public class World {

	private HashMap<Integer,Character> characters;
	private LinkedList<ICollidable> collidables; //TODO custom list supporting buublesort and incrtionsort
	private LinkedList<ITimeable> timables; //TODO custom list supporting buublesort and incrtionsort
	private double with;
	private double height;

	public World (double with, double height){
		this.with = with;
		this.height = height;
		this.collidables = new LinkedList<ICollidable>();
		this.timables = new LinkedList<ITimeable>();
		this.characters = new HashMap<>();
	}

	public static World generateWorld(double with, double height, int nbrOfResources) {
		World world = new World(with,height);

		return null;
	}

	public Character addCharacter(double xPoss, double yPoss, int key){
		Character c = new Character();

		//c.setX(xPoss);
		//c.setY(yPoss);

		this.collidables.add(c);
		this.characters.put(key,c);

		return c;
	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){


		return false;

	}


}
