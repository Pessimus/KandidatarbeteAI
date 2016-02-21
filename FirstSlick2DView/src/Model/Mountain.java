package Model;

/**
 * Created by Tobias on 2016-02-21.
 */
public class Mountain extends WorldCell {
	protected Mountain(){
		setTraversable(false);
	}

	protected Mountain(int x, int y){
		super(x, y, false);
	}
}
