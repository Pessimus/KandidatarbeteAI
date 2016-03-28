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

	public Inventory(LinkedList<IItem> startingItems){
		inventoryItems = startingItems;
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
		return inventoryItems.remove(item);
	}


}
