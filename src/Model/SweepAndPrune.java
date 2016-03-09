package Model;

/**
 * Created by Martin on 09/03/2016.
 */
public class SweepAndPrune {

	private CollisionList xList;
	private CollisionList yList;

	public SweepAndPrune(){
		xList = new CollisionList(CollisionList.Axis.X);
		yList = new CollisionList(CollisionList.Axis.Y);
	}

	public void sort(){
		this.xList.sort();
		this.yList.sort();
	}

	public void add(ICollidable collidable){
		this.xList.add(collidable);
		this.yList.add(collidable);
	}

	public void remove(ICollidable collidable){
		this.xList.remove(collidable);
		this.yList.remove(collidable);
	}

	public void collideCheck(){

	}

}
