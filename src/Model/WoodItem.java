package Model;

/**
 * Created by Tobias on 2016-03-10.
 */
public class WoodItem implements IItem {
	private int amount;

	public WoodItem(int value){
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
		return Type.WOOD_ITEM;
	}
}
