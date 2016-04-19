package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Model.ICollidable;
import Model.Character;
import Model.Interaction;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class SocializeState implements IState{
	private final ArtificialBrain brain;

	public SocializeState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		List<ICollidable> surround = brain.getBody().getSurroundings();
		int i = 0;
		for(ICollidable o : surround){
			if(o.getClass().equals(Character.class)){
				Character c = (Character) o;
				// TODO: Find what interaction should be done
				brain.getBody().setInteractionType(Interaction.InteractionType.SOCIAL);
				brain.getBody().interactObject(i);
			}
		}



		/* *Request interaction
			*Determine type of interaction
			* Enter correct interaction state
			* Go back to idleState
		 */
		brain.setState(brain.getIdleState());
	}
}