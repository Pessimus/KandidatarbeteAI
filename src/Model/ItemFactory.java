package Model;

import Model.IResource.ResourceType;

/**
 * Created by Tobias on 2016-02-26.
 */
public class ItemFactory {
	public static IItem createItem(ResourceType resource, int amount){
		switch (resource){
			case WATER:
				return new WaterItem(amount);

			case WOOD:
				return new WoodItem(amount);

			case STONE:
				return new StoneItem(amount);

			case GOLD:
				return new GoldItem(amount);

			case CROPS:
				return new CropsItem(amount);

			case FISH:
				return new FishItem(amount);

			case MEAT:
				return new MeatItem(amount);

			default:
				return null;
		}
	}
}
