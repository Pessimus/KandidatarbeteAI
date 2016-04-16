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
		if (best == null) {
			brain.setNextResourceToGather(IResource.ResourceType.WATER);
			brain.stackState(brain.getDrinkState());
			brain.stackState(brain.getGatherState());
			brain.setState(brain.getStateQueue().poll());
			/*
			// TODO: Pathfinding to nearest/best food-resource
			// TODO: Queue MovingState correctly
			for (RenderObject o : brain.map.getRenderObjects()) {
				if (o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.LAKE)) {
					if (closestLake == null) {
						closestLake = o;
					} else {
						cdx = Math.abs(brain.getBody().getX() - closestLake.getX());
						cdy = Math.abs(brain.getBody().getY() - closestLake.getY());
						odx = Math.abs(brain.getBody().getX() - o.getX());
						ody = Math.abs(brain.getBody().getY() - o.getY());
						if (Math.sqrt(cdx) + Math.sqrt(cdy) > Math.sqrt(odx) + Math.sqrt(ody))
							closestLake = o;
					}
				}
			}
			brain.findPathTo(closestLake.getX(), closestLake.getY());
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getGatherWaterState());
			brain.queueState(brain.getDrinkState());
			brain.setState(brain.getStateQueue().poll());
			*/

		}
		else{
			brain.stackState(brain.getDrinkState());
			brain.setState(brain.getStateQueue().poll());
		}
	}
}