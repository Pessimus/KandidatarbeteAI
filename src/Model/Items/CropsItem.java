package Model.Items;

import Model.*;
import Model.Character;
import Utility.Constants;

/**
 * Created by Oskar on 2016-03-10.
 */
public class CropsItem implements IItem {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

    private int amount;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing a amount of "Crops" when in a "Characters" "inventory".
	 * @param amount The amount of "crops" to be represented.
	 */
    public CropsItem(int amount){
        this.amount = amount;
    }

//---------------------------------------Interaction methods----------------------------------------------------------\\

    @Override
	/**{@inheritDoc}*/
    public void addAmount(int value) {
        this.amount += value;
    }

	@Override
	/**{@inheritDoc}*/
	public void removeAmount(int value) {
		if (value <= amount){
			amount = amount-value;
		}
	}

    @Override
	/**{@inheritDoc}*/
    public void interacted(Model.Character rhs) {
		rhs.changeHunger(Constants.CROP_HUNGER_CHANGE_INTERACT);
		rhs.changeEnergy(Constants.CROP_ENERGY_CHANGE_INTERACT);
		rhs.changeThirst(Constants.CROP_THIRST_CHANGE_INTERACT);
    }

    @Override
	/**{@inheritDoc}*/
    public void consumed(Character rhs) {
        if(amount > 1) {
            rhs.changeHunger(Constants.CROP_HUNGER_CHANGE_CONSUME);
            rhs.changeEnergy(Constants.CROP_ENERGY_CHANGE_CONSUME);
            rhs.changeThirst(Constants.CROP_THIRST_CHANGE_CONSUME);
            amount--;
        }
        else if (amount == 1){
			rhs.changeHunger(Constants.CROP_HUNGER_CHANGE_CONSUME);
			rhs.changeEnergy(Constants.CROP_ENERGY_CHANGE_CONSUME);
			rhs.changeThirst(Constants.CROP_THIRST_CHANGE_CONSUME);
			rhs.removeFromInventory(this);
        }
		else {
			rhs.removeFromInventory(this);
		}
    }

    @Override
	/**{@inheritDoc}*/
    public void attacked(Character rhs) {
		rhs.changeHunger(Constants.CROP_HUNGER_CHANGE_ATTACK);
		rhs.changeEnergy(Constants.CROP_ENERGY_CHANGE_ATTACK);
		rhs.changeThirst(Constants.CROP_THIRST_CHANGE_ATTACK);
    }

//---------------------------------------Getters & Setters------------------------------------------------------------\\

    @Override
	/**{@inheritDoc}*/
    public int getAmount() {
        return amount;
    }

    @Override
	/**{@inheritDoc}*/
    public void setAmount(int value) {
        amount = value;
    }

    @Override
	/**{@inheritDoc}*/
    public String getActions() {
        return null;
    }

    @Override
	/**{@inheritDoc}*/
    public IItem.Type getType() {
        return Type.CROPS_ITEM;
    }

	@Override
	/**{@inheritDoc}*/
	public CropsItem clone(){
		return new CropsItem(this.amount);
	}

}
