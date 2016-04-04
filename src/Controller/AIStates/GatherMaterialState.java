package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.ICharacterHandle;
import Model.IItem;
import Model.IResource;
import sun.awt.image.ImageWatched;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Tobias on 2016-03-29.
 */
public class GatherMaterialState implements IState{
	private final ArtificialBrain brain;

	public GatherMaterialState(ArtificialBrain brain){
		this.brain = brain;
	}

	@Override
	public void run() {
		IResource.ResourceType lowestType = null;
		int lowestAmount = 0;
		for(IResource.ResourceType type : IResource.ResourceType.values()){
			Iterator<IItem> items = brain.getBody().getInventory().iterator();

			while(items.hasNext()){
				IItem current = items.next();
				if(current.getAmount() < lowestAmount && lowestType != type){
					lowestType = type;
					lowestAmount = current.getAmount();
				}
			}
		}

		if(lowestType != null){
			// TODO: Pathfind to nearest source of this type
		}
		else{

		}
	}
}
