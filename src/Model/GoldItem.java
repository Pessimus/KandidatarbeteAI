package Model;

/**
 * Created by Oskar on 2016-03-10.
 */
public class GoldItem implements IItem {
    private int amount;

    public GoldItem(int value){
        this.amount = value;
    }

    @Override
    public void addAmount(int value) {
        this.amount += value;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int value) {
        this.amount = value;
    }

    @Override
    public String getActions() {
        return null;
    }

    @Override
    public IItem.Type getType() {
        return Type.GOLD_ITEM;
    }
}