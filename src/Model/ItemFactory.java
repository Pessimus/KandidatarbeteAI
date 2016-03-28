package Model;

import Model.IResource.ResourceType;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ItemFactory {
	IItem createItem(ResourceType resource){
		switch (resource){
			case WATER:
				return new WaterItem(5);

			case WOOD:
				return new WoodItem(5);

			case STONE:
				return new StoneItem(5);

			case GOLD:
				return new GoldItem(5);

			case CROPS:
				return new CropsItem(5);

			case FISH:
				return new FishItem(5);

			case MEAT:
				return new MeatItem(5);

			default:
				return null;
		}
	}
}
