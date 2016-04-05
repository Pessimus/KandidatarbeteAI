package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oskar on 2016-04-01.
 */
public class House implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.HOME;

    private static final StructureBuildingMaterialTuple[] buildingMaterials = new StructureBuildingMaterialTuple[]
            {       new StructureBuildingMaterialTuple(IResource.ResourceType.WOOD, 10),
                    new StructureBuildingMaterialTuple(IResource.ResourceType.STONE, 5)
            };

    private int capacity;
    private int occupants;
    private boolean isFull = false;

//-----------------------------------------------CONSTRUCTOR----------------------------------------------------------\\
    /**
     * A class representing the structure "House".
     * @param capacity the max capacity of occupants in the house.
     */
    public House(int capacity){this.capacity=capacity; }

    @Override
    public StructureBuildingMaterialTuple[] getBuildingMaterials() {
        return buildingMaterials;
    }

    @Override
    public StructureType getStructureType() {
        return structureType;
    }
//---------------------------------------Getters & Setters------------------------------------------------------------\\
    /** Returns the max capacity of the house. */
    public int getCapacity(){
        return capacity;
    }
    /** Return boolean that states if the house is full or not.*/
    public boolean getIsFull(){
        return isFull;
    }
//----------------------------------------ADD & REMOVE OCCUPANTS------------------------------------------------------\\
    /** Method to call when adding a character to this house. */
    public void addOccupant(){
        occupants++;
        if(occupants>=capacity)
        {
            isFull=true;
        }
    }
    /** Method to call when removing a character from this house. */
    public void removeOccupant(){
        occupants--;
        isFull=false;
    }
}

