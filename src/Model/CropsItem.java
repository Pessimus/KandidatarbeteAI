package Model;

/**
 * Created by Oskar on 2016-03-10.
 */
public class CropsItem implements IItem {
    private int amount;

    public CropsItem(int value){
        this.amount = value;
    }

    @Override
    public void addAmount(int value) {
        this.amount += value;
    }

    @Override
    public Outcome interact() {
        return null;//TODO implement
    }

    @Override
    public Outcome consume() {
        return null;//TODO implement
    }

    @Override
    public Outcome attack() {
        return null;//TODO implement
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int value) {
        amount = value;
    }

    @Override
    public String getActions() {
        return null;
    }

    @Override
    public IItem.Type getType() {
        return Type.CROPS_ITEM;
    }
}
