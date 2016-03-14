package Model;

/**
 * Created by Oskar on 2016-03-10.
 */
public class Gold extends FiniteResource {

    public static final ResourceType resourceType = ResourceType.GOLD;
    public static final String resourceName = "Gold";

    public Gold(int initial){
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
}
