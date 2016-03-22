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
	}

	public Inventory(LinkedList<IItem> startingItems){
		inventoryItems = startingItems;
	}

	LinkedList<IItem> getItems(){
		return inventoryItems;
	}

	boolean addItem(IItem item){
		return inventoryItems.add(item);
	}

	boolean removeItem(IItem item){
		return inventoryItems.remove(item);
	}


}
