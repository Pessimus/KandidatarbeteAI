package Toolkit;


import java.security.InvalidParameterException;

/**
 * Created by Tobias on 2016-03-27.
 */
public final class CoordinateTuple {
	private final float posX, posY;

	public CoordinateTuple(float x, float y){
		if(x < 0 || y < 0){
			throw new InvalidParameterException("Coordinates are non-negative!");
		}

		posX = x;
		posY = y;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}
}
