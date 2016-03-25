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
				return new GoldItem();

			case CROPS:
				return new CropsItem();

			case FISH:
				return new FishItem();

			case MEAT:
				return new MeatItem();

			default:
				return null;
		}
	}
}
