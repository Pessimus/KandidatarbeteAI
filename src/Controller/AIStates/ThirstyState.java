package Controller.AIStates;

import Controller.ArtificialBrain;
import Controller.PathStep;
import Model.*;
import Utility.RenderObject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Tobias on 2016-03-29.
 */
public class ThirstyState implements IState {
	private final ArtificialBrain brain;

	private List<PathStep> pathToResource;

	public ThirstyState(ArtificialBrain brain) {
		this.brain = brain;
	}

	RenderObject closestLake = null;
	double cdx = 0;
	double cdy = 0;
	double odx = 0;
	double ody = 0;

	@Override
	public void run() {
		if(brain.getBody().getNeeds()[1] < 95) {
			brain.stackState(brain.getThirstyState());
			Iterator<IItem> iterator = brain.getBody().getInventory().iterator();
			IItem best = null;
			int thirstAmount = -1;

			loop:
			while (iterator.hasNext()) {
				IItem current = iterator.next();
				switch (current.getType()) {
					case WATER_ITEM:
						best = current;
						break loop;
				}
			}

			brain.stackState(brain.getDrinkState());

			if (best == null) {
				brain.stackResourceToGather(IResource.ResourceType.WATER);
				brain.stackState(brain.getGatherState());

			}
			brain.setState(brain.getStateQueue().poll());
		} else {
			brain.setState(brain.getIdleState());
		}
	}
}