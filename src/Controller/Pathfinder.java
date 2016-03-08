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
        Arrays.fill(mask, false);
        adjacentCost = sCost;
        diagonalCost = dCost;
    }

    public void updateMask(CollisionList c) {
        Arrays.fill(mask, true);
        while (c.next()) {
            mask[(int) (c.getX() / gridSize)][(int) (c.getY() / gridSize)] = false;
            for (double i = (c.getX() - c.getRadius())/gridSize; i < (c.getX() + c.getRadius())/gridSize; i = i+gridSize) {

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
        open.add(new Node(startx, starty, 0, optimalDistance(startx, starty, endx, endy), null));

        Node q;

        //while the open list is not empty
        while (!open.isEmpty()) {
            //find the node with the least f on the open list, call it "q"
            //pop q off the open list
            q = open.poll();

            //generate q's 8 successors and set their parents to q
            //for each successor
            //if successor is the goal, stop the search
            //successor.g = q.g + distance between successor and q
            //successor.h = distance from goal to successor
            //successor.f = successor.g + successor.h
            LinkedList<Node> successorList = successors(q, endx, endy);

            for (Node s : successorList) {
                boolean add = true;
                //if a node with the same position as successor is in the OPEN list \
                //which has a lower f than successor, skip this successor
                for (Node o : open){
                    if (s.equals(o)) {
                        if (s.compareTo(o) > 0) {add = false;}
                        break;
                    }
                }
                if (add) {
                    //if a node with the same position as successor is in the CLOSED list \
                    //which has a lower f than successor, skip this successor
                    for (Node c : closed){
                        if (s.equals(c)) {
                            if (s.compareTo(c) > 0) {add = false;}
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
        if (n.x + 1 < width) {ret.add(new Node(n.x+1,n.y,n.g+adjacentCost,optimalDistance(n.x+1, n.y, endx, endy),n));}
        if (n.x - 1 >= 0) {ret.add(new Node(n.x-1,n.y,n.g+adjacentCost,optimalDistance(n.x-1, n.y, endx, endy),n));}
        if (n.y + 1 < height) {ret.add(new Node(n.x,n.y+1,n.g+adjacentCost,optimalDistance(n.x, n.y+1, endx, endy),n));}
        if (n.y - 1 >= 0) {ret.add(new Node(n.x,n.y-1,n.g+adjacentCost,optimalDistance(n.x, n.y-1, endx, endy),n));}

        // diagonal nodes
        if (adjacentCost <= diagonalCost/2) {
            if (n.x + 1 < width) {
                if (n.y + 1 < height) {ret.add(new Node(n.x+1,n.y+1,n.g+diagonalCost,optimalDistance(n.x+1, n.y+1, endx, endy),n));}
                if (n.y - 1 >= 0) {ret.add(new Node(n.x+1,n.y-1,n.g+diagonalCost,optimalDistance(n.x+1, n.y-1, endx, endy),n));}
            }
            if (n.x - 1 >= 0) {
                if (n.y + 1 < height) {ret.add(new Node(n.x-1,n.y+1,n.g+diagonalCost,optimalDistance(n.x-1, n.y+1, endx, endy),n));}
                if (n.y - 1 >= 0) {ret.add(new Node(n.x-1,n.y-1,n.g+diagonalCost,optimalDistance(n.x-1, n.y-1, endx, endy),n));}
            }
        }
        return ret;
    }

    private class Tuple {
        public int x;
        public int y;
        public Tuple (int u, int v) {x=u; y=v;}
    }

    public LinkedList<PathStep> getPath(int startx, int starty, int endx, int endy) {
        return null;
    } //unsure if needed

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
            if (this.f > n.f) {
                return 1;
            } else if (this.f < n.f) {
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
    }
}