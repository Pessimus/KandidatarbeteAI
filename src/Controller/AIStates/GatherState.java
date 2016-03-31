package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.*;

import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class GatherState implements IState{

	private final ArtificialBrain brain;

	public GatherState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		List<IItem> inventory = brain.getBody().getInventory();
		IResource.ResourceType lowestResource = null;
		int lowestAmount = 0;
		for(IResource.ResourceType resource : IResource.ResourceType.values()) {
			//if(body.in)
		}
		switch (lowestResource){
			case CROPS:
			case MEAT:
			case FISH:
				brain.setState(brain.getGatherState());
				break;
		}
	}
}