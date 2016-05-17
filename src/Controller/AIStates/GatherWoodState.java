package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.*;
import Utility.RenderObject;
import java.util.Iterator;

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

	int i = 0;

    @Override
    public void run() {
		int woodAmount = brain.getBody().getInventory()
				.stream()
				.filter(o -> o.getType().equals(IItem.Type.WOOD_ITEM))
				.mapToInt(IItem::getAmount)
				.sum();
		if(!(World.nbrTrees < 20)) {
			if (woodAmount < brain.getNextResourceToGather().resourceAmount) {
				Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
				int i = 0;
				boolean found = false;
				while (iterator.hasNext()) {
					ICollidable next = iterator.next();
					if (next.getClass().equals(ResourcePoint.class)) {
						ResourcePoint tempPoint = (ResourcePoint) next;
						if (tempPoint.getResource().getResourceType().equals(IResource.ResourceType.WOOD)) {
							brain.getBody().interactObject(i);
							brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.FORESTING);
							found = true;
							break;
						}
					}

					i++;
				}

				if (!found) {
					brain.setState(brain.getGatherState());
				}
			} else {
				brain.getGatherStack().remove();
				brain.setState(brain.getIdleState());
			}
		} else {
			brain.getGatherStack().remove();
			brain.setState(brain.getIdleState());
		}
    }
}
