package Controller.AIStates;

import Controller.ArtificialBrain;
import Model.ICollidable;
import Model.IItem;
import Model.IStructure;
import Model.Structures.Stockpile;
import Utility.RenderObject;

import java.util.Iterator;

/**
 * Created by Tobias on 2016-05-06.
 */
public class DumpToStockpileState implements IState {
	private final ArtificialBrain brain;

	public DumpToStockpileState(ArtificialBrain brain) {
		this.brain = brain;
	}

	@Override
	public void run() {
		if (brain.getCurrentStockpileInteraction() == null) {
			int i = -1;
			Iterator<ICollidable> iterator = brain.getBody().getInteractables().iterator();
			boolean found = false;

			while (iterator.hasNext()) {
				i++;
				ICollidable temp = iterator.next();

				if (temp.getClass().equals(Stockpile.class)) {
					found = true;
					break;
				}
			}

			if (found) {
				brain.getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.STORING);
				brain.getBody().interactObject(i);
			} else {
				Stockpile s = (Stockpile) brain.getStructureMemory().stream()
						.filter(o -> o.getStructureType().equals(IStructure.StructureType.STOCKPILE))
						.findFirst()
						.orElseGet(() -> null);

				if (s != null) {
					brain.stackState(this);
					brain.stackResource(s);
					brain.setState(brain.getMovingState());
				} else {
					brain.setState(brain.getIdleState());
				}
			}
		} else {
			IItem item = brain.getBody().getInventory().stream()
					.filter(o -> o.getType().equals(IItem.Type.GOLD_ITEM) || o.getType().equals(IItem.Type.WOOD_ITEM) || o.getType().equals(IItem.Type.STONE_ITEM))
					.reduce((i1, i2) -> i1.getAmount() > i2.getAmount() ? i1 : i2)
					.orElseGet(() -> null);

			if (item != null) {
				brain.getCurrentStockpileInteraction().addToStockpile(item);
			}

			brain.getCurrentStockpileInteraction().endInteraction();
			brain.setState(brain.getIdleState());
		}
	}
}
