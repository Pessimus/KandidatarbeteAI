package Controller;

import Model.CollisionList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
/**
 * Created by Gustav on 2016-03-04.
 */
public class Pathfinder {

    private double gridSize;
    private boolean[][] mask;
    private int width;
    private int height;
    private double straightCost; // the cost of moving to an adjecent node
    private double diagonalCost; // the cost of moving to a diagonal node (like Inf if diagonal not possible. Otherwise like sqrt(2*straightCost))

    public Pathfinder (double grid, double worldx, double worldy, double sCost, double dCost) {
        gridSize = grid;
        width = (int) (worldx / grid);
        height = (int) (worldy / grid);
        mask = new boolean[width][height];
        Arrays.fill(mask, false);
        straightCost = sCost;
        diagonalCost = dCost;
    }

    public void updateMask(CollisionList c) {
        Arrays.fill(mask, true);
        while (c.next()) {
            mask[(int) (c.getX() / gridSize)][(int) (c.getY() / gridSize)] = false;
            for (double i = (c.getX() - c.getRadius())/gridSize; i < (c.getX() + c.getRadius())/gridSize; i = i+gridSize) {
                for (double j = (c.getY() - c.getRadius())/gridSize; i < (c.getX() + c.getRadius())/gridSize; i = i+gridSize) {
                    mask[(int) i][(int) j] = false;
                }
            }
        }
    }

    //returns whether or not the given position is empty in the mask. Always returns false for positions outside the grid.
    public boolean isEmpty(double x, double y) {
        if (((int) (x * gridSize)) > width || ((int) (y * gridSize)) > height) {
            return false;
        } else {
            return mask[(int)(x*gridSize)][(int)(y*gridSize)];
        }
    }

    public boolean[][] getMask() {
        return mask;
    }

    public double getGridSize() {
        return gridSize;
    }

    public LinkedList<PathStep> getPath (double startx, double starty, double endx, double endy) {
        LinkedList<PathStep> ret = new LinkedList<>();
        for (Tuple t : helpPath((int)(startx/gridSize), (int)(starty/gridSize), (int)(endx/gridSize), (int)(endy/gridSize))) {
            ret.add(createPathStep(t.x,t.y));
        }
        return ret;
    }

    private LinkedList<Tuple> helpPath (int startx, int starty, int endx, int endy) {
        //initialize the open list
        PriorityQueue<Node> open = new PriorityQueue<>();
        //initialize the closed list
        PriorityQueue<Node> closed = new PriorityQueue<>();
        //put the starting node on the open list (you can leave its f at zero)
        //open.add(new Node(startx, starty, ))

        //while the open list is not empty
        //find the node with the least f on the open list, call it "q"
        //pop q off the open list
        //generate q's 8 successors and set their parents to q
        //for each successor
        //if successor is the goal, stop the search
        //successor.g = q.g + distance between successor and q
        //successor.h = distance from goal to successor
        //successor.f = successor.g + successor.h

        //if a node with the same position as successor is in the OPEN list \
        //which has a lower f than successor, skip this successor
        //if a node with the same position as successor is in the CLOSED list \
        //which has a lower f than successor, skip this successor
        //otherwise, add the node to the open list
        //end
        //push q on the closed list
        //end
        return null;
    }

    private PathStep createPathStep (int x, int y) {
        return new PathStep((double)(x*gridSize + gridSize/2), (double)(y*gridSize + gridSize/2));
    }

    private class Tuple {
        public int x;
        public int y;
        public Tuple (int u, int v) {x=u; y=v;}
    }

    private class Node {
        public int x;
        public int y;
        public double g;
        public double h;
        public double f;
        public Node successor;

        public Node (int a, int b, double c, double d, Node n) {
            x=a; y=b; g=c; h=d; successor = n;
            f = g+h;
        }
    }

    private double optimalDistance(int nodex, int nodey, int endx, int endy) {

    }
}