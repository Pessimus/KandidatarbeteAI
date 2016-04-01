package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.Constants;
import Model.ICollidable;
import Toolkit.RenderObject;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.lwjgl.Sys;

import java.util.LinkedList;
import java.util.List;
/**
 * Created by Oskar on 2016-04-01.
 */
public class GatherMeatState implements IState {
    private final ArtificialBrain brain;

    private int waitUpdates = 0;

    public GatherMeatState(ArtificialBrain brain) {
        this.brain = brain;
    }

    @Override
    public void run() {
        List<ICollidable> surrond = brain.getBody().getInteractables();

        for(ICollidable temp : surrond){
            // TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!
            //if(temp.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.ANIMAL)){
            // TODO: Don't use RENDER_OBJECT_ENUM, use Outcome in some way!!

            if((waitUpdates = (++waitUpdates % Constants.GATHER_MEAT_STATE_TIME)) == 0){
                temp.interacted((Model.Character) brain.getBody());
                if(brain.getStateQueue().isEmpty()){
                    brain.setState((brain.getIdleState()));
                }
                else{
                    brain.setState(brain.getStateQueue().poll());
                }

            }
        }
    }
}
