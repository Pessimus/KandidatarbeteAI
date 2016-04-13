package Model;

import java.beans.PropertyChangeEvent;

/**
 * Created by Martin on 2016-04-12.
 */
public class ConsumeTask implements ITask {

	private ICollidable consumable;
	private Character actor;
	private int waittime;
	private long starttime;
	private long endtime;
	private boolean remove;

	public ConsumeTask(ICollidable consumable, Character actor, int waittime){
		this.consumable = consumable;
		this.actor = actor;
		actor.addPropertyChangeListener(this);
		this.waittime = waittime;
		this.starttime = System.currentTimeMillis();
		this.endtime = this.starttime + this.waittime;
		this.remove = false;
	}

	@Override
	public void execute() {
		this.consumable.consumedCommand(this.actor);
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
		return remove || consumable.toBeRemoved();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.remove = true;
	}


}
