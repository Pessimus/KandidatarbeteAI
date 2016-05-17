package Model;

import Utility.Constants;
import java.util.LinkedList;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Inventory{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	LinkedList<IItem> inventoryItems;


//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * Creates a new inventory for handling a characters items.
	 */
	public Inventory(){
		inventoryItems = new LinkedList<>();
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	/** @return a list of all items in the characters inventory. */
	public LinkedList<IItem> getItems(){
		return inventoryItems;
	}

	/**
	 * Adds a item with the specified amount to the characters inventory.
	 * IF the amount is more than the inventory can hold, as much as possible is added.
	 * @param item the item to be added.
	 * @return true if items are successfully added, else false.
	 */
	public boolean addItem(IItem item){
		for (IItem invItem : inventoryItems) {
			if(item == null) {
				return false;
			}
			if (invItem != null && item != null) {
				if (item.getType() == invItem.getType() && invItem.getAmount() < Constants.MAX_AMOUNT) {
					if (invItem.getAmount() + item.getAmount() > Constants.MAX_AMOUNT) {
						item.setAmount((invItem.getAmount() + item.getAmount()) % Constants.MAX_AMOUNT);
						invItem.setAmount(Constants.MAX_AMOUNT);
						return inventoryItems.add(item.clone());
					} else {
						invItem.addAmount(item.getAmount());
						return true;
					}
				}
			}
		}
		if (inventoryItems.size() < Constants.MAX_INVENTORY_SLOTS) {
			return inventoryItems.add(item.clone());
		}

		return false;
	}

	/**
	 * Remove the item with the specified amount from the characters inventory.
	 * If the inventory does not contain that item of that amount nothing is removed.
	 * @param item the item to be removed.
	 * @return true if the item is removed, else false.
	 */
	boolean removeItem(IItem item){
		for(IItem invItem : inventoryItems) {
			if(item.getType() == invItem.getType()){
				if(item.getAmount() == invItem.getAmount()) {
					return inventoryItems.remove(invItem);
				}if(item.getAmount() < invItem.getAmount()){
					invItem.removeAmount(item.getAmount());
					return true;
				}
			}
		}
		return false;//Item not in inventory.
	}


	public boolean contains(IItem item) {
		for(IItem tmpItem : inventoryItems){
			if(tmpItem.getType().equals(item.getType()) && tmpItem.getAmount() >= item.getAmount()){
				return true;
			}
		}
		return false;
	}
}
