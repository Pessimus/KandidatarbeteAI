package Model;
import java.util.LinkedList;
/**
 * Created by Gustav on 2016-03-25.
 */
public class Outcome {
    private boolean consume;
    private LinkedList<IItem.Type> gainedItems;
    private LinkedList<IItem.Type> lostItems;
    private int hungerChange;
    private int thirstChange;
    private int energyChange;
    private int time;

    public boolean isConsumable() {return consume;}
    public LinkedList<IItem.Type> getGained() {return gainedItems;}
    public LinkedList<IItem.Type> getLost() {return lostItems;}
    public int getHunger() {return hungerChange;}
    public int getThirst() {return thirstChange;}
    public int getEnergy() {return energyChange;}
    public int getTime() {return time;}

    public Outcome () {
        consume = false;
        gainedItems = new LinkedList<IItem.Type>();
        lostItems = new LinkedList<IItem.Type>();
        hungerChange = 0;
        thirstChange = 0;
        energyChange = 0;
        time = 1;
    }
    public Outcome (boolean consume, int hunger, int thirst, int energy, int time) {
        this.consume = consume;
        gainedItems = new LinkedList<IItem.Type>();
        lostItems = new LinkedList<IItem.Type>();
        hungerChange = hunger;
        thirstChange = thirst;
        energyChange = energy;
        this.time = time;
    }
    public Outcome (boolean consume, LinkedList<IItem.Type> gained, LinkedList<IItem.Type> lost, int time) {
        this.consume = consume;
        gainedItems = gained;
        lostItems = lost;
        hungerChange = 0;
        thirstChange = 0;
        energyChange = 0;
        this.time = time;
    }

}
