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

			//CHECK OUR SURROUNDINGS
			for (ICollidable o : surround) {
				//IS IT A CHARACTER?
				if (o.getClass().equals(Character.class)) {
					Character c = (Character) o;
					//IS THAT CHARACTER INTERACTING IR IS IT BLACKLISTED?
					if(!brain.getBlackList().contains(c)) {
						// TODO: Find what interaction should be done
						//NO, CHECK IF WE ARE WE CLOSE ENOUGH TO INTERACT
						if (Math.abs(brain.getBody().getX() - c.getX()) < Constants.CHARACTER_INTERACTION_RADIUS
								&& Math.abs(brain.getBody().getY() - c.getY()) < Constants.CHARACTER_INTERACTION_RADIUS) {
							//YES? INTERACT WITH CHARACTER
							//brain.setState(brain.getIdleState());
							brain.getBody().interactObject(brain.getBody().getInteractables().indexOf(c));
							brain.getBody().setInteractionType(Interaction.InteractionType.SOCIAL);
						} else {
							//NO? FOlLOW THE CHARACTER
							brain.setObjectToFollow(c);
							brain.setState(brain.getFollowState());
						}

						isSomebodyAround = true;
						break;
					} else {
						//YES
						//IS OUR BLACKLIST CONTAINING A LOT OF CHARACTERS?
						if(brain.getBlackList().size() > 5) {
							//YES? CLEAR SO WE CAN TRY AGAIN
							brain.getBlackList().clear();
						}
						//NO? ADD CHARACTER TO BLACKLIST IF THEY ARE NOT ALREADY IN IT
						if(!brain.getBlackList().contains(c)) {
						//	brain.getBlackList().add(c);
						}
					}
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
					} else {
						brain.setState(brain.getIdleState());
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