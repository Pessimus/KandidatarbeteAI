package Controller;

import Model.CollisionList;

import java.util.Arrays;
import java.util.LinkedList;
/**
 * Created by Gustav on 2016-03-04.
 */
public class Pathfinder {

    private double gridSize;
    private boolean[][] mask;
    private int width;
    private int height;

    public Pathfinder(double grid, double worldx, double worldy) {
        gridSize = grid;
        width = (int) (worldx / grid);
        height = (int) (worldy / grid);
        mask = new boolean[width][height];
        Arrays.fill(mask, false);
    }

    public void updateMask(CollisionList c) {

    }

    public boolean isFree(double x, double y) {
        if (((int) x * gridSize) > width || ((int) y * gridSize) > height) {
            return false;
        } else {
            return mask[(int)(x*gridSize)][(int) (y*gridSize)];
        }
    }

    public boolean[][] getMask() {
        return mask;
    }

    public double getGridSize() {
        return gridSize;
    }

    public LinkedList<PathStep> getPath(int startx, int starty, int endx, int endy) {
        return null;
    }

}