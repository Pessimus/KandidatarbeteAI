package Model;

/**
 * Created by Oskar on 2016-04-01.
 */
public interface IStructure {

    enum StructureType{
        HOME,
        STOCKPILE
    }

    StructureType getStructureType();


}
