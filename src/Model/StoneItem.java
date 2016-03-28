package Model;

/**
 * Created by Tobias on 2016-03-10.
 */
public class StoneItem implements IItem {
	private int amount;

	public StoneItem(int value){
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
	public Type getType() {
		return Type.STONE_ITEM;
	}
}
