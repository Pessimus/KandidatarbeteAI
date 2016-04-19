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
	public static IStructure createStructure(StructureType type, float x, float y){
		/*switch (type){
			case STOCKPILE:
				return new Stockpile(x, (float)(y-Constants.STOCKPILE_COLLISION_RADIUS));

			case HOUSE:
				return new House(x, (float)(y-Constants.HOUSE_COLLISION_RADIUS));

			case FARM:
				return new Farm(x, (float)(y-Constants.FARM_COLLISION_RADIUS));

			default:
				return null;
		}*/
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

	public static LinkedList<IItem> getCost(StructureType typeToSpawn) {
		LinkedList<IItem> cost = new LinkedList<>();
		switch (typeToSpawn){
			case STOCKPILE:
				cost.add(new StoneItem(1));
				cost.add(new WoodItem(2));
				return cost;

			case HOUSE:
				cost.add(new StoneItem(2));
				cost.add(new WoodItem(4));
				return cost;

			case FARM:
				cost.add(new StoneItem(20));
				cost.add(new WoodItem(8));
				return cost;

			default:
				return cost;
		}
	}
}
