package Model.Tasks;

import Model.*;
import Model.Character;

import java.beans.PropertyChangeEvent;

/**
 * Created by Martin on 2016-04-12.
 */
public class InteractTask implements ITask {

	private ICollidable interactable;
	private Model.Character actor;
	private long waittime;
	private long starttime;
	private long endtime;
	private boolean remove;

	public InteractTask(ICollidable interactable, Character actor, long waittime){
		this.interactable = interactable;
		this.actor = actor;
		actor.addPropertyChangeListener(this);
		this.waittime = waittime;
		this.starttime = System.currentTimeMillis();
		this.endtime = this.starttime + this.waittime;
		this.remove = false;
	}

	@Override
	public void execute() {
		System.out.println("Executing in task");
		this.interactable.interactedCommand(this.actor);
	}

	@Override
	public long getWaittime() {
		return waittime;
	}

	@Override
	public long getEndtime() {
		return endtime;
	}

	@Override
	public boolean toBeRemoved() {
		return remove || interactable.toBeRemoved();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.remove = true;
	}

}