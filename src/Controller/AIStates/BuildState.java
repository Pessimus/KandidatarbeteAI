package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;

/**
 * Created by Tobias on 2016-03-29.
 */
public class BuildState implements IState{
	private final ArtificialBrain brain;

	public BuildState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		/*
		if(!brain.getBody().hasHome()){
			if(!brain.getBody().hasMaterialFor(Structure.HOUSE)){
				for(IResource.ResourceType type :
			}
		}
		*/
	}
}
