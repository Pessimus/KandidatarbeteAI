package Controller;

import Model.Character;

/**
 * Created by Gustav on 2016-03-23.
 */
public abstract class AbstractBrain {

    private Character body; // The character this Brain controls

    public abstract void step(); // running this allows the brain to control its character

}
