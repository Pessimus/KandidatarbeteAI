package Model;

import java.util.List;

/**
 * Created by Oskar on 2016-04-01.
 */
public interface IStructure extends ICollidable{

    enum StructureType{
        HOUSE,
        STOCKPILE,
        FARM
    }

    // TODO: Remove maybe?
    /*
    class StructureBuildingMaterialTuple{
        private final IItem.Type resourceType;
        private final int resourceAmount;

        StructureBuildingMaterialTuple(IItem.Type type, int amount){
            resourceType = type;
            resourceAmount = amount;
        }

        public IItem.Type getResourceType() {
            return resourceType;
        }

        public int getResourceAmount() {
            return resourceAmount;
        }
    }

    StructureBuildingMaterialTuple[] getBuildingMaterials();

    StructureType getStructureType();
*/

}
