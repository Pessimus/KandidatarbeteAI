package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICollidable;
import Model.ResourcePoint;
import Utility.Constants;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by victo on 2016-04-20.
 */
public class ExploreState implements IState {

    private final ArtificialBrain brain;

    public ExploreState(ArtificialBrain b){
        brain = b;
    }
    private LinkedList<PathStep> currentPath = null;
    @Override
    public void run() {
        boolean foundObject = false;

        for(ICollidable o : brain.getBody().getSurroundings()){
            if(o.getClass().equals(brain.getObjectToFind())){
                foundObject = true;
                brain.setObjectToFind(null);
                break;
            }
        }

        if(!foundObject) {
            //Have a resource to look for
            if (currentPath != null) {
                if (!currentPath.isEmpty()) {
                    currentPath.getFirst().stepTowards(brain.getBody());
                    if (currentPath.getFirst().reached(brain.getBody())) {
                        currentPath.removeFirst();
                    }
                } else{
                    brain.findPathTo(brain.getBody().getX() + ((1 - (Math.random() * 2)) * 120), brain.getBody().getY() + ((1 - (Math.random() * 2)) * 120));
                    currentPath = brain.getNextPath();
                }
            } else{
                brain.findPathTo(brain.getBody().getX() + ((1 - (Math.random() * 2)) * 120), brain.getBody().getY() + ((1 - (Math.random() * 2)) * 120));
                currentPath = brain.getNextPath();
            }
            //If we se it, enter that state
            //Randomize a point nearby to move towards
            brain.stackState(this);
        }

        brain.setState(brain.getIdleState());
    }
}
