package Model;

/**
 * Created by Oskar on 2016-04-01.
 */
public class House implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.HOME;

    private int capacity;
    private int occupants;

    //-----------------------------------------------CONSTRUCTOR------------------------------------------------------\\
    public House(int capacity){this.capacity=capacity; }

    @Override
    public StructureType getStructureType() {
        return structureType;
    }

    public int getCapacity(){
        return capacity;
    }
}

