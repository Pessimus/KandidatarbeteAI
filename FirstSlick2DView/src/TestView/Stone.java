package TestView;

/**
 * Created by Oskar on 2016-02-22.
 */
public class Stone extends Resource {

    public Stone(int x, int y, int amount)
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
