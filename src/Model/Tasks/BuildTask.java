package Model.Tasks;

import Model.*;

import java.beans.PropertyChangeEvent;
import java.util.LinkedList;

/**
 * Created by Martin on 20/04/2016.
 */
public class BuildTask implements ITask {
	private Model.Character actor;
	LinkedList<IItem> cost;
	World rhs;
	private int waittime;
	private boolean remove;

	public BuildTask(Model.Character actor,LinkedList<IItem> cost, World rhs, int waittime){
		this.actor = actor;
		this.cost = cost;
		this.rhs = rhs;
		this.waittime = waittime;
		this.remove = false;
	}

	@Override
	public void execute() {
		actor.build(cost,rhs);
	}

	@Override
	public void interrupt() {
		this.actor.setWaiting(false);
		this.remove = true;
	}

	@Override
	public boolean toBeRemoved() {
		return remove;
	}

	@Override
	public boolean updateTick() {
		return this.waittime-- <=0;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.remove = true;
	}
}
