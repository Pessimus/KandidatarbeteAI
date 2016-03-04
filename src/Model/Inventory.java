package Model;

import java.util.List;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Inventory{
	List<IItem> inventoryItems;

	public Inventory(){
		;
	}

	public Inventory(List<IItem> startingItems){
		inventoryItems = startingItems;
	}

	List<IItem> getItems(){
		return inventoryItems;
	}

	boolean addItem(IItem item){
		return inventoryItems.add(item);
	}
}
