package Model;

import Model.IResource.ResourceType;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ItemFactory {
	IItem createItem(ResourceType resource){
		switch (resource){
			case WATER:
				return new WaterItem();

			case WOOD:
				return new WoodItem();

			case STONE:
				return new StoneItem();

			case GOLD:
				return null; //TODO: Return GoldItem()

			case FOOD:
				return null; //TODO: Return FoodItem()

			default:
				return null;
		}
	}
}
