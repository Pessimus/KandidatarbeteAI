package Model.Tasks;

import Model.*;
import Model.Character;
import java.beans.PropertyChangeEvent;

/**
 * Created by Martin on 2016-04-12.
 */
public class AttackTask implements ITask {

	private ICollidable attackable;
	private Model.Character actor;
	private int waittime;
	//private long starttime;
	//private long endtime;
	private boolean remove;

	public AttackTask(ICollidable attackable, Character actor, int waittime){
		this.attackable = attackable;
		this.actor = actor;
		actor.addPropertyChangeListener(this);
		this.waittime = waittime;
		//this.starttime = System.currentTimeMillis();
		//this.endtime = this.starttime + this.waittime;
		this.remove = false;
		this.actor.setWaiting(true);
	}

	@Override
	public void execute() {
		this.attackable.attackedCommand(this.actor);
		this.actor.setWaiting(false);
		actor.removePropertyChangeListener(this);
	}

	@Override
	public void interrupt() {
		this.actor.setWaiting(false);
		this.remove = true;
		this.attackable.attackedInterrupted(this.actor);
		actor.removePropertyChangeListener(this);
	}

	/*@Override
	public long getWaittime() {
		return waittime;
	}

	@Override
	public long getEndtime() {
		return endtime;
	}*/

	@Override
	public boolean toBeRemoved() {
		return remove || attackable.toBeRemoved();
	}

	/**
	 * Updates the waittime of this task.
	 * @return true if the remaining waittime is less then or equal to 0.
	 */
	@Override
	public boolean updateTick() {
		return this.waittime-- <=0;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.remove = true;
	}
}
