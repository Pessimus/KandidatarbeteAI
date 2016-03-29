package Controller.AIStates;

import Model.*;

import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class GatherState implements IState{
	private ICharacterHandle body;

	public GatherState(ICharacterHandle character){
		body = character;
	}

	@Override
	public void setBody(ICharacterHandle character) {

	}

	@Override
	public boolean run() {
		List<IItem> inventory = body.getInventory();
		IResource.ResourceType lowestResource = null;
		int lowestAmount = 0;
		for(IResource.ResourceType resource : IResource.ResourceType.values()) {
			//if(body.in)
		}
		switch (lowestResource){
			case CROPS:
			case MEAT:
			case FISH:
				set
				break;
		}
	}
}