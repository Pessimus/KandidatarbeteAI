package Controller;

import Model.ICharacterHandle;
import Model.IResource;
import Model.ResourcePoint;
import Utility.Constants;
import org.lwjgl.Sys;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gustav on 2016-04-21.
 */
public class ResourceMemory {
    private Node current;
    private Node last;
    private Node first;
    private int size;

    public ResourceMemory () {
        System.out.print("Initializing ResourceMemory");
        last = new Node();
        first = new Node();
        first.next = last;
        last.previous = first;
        current = first;
        size = 0;
        System.out.print("ResourceMemory Online");
    }

    public void reset () {
        current = first;
    }

    public int size() {return size;}

    public boolean hasNext() {
        return !current.next.endNode;
    }

    public ResourcePoint next() {
        current = current.next;
        return current.item;
    }

    public void add (ResourcePoint item) {
        Node newNode = new Node(item, last.previous, last, item.getX(), item.getY()); size++;
    }

    public void addFirst (ResourcePoint item) {
        Node newNode = new Node(item, first, first.next, item.getX(), item.getY()); size++;
    }

    public void clean () {
        Node counter = first.next;
        LinkedList<Node> memory = new LinkedList<>();
        while(!counter.endNode) {
            if (counter.item == null) {
                counter.previous.next = counter.next;
                counter.next.previous = counter.previous;
                if (counter == current) {current = counter.previous;}
                size--;
            }
            counter = counter.next;
        }
        //System.out.println("Done");
    }

    public ResourcePoint remove(int i) {
        Node counter = first.next;
        for (int j = 0; j < i; j++) {
            if (counter.next.endNode) {
                return null;
            }
            counter = counter.next;
        }
        if (counter.endNode) {return null;}
        counter.previous.next = counter.next;
        counter.next.previous = counter.previous;
        if (counter == current) {current = counter.previous;}
        size--;
        return counter.item;
    }

    public boolean remove(ResourcePoint r) {
        Node counter = first.next;
        while (r != counter.item) {
            if (counter.next.endNode) {
                return false;
            }
            counter = counter.next;
        }
        if (counter.endNode) {return false;}
        counter.previous.next = counter.next;
        counter.next.previous = counter.previous;
        if (counter == current) {current = counter.previous;}
        size--;
        return true;
    }

    public ResourcePoint remove() {
        Node counter = current;
        if (current.endNode) {return null;}
        counter.previous.next = counter.next;
        counter.next.previous = counter.previous;
        current = counter.previous;
        size--;
        return counter.item;
    }

    public void clearSurroundings (ICharacterHandle c) {
        reset();
        Node tmp;
        while(hasNext()) {
            current = current.next;
            if (current.item == null || (Math.abs(current.x - c.getX()) < Constants.CHARACTER_SURROUNDING_RADIUS && Math.abs(current.y - c.getY()) < Constants.CHARACTER_SURROUNDING_RADIUS)) {
                tmp = current;
                tmp.previous.next = tmp.next;
                tmp.next.previous = tmp.previous;
                current = current.previous;
                size--;
            }
        }
        reset();
    }

    public ResourcePoint get(int i) {
        Node counter = first.next;
        for (int j = 0; j < i; j++) {
            if (counter.next.endNode) {
                return null;
            }
            counter = counter.next;
        }
        return counter.item;
    }

    public int indexOf (ResourcePoint item) {
        Node counter = first.next;
        for (int i = 0; !counter.endNode; i++) {
            if (counter.item != null && counter.item == item) {return i;}
            counter = counter.next;
        }
        return -1;
    }

    public boolean contains (ResourcePoint item) {
        return indexOf(item) != -1;
    }

    public ResourcePoint getClosest(IResource.ResourceType type, ICharacterHandle c) {
        ResourcePoint p;
        ResourcePoint closest = null;
        IResource.ResourceType pType;
        double distance = Double.MAX_VALUE;
        double testDistance;
        while (hasNext()) {
            p = next();
            pType = p.getResource().getResourceType();
            if (type == IResource.ResourceType.FOOD && (pType == IResource.ResourceType.CROPS || pType == IResource.ResourceType.WATER || pType == IResource.ResourceType.MEAT)) {
                testDistance = Math.pow((current.x-c.getX()), 2) + Math.pow((current.x - c.getY()), 2);
                if (testDistance < distance) {
                    distance = testDistance;
                    closest = p;
                }
            }
            else if (type != IResource.ResourceType.FOOD && pType == type) {
                testDistance = Math.pow((p.getX()-c.getX()), 2) + Math.pow((p.getY() - c.getY()), 2);
                if (testDistance < distance) {
                    distance = testDistance;
                    closest = p;
                }
            }
        }
        reset();
        return closest;
    }

    private class Node {
        Node previous;
        Node next;
        double x;
        double y;
        ResourcePoint item;
        boolean endNode;

        Node () {
            previous = null;
            next = null;
            item = null;
            endNode = true;
            x = -9999;
            y = -9999;
        }

        Node (ResourcePoint item, Node before, Node after, double x, double y) {
            this.item = item;
            next = after;
            previous = before;
            endNode = false;
            before.next = this;
            after.previous = this;
            this.x = x;
            this.y = y;
        }
    }
}
