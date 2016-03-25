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
    private double adjacentCost;
    private double diagonalCost;

    public Pathfinder (double grid, double worldx, double worldy, double sCost, double dCost) {
        gridSize = grid;
        width = (int) (worldx / grid);
        height = (int) (worldy / grid);
        mask = new boolean[width][height];
        fill(mask, false);
        adjacentCost = sCost;
        diagonalCost = dCost;
    }

    private void fill(boolean[][] m, boolean v)  {
        for (int i = 0; i<width; i++)  {
            Arrays.fill(m[i], v);
        }
    }

    public void updateMask(CollisionList c) {
        fill(mask, true);
        while (c.next()) {
            mask[(int) (c.getX() / gridSize)][(int) (c.getY() / gridSize)] = false;
            for (double i = c.getX() - c.getRadius(); i < c.getX() + c.getRadius(); i = i+gridSize) {
                for (double j = c.getY() - c.getRadius(); j < c.getY() + c.getRadius(); j = j+gridSize) {
                    if (i < gridSize*width && i >= 0 && j < gridSize*height && j >= 0) {
                        mask[(int)(i/gridSize)][(int)(j/gridSize)] = false;
                    }
                    if (j + gridSize > c.getY() + c.getRadius()) {mask[(int)(i/gridSize)][(int)((c.getY() + c.getRadius())/gridSize)] = false;}
                }
                if (i + gridSize > c.getX() + c.getRadius()) {i = c.getX() + c.getRadius();}
            }
        }
    }

    //returns whether or not the given position is empty in the mask. Always returns false for positions outside the grid.
    public boolean isEmpty(double x, double y) {
        if (((int) (x / gridSize)) > width || ((int) (y / gridSize)) > height) {
            return false;
        } else {
            return mask[(int)(x/gridSize)][(int)(y/gridSize)];
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
        LinkedList<Tuple> help = helpPath((int)(startx/gridSize), (int)(starty/gridSize), (int)(endx/gridSize), (int)(endy/gridSize));
        if (help != null) {
            for (Tuple t : help) {
                ret.add(createPathStep(t.x,t.y));
            }
            ret.add(new PathStep(endx, endy));
            return ret;
        } else {
            return null;
        }
    }

    private LinkedList<Tuple> helpPath (int startx, int starty, int endx, int endy) {
        //initialize the open list
        PriorityQueue<Node> open = new PriorityQueue<>();
        //initialize the closed list
        PriorityQueue<Node> closed = new PriorityQueue<>();
        //put the starting node on the open list (you can leave its f at zero)
        open.add(new Node(startx, starty, 0, optimalDistance(startx, starty, endx, endy), null));

        Node q;

        //while the open list is not empty
        while (!open.isEmpty()) {
            //find the node with the least f on the open list, call it "q"
            //pop q off the open list
            q = open.poll();

            //generate q's 8 successors and set their parents to q
            //for each successor
            //successor.g = q.g + distance between successor and q
            //successor.h = distance from goal to successor
            //successor.f = successor.g + successor.h
            LinkedList<Node> successorList = successors(q, endx, endy);

            for (Node s : successorList) {
                boolean add = true;
                //if successor is the goal, stop the search
                //if a node with the same position as successor is in the OPEN list \
                //which has a lower f than successor, skip this successor
                if (s.x == endx && s.y == endy) {
                    LinkedList<Tuple> ret = new LinkedList<Tuple>();
                    while (s.parent != null) {
                        ret.addLast(new Tuple(s.x, s.y));
                        s = s.parent;
                    }
                    return ret;
                }

                for (Node o : open){
                    if (s.equals(o)) {
                        if (s.g >= o.g) {add = false;}
                        else {open.remove(o);}
                        break;
                    }
                }
                if (add) {
                    //if a node with the same position as successor is in the CLOSED list \
                    //which has a lower f than successor, skip this successor
                    for (Node c : closed){
                        if (s.equals(c)) {
                            if (s.g >= c.g) {add = false;}
                            else {closed.remove(c);}
                            break;
                        }
                    }
                }
                //otherwise, add the node to the open list
                if (add) {
                    open.add(s);
                }
            }//end
            //push q on the closed list
            closed.add(q);
        }//end
        return null;
    }

    private PathStep createPathStep (int x, int y) {
        return new PathStep((double)(x*gridSize + gridSize/2), (double)(y*gridSize + gridSize/2));
    }

    private LinkedList<Node> successors(Node n, int endx, int endy) {
        LinkedList<Node> ret = new LinkedList<>();
        // adjacent nodes
        if (n.x + 1 < width && mask[n.x+1][n.y]) {ret.add(new Node(n.x+1,n.y,n.g+adjacentCost,optimalDistance(n.x+1, n.y, endx, endy),n));}
        if (n.x - 1 >= 0 && mask[n.x-1][n.y]) {ret.add(new Node(n.x-1,n.y,n.g+adjacentCost,optimalDistance(n.x-1, n.y, endx, endy),n));}
        if (n.y + 1 < height && mask[n.x][n.y+1]) {ret.add(new Node(n.x,n.y+1,n.g+adjacentCost,optimalDistance(n.x, n.y+1, endx, endy),n));}
        if (n.y - 1 >= 0 && mask[n.x][n.y-1]) {ret.add(new Node(n.x,n.y-1,n.g+adjacentCost,optimalDistance(n.x, n.y-1, endx, endy),n));}

        // diagonal nodes
        if (adjacentCost <= diagonalCost/2) {
            if (n.x + 1 < width) {
                if (n.y + 1 < height && mask[n.x+1][n.y+1]) {ret.add(new Node(n.x+1,n.y+1,n.g+diagonalCost,optimalDistance(n.x+1, n.y+1, endx, endy),n));}
                if (n.y - 1 >= 0 && mask[n.x+1][n.y-1]) {ret.add(new Node(n.x+1,n.y-1,n.g+diagonalCost,optimalDistance(n.x+1, n.y-1, endx, endy),n));}
            }
            if (n.x - 1 >= 0) {
                if (n.y + 1 < height && mask[n.x-1][n.y+1]) {ret.add(new Node(n.x-1,n.y+1,n.g+diagonalCost,optimalDistance(n.x-1, n.y+1, endx, endy),n));}
                if (n.y - 1 >= 0 && mask[n.x-1][n.y+1]) {ret.add(new Node(n.x-1,n.y-1,n.g+diagonalCost,optimalDistance(n.x-1, n.y-1, endx, endy),n));}
            }
        }
        return ret;
    }

    private class Tuple {
        public int x;
        public int y;
        public Tuple (int u, int v) {x=u; y=v;}
    }

    private class Node implements Comparable<Node> {
        public int x;
        public int y;
        public double g;
        public double h;
        public double f;
        public Node parent;

        @Override
        public boolean equals(Object o) {
            boolean ret = false;
            if (o != null && o instanceof Node) {
                ret = this.x == ((Node) o).x && this.y == ((Node) o).y;
            }
            return ret;
        }

        public int compareTo(Node n) {
            if (this.h > n.h) {
                return 1;
            } else if (this.h < n.h) {
                return -1;
            } else {
                return 0;
            }
        }

        public Node (int a, int b, double c, double d, Node n) {
            x=a; y=b; g=c; h=d; parent = n;
            f = g+h;
        }
    }

	//
    private double optimalDistance(int nodex, int nodey, int endx, int endy) {

        
        int movex = Math.abs(nodex - endx);
        int movey = Math.abs(nodey - endy);
        if (movex == 0 || movey == 0 || adjacentCost > diagonalCost/2) { //if we move in a straight line or its cheaper to move straight twice than to move diagonally
            return movey*adjacentCost + movex*adjacentCost;
        } else if (movex > movey) {
            return (movex - movey)*adjacentCost + movey*diagonalCost;
        } else if (movey > movex) {
            return (movey - movex)*adjacentCost + movex*diagonalCost;
        } else {
            return movex*diagonalCost;
        }
		// Maybe wrong math for what this method should do.
		// I just assumed this was what was intended!
		//double dx = nodex-endx;
		//double dy = nodey-endy;
		//return Math.sqrt((dx*dx) + (dy*dy));
        //b77d096800899bede09d64ed42a2f345d96a9eb8
    }
}