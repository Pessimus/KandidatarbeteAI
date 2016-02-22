package Model;

/**
 * Created by Tobias on 2016-02-20.
 */
public class Terrain extends WorldCell{

	public Terrain(){
		setTraversable(true);
	}

	public Terrain(int x, int y){
		super(x, y, true);
	}

	protected Terrain(int x, int y, boolean traversable){
		super(x, y, traversable);
	}
}
