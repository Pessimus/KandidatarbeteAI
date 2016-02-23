package TestView;

/**
 * Created by Oskar on 2016-02-17.
 */
public class Tree extends Resource {

    public Tree(int x, int y, int amount)
    {
        xPos = x;
        yPos = y;
        value = amount;
    }

    public void gather()
    {
        if(value > 0) {
            value--;
            if(value==0)
            {
                depleted=true;
            }
        }
    }
}
