package Model;

import java.util.List;

/**
 * Created by Oskar on 2016-04-01.
 */
public interface IStructure {

    enum StructureType{
        HOME,
        STOCKPILE
    }

    class StructureBuildingMaterialTuple{
        private final IResource.ResourceType resourceType;
        private final int resourceAmount;

        StructureBuildingMaterialTuple(IResource.ResourceType type, int amount){
            resourceType = type;
            resourceAmount = amount;
        }

        public IResource.ResourceType getResourceType() {
            return resourceType;
        }

        public int getResourceAmount() {
            return resourceAmount;
        }
    }

    StructureBuildingMaterialTuple[] getBuildingMaterials();

    StructureType getStructureType();


}
