package Model.Tasks;

import Model.ITask;

import java.beans.PropertyChangeEvent;

/**
 * Created by Martin on 20/04/2016.
 */
public class ReproduceTask implements ITask{

	private Model.Character actor;
	private int waittime;

	public ReproduceTask(Model.Character actor, int waittime){
		this.actor = actor;
		this.waittime = waittime;
	}

	@Override
	public void execute() {
		this.actor.reproduceCommand();
		this.actor.setWaiting(false);
		actor.removePropertyChangeListener(this);
	}

	@Override
	public void interrupt() {
		this.actor.setWaiting(false);
		actor.removePropertyChangeListener(this);
	}

	@Override
	public boolean toBeRemoved() {
		return actor.toBeRemoved();
	}

	@Override
	public boolean updateTick() {
		return this.waittime-- <=0;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
}
