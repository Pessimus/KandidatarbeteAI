package Model;

/**
 * Created by Oskar on 2016-03-10.
 */
public class Meat extends FiniteResource {

    public static final ResourceType resourceType = ResourceType.MEAT;
    public static final String resourceName = "Meat";

    public Meat(int initial){
        super(initial);
    }

    @Override
    public ItemFactory getItemFactory() {
        return null;
    }

    @Override
    public IItem gatherResource() {
        int resourceLeft = getResourcesLeft();
        if(resourceLeft>0){
            setResourcesLeft(resourceLeft-1);
            return  null;
        }
        else{
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
    public void interacted(Character rhs) {

    }

    @Override
    public void consumed(Character rhs) {

    }

    @Override
    public void attacked(Character rhs) {

    }
}
