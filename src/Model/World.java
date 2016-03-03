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
	private double width;
	private double height;

	public World (double width, double height){
		this.width = width;
		this.height = height;
		this.collidables = new LinkedList<ICollidable>();
		this.timables = new LinkedList<ITimeable>();
		this.characters = new HashMap<>();
	}

	public static World generateWorld(double width, double height, int nbrOfResources) {
		World world = new World(width,height);

		return null;
	}

	public Character addCharacter(double xPoss, double yPoss, int key){
		Character c = new Character(xPoss, yPoss);

		this.collidables.add(c);
		this.characters.put(key,c);

		return c;
	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){return false;}

	public LinkedList<Vissible> getVisibles(){
		LinkedList<Vissible> vissibles = new LinkedList<>();
		for(ICollidable visible : collidables){
			Vissible tmp = new Vissible();
			tmp.xPoss = visible.getX();
			tmp.yPoss = visible.getY();
			tmp.radius = visible.getRadius();
			//TODO ENUM for type
			vissibles.add(tmp);
		}
		return vissibles;
	}

}
