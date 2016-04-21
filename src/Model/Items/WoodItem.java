package Model.Items;

import Model.*;
import Model.Character;
import Utility.Constants;

/**
 * Created by Tobias on 2016-03-10.
 */
public class WoodItem implements IItem {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private int amount;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A class representing a amount of "Wood" when in a "Characters" "inventory".
	 * @param amount The amount of "wood" to be represented.
	 */
	public WoodItem(int amount){
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
		rhs.changeHunger(Constants.WOOD_HUNGER_CHANGE_INTERACT);
		rhs.changeEnergy(Constants.WOOD_ENERGY_CHANGE_INTERACT);
		rhs.changeThirst(Constants.WOOD_THIRST_CHANGE_INTERACT);
	}

	@Override
	public void consumedEffect(Character rhs) {
		rhs.changeHunger(Constants.WOOD_HUNGER_CHANGE_CONSUME);
		rhs.changeEnergy(Constants.WOOD_ENERGY_CHANGE_CONSUME);
		rhs.changeThirst(Constants.WOOD_THIRST_CHANGE_CONSUME);
	}

	@Override
	/**{@inheritDoc}*/
	public void consumed(Character rhs) {
		if(amount > 1) {
			consumed(rhs);
			amount--;
		}else if(amount == 1) {
			consumedEffect(rhs);
			rhs.removeFromInventory(this);
		}else{
			rhs.removeFromInventory(this);
		}
	}

	@Override
	/**{@inheritDoc}*/
	public void attacked(Character rhs) {
		rhs.changeHunger(Constants.WOOD_HUNGER_CHANGE_ATTACK);
		rhs.changeEnergy(Constants.WOOD_ENERGY_CHANGE_ATTACK);
		rhs.changeThirst(Constants.WOOD_THIRST_CHANGE_ATTACK);
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
		this.amount = value;
	}

	@Override
	/**{@inheritDoc}*/
	public String getActions() {
		return null;
	}

	@Override
	/**{@inheritDoc}*/
	public IItem.Type getType() {
		return Type.WOOD_ITEM;
	}

	@Override
	/**{@inheritDoc}*/
	public WoodItem clone(){
		return new WoodItem(this.amount);
	}

	@Override
	public int[] getNeedsChange() {
		int[] ret = new int[3]; //hunger, thirst, energy
		ret[0] = Constants.WOOD_HUNGER_CHANGE_CONSUME;
		ret[1] = Constants.WOOD_HUNGER_CHANGE_CONSUME;
		ret[2] = Constants.WOOD_HUNGER_CHANGE_CONSUME;
		return ret;
	}

}
