package Model;

/**
 * Created by Tobias on 2016-03-10.
 */
public class WaterItem implements IItem {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private int amount;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public WaterItem(int value){
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
		rhs.changeHunger(Constants.WATER_HUNGER_CHANGE_INTERACT);
		rhs.changeEnergy(Constants.WATER_ENERGY_CHANGE_INTERACT);
		rhs.changeThirst(Constants.WATER_THIRST_CHANGE_INTERACT);
	}

	@Override
	public void consumed(Character rhs) {
		rhs.changeHunger(Constants.WATER_HUNGER_CHANGE_CONSUME);
		rhs.changeEnergy(Constants.WATER_ENERGY_CHANGE_CONSUME);
		rhs.changeThirst(Constants.WATER_THIRST_CHANGE_CONSUME);
		this.amount--;
	}

	@Override
	public void attacked(Character rhs) {
		rhs.changeHunger(Constants.WATER_HUNGER_CHANGE_ATTACK);
		rhs.changeEnergy(Constants.WATER_ENERGY_CHANGE_ATTACK);
		rhs.changeThirst(Constants.WATER_THIRST_CHANGE_ATTACK);
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
	public IItem.Type getType() {
		return Type.WATER_ITEM;
	}
}
