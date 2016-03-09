package Model;

/**
 * Created by Oskar on 2016-03-04.
 */
public class Wood extends RenewableResource {

    public static final int MAX_TREE_RESOURCES = 150;
    public static final ResourceType resourceType = ResourceType.WOOD;
    public static final String resourceName = "Wood";


    public Wood(int initial) {
        super(initial, MAX_TREE_RESOURCES);
    }

    public Wood(int initial, int maxResources){
        super(initial, maxResources);
    }
    @Override
    public IItemFactory getItemFactory() {
        return null;
    }

    @Override
    public IItem gatherResource() {
        if(getResourcesLeft()>0) {
            //return getItemFactory() Skapa träd
            //setResourceLeft(getResourcesLeft()-1);
            return null;
        }
        else {
            return null;
        }
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }
    @Override
    public void update() { //öka mängd eller spawna nya träd?

    }
}
