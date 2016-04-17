package Controller.AIStates;

import Controller.ArtificialBrain;
import Utility.Constants;

import java.util.Random;

/**
 * Created by Tobias on 2016-04-17.
 */
public class BuildingState implements IState {

	private final ArtificialBrain brain;

	public BuildingState(ArtificialBrain b){
		brain = b;
	}

	@Override
	public void run() {
		brain.getBody().build(brain.getStructureStack().pop());
		/*int x = (int)brain.getBody().getX();
		int y = (int)brain.getBody().getY();

		boolean[][] mask = Constants.PATHFINDER_OBJECT.getMask();
		boolean canBuild = true;

		firstLoop:
		for (int i = x - (int) Constants.HOUSE_COLLISION_RADIUS; i <= x + Constants.HOUSE_COLLISION_RADIUS; i++) {
			for (int j = y - (int) Constants.HOUSE_COLLISION_RADIUS; j <= y + Constants.HOUSE_COLLISION_RADIUS; j++) {
				if (!mask[i/Constants.PATHFINDER_GRID_SIZE][j/Constants.PATHFINDER_GRID_SIZE]) {
					canBuild = false;
					break firstLoop;
				}
			}
		}

		if(canBuild){
			System.out.println("Building!");
			brain.getBody().build(brain.getStructureStack().pop());
		} else{
			brain.stackState(this);
			// TODO: Remove random point!!!
			Random r = new Random();
			brain.findPathTo(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
			brain.stackState(brain.getMovingState());
		}*/

		if(brain.getStateQueue().isEmpty()) {
			brain.setState(brain.getIdleState());
		}
		else{
			brain.setState(brain.getStateQueue().poll());
		}
	}
}
