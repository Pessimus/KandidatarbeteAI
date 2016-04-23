package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Model.ICollidable;
import Model.Character;
import Model.Interaction;
import Utility.Constants;

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
		if(brain.getBody().getInteractionType() == null) {
			List<ICollidable> surround = brain.getBody().getSurroundings();
			int i = 0;
			boolean isSomebodyAround = false;
			for (ICollidable o : surround) {
				if (o.getClass().equals(Character.class)) {
					Character c = (Character) o;
					// TODO: Find what interaction should be done
					if(Math.abs(brain.getBody().getX() - c.getX()) < Constants.CHARACTER_INTERACTION_RADIUS
							&& Math.abs(brain.getBody().getY() - c.getY()) < Constants.CHARACTER_INTERACTION_RADIUS){
						brain.getBody().setInteractionType(Interaction.InteractionType.SOCIAL);
						brain.getBody().interactObject(i);
						brain.setState(brain.getIdleState());
					} else{
						brain.setObjectToFollow(c);
						brain.setState(brain.getFollowState());
					}
					isSomebodyAround = true;
					break;
				}
				i++;
			}
			if(!isSomebodyAround) {
				brain.setState(brain.getFindCharacterState());
			}
		} else{
			switch (brain.getBody().getInteractionType()){
				case SOCIAL:
					if(brain.getCurrentInteraction() != null) {
						brain.getCurrentInteraction().acceptInteraction(brain.getBody().hashCode(), brain);
						brain.setState(brain.getConverseState());
					}
					break;
				case TRADE:
					brain.setState(brain.getIdleState());
					break;
				case HOSTILE:
					brain.setState(brain.getIdleState());
					break;
				default:
					brain.setState(brain.getIdleState());
					break;
			}
		}
	}
}