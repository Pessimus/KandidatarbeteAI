package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import java.util.LinkedList;
import Utility.Constants;

/**
 * Created by victor on 2016-04-20.
 */
public class FollowState implements IState {
    private final ArtificialBrain brain;

    public FollowState(ArtificialBrain b){
        brain = b;
    }
    private LinkedList<PathStep> currentPath = null;

    @Override
    public void run() {
        if(brain.getObjectToFollow() != null) {
            if (currentPath != null) {
                if (!currentPath.isEmpty()) {
                    currentPath.getFirst().stepTowards(brain.getBody());
                    if (currentPath.getFirst().reached(brain.getBody())) {
                        currentPath.removeFirst();
                    }
                } else {
                    brain.findPathTo(brain.getObjectToFollow());
                    currentPath = brain.getNextPath();
                    brain.getPathStack().removeFirst();
                }
            } else {
                brain.findPathTo(brain.getObjectToFollow());
                currentPath = brain.getNextPath();
                brain.getPathStack().removeFirst();
            }

            if(Math.abs(brain.getBody().getX() - brain.getObjectToFollow().getX()) < Constants.CHARACTER_INTERACTION_RADIUS + brain.getObjectToFollow().getCollisionRadius()
                    && Math.abs(brain.getBody().getY() - brain.getObjectToFollow().getY()) < Constants.CHARACTER_INTERACTION_RADIUS + brain.getObjectToFollow().getCollisionRadius()) {
                brain.setObjectToFollow(null);
                currentPath = null;
                brain.setState(brain.getIdleState());
            }
        } else{
            currentPath = null;
			brain.setState(brain.getIdleState());
        }
    }

    public void clearPath () {
        currentPath = null;
    }


}
