package Model;

/**
 * Created by Oskar on 2016-04-01.
 */
public class Stockpile implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.STOCKPILE;

    private static final StructureBuildingMaterialTuple[] buildingMaterials = new StructureBuildingMaterialTuple[]
            {       new StructureBuildingMaterialTuple(IItem.Type.WOOD_ITEM, 5),
                    new StructureBuildingMaterialTuple(IItem.Type.STONE_ITEM, 20)
            };

    private Inventory inventory;


//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\
    public Stockpile(){
        inventory= new Inventory();
    }

    @Override
    public StructureBuildingMaterialTuple[] getBuildingMaterials() {
        return buildingMaterials;
    }

    //---------------------------------------------Getters & Setters------------------------------------------------------\\
    @Override
    public StructureType getStructureType() {
      return structureType;
    }
//---------------------------------------------INTERACTION METHODS----------------------------------------------------\\
   /* *//**
     * Adds the specified amount of the specified item to this characters inventory.
     * @param item The item to be added.
     *//*
    public void addToInventory(IItem item){
        inventory.addItem(item);
    }

    *//**
     * Removes the specified amount of the specified item from this characters inventory.
     * @param item The item to be removed.
     *//*
    public void removeFromInventory(IItem item){
        inventory.removeItem(item);
    }*/
}

