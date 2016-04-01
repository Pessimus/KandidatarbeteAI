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
		rhs.changeHunger(Constants.CROP_HUNGER_CHANGE_INTERACT);
		rhs.changeEnergy(Constants.CROP_ENERGY_CHANGE_INTERACT);
		rhs.changeThirst(Constants.CROP_THIRST_CHANGE_INTERACT);
    }

    @Override
    public void consumed(Character rhs) {
        if(amount > 1) {
            rhs.changeHunger(Constants.CROP_HUNGER_CHANGE_CONSUME);
            rhs.changeEnergy(Constants.CROP_ENERGY_CHANGE_CONSUME);
            rhs.changeThirst(Constants.CROP_THIRST_CHANGE_CONSUME);
            amount--;
        }else{
            rhs.removeFromInventory(this);
        }
    }

    @Override
    public void attacked(Character rhs) {
		rhs.changeHunger(Constants.CROP_HUNGER_CHANGE_ATTACK);
		rhs.changeEnergy(Constants.CROP_ENERGY_CHANGE_ATTACK);
		rhs.changeThirst(Constants.CROP_THIRST_CHANGE_ATTACK);
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
