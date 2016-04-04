package Controller.AIStates;

import Controller.AbstractBrain;
import Controller.ArtificialBrain;
import Model.ICharacterHandle;
import Toolkit.RenderObject;

/**
 * Created by Tobias on 2016-03-29.
 */
public class BuildState implements IState{
	private final ArtificialBrain brain;
	RenderObject closestWood = null;
	double cdx = 0;
	double cdy = 0;
	double odx= 0;
	double ody = 0;
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

		//FIND MISSING MATERIALS AND GO GATHER
			//FIND WOOD
			for(RenderObject o : brain.map.getRenderObjects()) {
				if((o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.WOOD)) ||(o.getRenderType().equals(RenderObject.RENDER_OBJECT_ENUM.WOOD ))) {
					if (closestWood == null) {
						closestWood = o;
					} else {
						cdx = Math.abs(brain.getBody().getX() - closestWood.getX());
						cdy = Math.abs(brain.getBody().getY() - closestWood.getY());
						odx = Math.abs(brain.getBody().getX() - o.getX());
						ody = Math.abs(brain.getBody().getY() - o.getY());
						if (Math.sqrt(cdx) + Math.sqrt(cdy) > Math.sqrt(odx) + Math.sqrt(ody))
							closestWood = o;
					}
				}

			}
			brain.findPathTo(closestWood.getX(), closestWood.getY());
			brain.queueState(brain.getMovingState());
			brain.queueState(brain.getGatherWoodState());
			brain.setState(brain.getStateQueue().poll());

		}
}
