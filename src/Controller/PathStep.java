package Controller;

import Model.Character;

/**
 * Created by Gustav on 2016-03-04.
 */


// A class for containing a single step in a path and methods for moving towards that step
public class PathStep {

    private double nodex;
    private double nodey;
    final static double reached_distance = 1;

    public PathStep (double x, double y) {
        nodex = x;
        nodey = y;
    }

    // returns whether or not the node has been reached
    public boolean reached (double nowx, double nowy) {
        return Math.abs(nowx - nodex) <= reached_distance && Math.abs(nowy - nodey) <= reached_distance;
    }

    // returns whether or not the node is within the given range
    public boolean reached (double nowx, double nowy, double range) {
        return Math.abs(nowx - nodex) <= range && Math.abs(nowy - nodey) <= range;
    }

    // steps the given character towards the node and returns true if they reached the node
    public boolean stepTowards (Character c) {
        double diffx = nodex - c.getX();
        double diffy = nodey - c.getY();
        int ret = 0;

        if (reached(c.getX(), c.getY(), c.getSteplength())) {
            return true;
        }

        if (diffx > c.getSteplength()) {
            //if the character should move right
            c.moveRight();
        } else if (diffx < -c.getSteplength()) {
            //if the character should move left
            c.moveLeft();
        } else {
            ret += 1;
        }

        if (diffy > c.getSteplength()) {
            //if the character should move down
            c.moveDown();
        } else if (diffy < -c.getSteplength()) {
            //if the character should move left
            c.moveUp();
        } else {
            ret += 1;
        }

        return ret == 2;
    }

    // returns the direction (in degrees) from the given coordinates towards this node (not implemented)
    public double direction (double x, double y) {
        /*double diffx = nodex - x;
        double diffy = nodey - y;
        double tangent diffy/diffx =*/
        return 0;
    }
}
