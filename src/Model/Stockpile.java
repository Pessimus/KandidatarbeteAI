package Model;

/**
 * Created by Oskar on 2016-04-01.
 */
public class Stockpile implements IStructure {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\
    public static final StructureType structureType = StructureType.STOCKPILE;

    @Override
    public StructureType getStructureType() {
        return structureType;
    }
}
