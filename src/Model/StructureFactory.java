package Model;

import Model.IStructure.StructureType;

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
}
