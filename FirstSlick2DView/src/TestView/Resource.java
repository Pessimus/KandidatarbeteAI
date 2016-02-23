package TestView;

/**
 * Created by Oskar on 2016-02-17.
 */
public class Resource {

    int xPos;
    int yPos;
    int value;
    boolean depleted = false;

    public int getYPos()
    {
        return yPos;
    }
    public int getXPos()
    {
        return xPos;
    }
    public boolean isDepleted() { return depleted;}
    /*public Resource(int x, int y, int amount)
    {
        xPos=x;
        yPos=y;
        value=amount;
        depleted=false;
    }*/




}
