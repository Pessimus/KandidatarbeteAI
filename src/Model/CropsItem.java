package Model;

/**
 * Created by Oskar on 2016-03-10.
 */
public class CropsItem implements IItem {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    private int amount;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

    public CropsItem(int value){
        this.amount = value;
    }

//---------------------------------------Interaction methods----------------------------------------------------------\\

    @Override
    public void addAmount(int value) {
        this.amount += value;
    }

	@Override
	public void removeAmount(int value) {
		if (value <= amount){
			amount = amount-value;
		}
	}

    @Override
    public void interacted(Character rhs) {
		//TODO implement
    }

    @Override
    public void consumed(Character rhs) {
		//TODO implement
    }

    @Override
    public void attacked(Character rhs) {
		//TODO implement
    }

//---------------------------------------Getters & Setters------------------------------------------------------------\\

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
