package Model;

import java.util.LinkedList;

/**
 * Created by Martin on 23/02/2016.
 */
public class World {

	private LinkedList<ICollidable> collidables; //TODO custom list supporting buublesort and incrtionsort
	private double width;
	private double height;

	public World (double with, double height){
		this.collidables = new LinkedList<ICollidable>();
	}

	public static World generateWorld(double with, double height, int nbrOfResources) {
		World world = new World(with,height);



		return null;

	}

	private boolean addCollidable(double xPoss, double yPoss, double radius){

		return false;

	}


}
