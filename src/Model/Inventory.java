package Model;

import java.util.LinkedList;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Inventory{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	LinkedList<IItem> inventoryItems;


//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public Inventory(){
		inventoryItems = new LinkedList<>();
	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	public LinkedList<IItem> getItems(){
		return inventoryItems;
	}

	public boolean addItem(IItem item){
		for(IItem invItem : inventoryItems){
			if(item.getType() == invItem.getType() && invItem.getAmount()<Constants.MAX_AMOUNT){
				if(invItem.getAmount()+item.getAmount() > Constants.MAX_AMOUNT) {

					item.setAmount((invItem.getAmount()+item.getAmount())%Constants.MAX_AMOUNT);
					System.out.println((invItem.getAmount()+item.getAmount())%Constants.MAX_AMOUNT);
					invItem.setAmount(Constants.MAX_AMOUNT);
					return inventoryItems.add(item);
				}else{
					invItem.addAmount(item.getAmount());
				}
				return true;
			}
		}
		if(inventoryItems.size() < Constants.MAX_INVENTORY_SLOTS){
			return inventoryItems.add(item);
		}
		return false;
	}

	boolean removeItem(IItem item){
		for(IItem invItem : inventoryItems) {
			if(item.getType() == invItem.getType()){
				if (invItem.getAmount()>item.getAmount()){
					invItem.removeAmount(item.getAmount());
					return true;
				}else if(invItem.getAmount() == item.getAmount()){
					return inventoryItems.remove(item);
				}else{//Not that many items of that type in inventory.
					return false;
				}
			}
		}
		return false;//Item not in inventory.
	}


}
