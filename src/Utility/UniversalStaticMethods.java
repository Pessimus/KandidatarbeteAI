package Utility;

/**
 * Created by Tobias on 2016-04-04.
 */
public class UniversalStaticMethods {
	public static double distanceBetweenPoints(double x1, double y1, double x2, double y2){
		double dx = Math.abs(x1 - x2);
		double dy = Math.abs(y1 - y2);
		return dx + dy;
	}
}
