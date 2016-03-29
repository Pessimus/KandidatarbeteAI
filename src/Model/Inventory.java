package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Inventory{
	LinkedList<IItem> inventoryItems;

	public Inventory(){
		inventoryItems = new LinkedList<IItem>();

		//TODO remove this test----------------
		//---------Test cases for inventory display
				GoldItem gi = new GoldItem(5);
				FishItem fi = new FishItem(5);
				WaterItem wi = new WaterItem(5);
				StoneItem si = new StoneItem(5);
				WaterItem wi2 = new WaterItem(10);

				addItem(gi);
				addItem(fi);
				addItem(wi);
				addItem(si);
				addItem(wi2);

	}

	public LinkedList<IItem> getItems(){
		return inventoryItems;
	}

	public boolean addItem(IItem item){
		for(IItem invItem : inventoryItems){
			if(item.getType() == invItem.getType()){
				invItem.addAmount(item.getAmount());
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
