package Model;

/**
 * Created by Oskar on 2016-04-01.
 */
public class CookedMeatItem implements IItem {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

    private int amount;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

    /**
     * A class representing a amount of "CookedMeat" when in a "Characters" "inventory".
     * @param amount The amount of "cookedmeat" to be represented.
     */
    public CookedMeatItem(int amount){
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
    public void interacted(Character rhs) {
        rhs.changeHunger(Constants.COOKED_MEAT_HUNGER_CHANGE_INTERACT);
        rhs.changeEnergy(Constants.COOKED_MEAT_ENERGY_CHANGE_INTERACT);
        rhs.changeThirst(Constants.COOKED_MEAT_THIRST_CHANGE_INTERACT);
    }

    @Override
    /**{@inheritDoc}*/
    public void consumed(Character rhs) {
        if(amount >= 1) {
            rhs.changeHunger(Constants.COOKED_MEAT_HUNGER_CHANGE_CONSUME);
            rhs.changeEnergy(Constants.COOKED_MEAT_ENERGY_CHANGE_CONSUME);
            rhs.changeThirst(Constants.COOKED_MEAT_THIRST_CHANGE_CONSUME);
            this.amount--;
        } else{
            rhs.removeFromInventory(this);
        }
    }

    @Override
    /**{@inheritDoc}*/
    public void attacked(Character rhs) {
        rhs.changeHunger(Constants.COOKED_MEAT_HUNGER_CHANGE_ATTACK);
        rhs.changeEnergy(Constants.COOKED_MEAT_ENERGY_CHANGE_ATTACK);
        rhs.changeThirst(Constants.COOKED_MEAT_THIRST_CHANGE_ATTACK);
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
        return Type.MEAT_ITEM;
    }   //Saknas type för cooked, behov?
}
