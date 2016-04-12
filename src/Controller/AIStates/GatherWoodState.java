package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.Constants;
import Model.ICollidable;
import Model.IResource;
import Model.ResourcePoint;
import Toolkit.RenderObject;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.lwjgl.Sys;
import java.util.List;

/**
 * Created by Victor on 2016-04-01.
 */
public class GatherWoodState implements IState {
    private final ArtificialBrain brain;

    private int waitUpdates = 0;
    private boolean waiting = false;
    private int bestIndex = -1;

    public GatherWoodState(ArtificialBrain b){
        brain = b;
    }

    @Override
    public void run() {
        if(waiting){
            if((waitUpdates = (++waitUpdates % Constants.GATHER_WOOD_STATE_TIME)) == 0) {
                brain.getBody().interactObject(bestIndex);

                waiting = false;
                bestIndex = -1;

                if (brain.getStateQueue().isEmpty()) {
                    brain.setState(brain.getIdleState());
                } else {
                    brain.setState(brain.getStateQueue().poll());
                }
            }
        } else {
            List<ICollidable> surround = brain.getBody().getInteractables();
            int i = 0;
            for (ICollidable temp : surround) {
                if(temp.getClass().equals(ResourcePoint.class)){
                    ResourcePoint tempPoint = (ResourcePoint) temp;
                    if(tempPoint.getResource().getResourceType().equals(IResource.ResourceType.WOOD)) {
                        bestIndex = i;
                        break;
                    }
                }

                i++;
            }

            if(bestIndex > 0){
                waiting = true;
            } else{
                if (brain.getStateQueue().isEmpty()) {
                    brain.setState(brain.getIdleState());
                } else {
                    brain.setState(brain.getStateQueue().poll());
                }
            }
        }
    }
}