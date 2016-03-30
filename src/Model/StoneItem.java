package Model;

/**
 * Created by Tobias on 2016-03-10.
 */
public class StoneItem implements IItem {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private int amount;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public StoneItem(int value){
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

//---------------------------------------Getters & Setters------------------------------------------------------------\\

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
	public Type getType() {
		return Type.STONE_ITEM;
	}
}
