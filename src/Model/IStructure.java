package Model;

/**
 * Created by Oskar on 2016-04-01.
 */
public interface IStructure extends ICollidable{

    enum StructureType{
        HOUSE,
        STOCKPILE,
        FARM
    }

    StructureType getStructureType();


}
