package Controller;

import java.util.LinkedList;
/**
 * Created by Gustav on 2016-03-04.
 */


// A class for containing a single step in a path and methods for moving towards that step
public class PathStep {

    private double nodex;
    private double nodey;

    public PathStep (double x, double y) {
        nodex = x;
        nodey = y;
    }

    // returns whether or not the node has been reached
    public boolean reached (double nowx, double nowy) {
        return Math.abs(nowx - nodex) < 1 && Math.abs(nowy - nodey) < 1;
    }

    // steps the character towards the node (or maybe returns the direction to move towards?)
    public void stepTowards (double nowx, double nowy, double stepSpeed) {
        double diffx = nodex - nowx;
        double diffy = nodey - nowy;

        if (diffx > 0) {
            //if the character should move right
        } else if (diffx < 0) {
            //if the character should move left
        }

        if (diffy > 0) {
            //if the character should move down
        } else if (diffy < 0) {
            //if the character should move left
        }

    }

}
