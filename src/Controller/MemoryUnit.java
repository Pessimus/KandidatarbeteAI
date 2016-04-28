package Controller;

import Model.ICharacterHandle;
import Model.IResource;
import Model.ResourcePoint;
import Utility.Constants;
import org.lwjgl.Sys;

/**
 * Created by Gustav on 2016-04-26.
 */
public class MemoryUnit {
    private double x;
    private double y;
    private ResourcePoint item;
    private IResource.ResourceType type;

    MemoryUnit (ResourcePoint p) {
        item = p;
        x = p.getX();
        y = p.getY();
        type = p.getResource().getResourceType();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public ResourcePoint getResourcePoint() {
        return item;
    }

    public IResource.ResourceType getResourceType() {
        if (type != null) {
            return type;
        } else {
            return IResource.ResourceType.GOLD;
        }
    }

    public boolean exists () {
        return !item.toBeRemoved();
    }

    public boolean surrounding (ICharacterHandle c) {
        if (item == null || type == null) {
            System.out.println("################################################################");
        }
        return Math.abs(c.getX()-getX()) < Constants.CHARACTER_SURROUNDING_RADIUS && Math.abs(c.getY()-getY()) < Constants.CHARACTER_SURROUNDING_RADIUS;
    }
}
