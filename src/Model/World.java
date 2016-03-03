package Model;

import java.util.LinkedList;

/**
 * Created by Martin on 23/02/2016.
 */
public class World {

	private LinkedList<ICollidable> collidables; //TODO custom list supporting buublesort and incrtionsort
	private LinkedList<ITimable> timables; //TODO custom list supporting buublesort and incrtionsort
	private double with;
	private double height;

	public World (double with, double height){
		this.with = with;
		this.height = height;
		this.collidables = new LinkedList<ICollidable>();
		this.timables = new LinkedList<ITimable>();

	}

	public static World generateWorld(double with, double height, int nbrOfResources) {
		World world = new World(with,height);

		return null;
	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){



		return false;

	}


}
