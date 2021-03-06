package Model;

import Model.IStructure.StructureType;
import Model.Items.StoneItem;
import Model.Items.WoodItem;
import Model.Structures.Farm;
import Model.Structures.House;
import Model.Structures.Stockpile;
import Utility.Constants;
import java.util.LinkedList;

/**
 * Created by Martin on 2016-04-06.
 */
public class StructureFactory {
	/**
	 * A method for generating structures.
	 * @param type the type of structure to generate.
	 * @return a item of the the specified type and amount.
	 */
	public static IStructure createStructure(StructureType type, double x, double y){
		switch (type){
			case STOCKPILE:
				return new Stockpile(x, y);

			case HOUSE:
				return new House(x, y);

			case FARM:
				return new Farm(x, y);

			default:
				return null;
		}
	}

	public static int getWaitTime(StructureType typeToSpawn){
		switch (typeToSpawn){
			case STOCKPILE:
				return Constants.STOCKPILE_BUILD_TIME;

			case HOUSE:
				return Constants.HOUSE_BUILD_TIME;

			case FARM:
				return Constants.FARM_BUILD_TIME;

			default:
				return 0;
		}
	}

	public static LinkedList<IItem> getCost(StructureType typeToSpawn) {
		LinkedList<IItem> cost = new LinkedList<>();
		switch (typeToSpawn){
			case STOCKPILE:
				cost.add(new StoneItem(Constants.STOCKPILE_STONE_COST));
				cost.add(new WoodItem(Constants.STOCKPILE_WOOD_COST));
				return cost;

			case HOUSE:
				cost.add(new StoneItem(Constants.HOUSE_STONE_COST));
				cost.add(new WoodItem(Constants.HOUSE_WOOD_COST));
				return cost;

			case FARM:

				cost.add(new StoneItem(Constants.FARM_STONE_COST));
				cost.add(new WoodItem(Constants.FARM_WOOD_COST));
				return cost;

			default:
				return cost;
		}
	}
}
