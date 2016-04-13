package Model;

/**
 * Created by Martin on 2016-04-12.
 */
public class AttackTask implements ITask {

	private ICollidable attackable;
	private Character actor;
	private int waittime;
	private long starttime;
	private long endtime;

	public AttackTask(ICollidable attackable, Character actor, int waittime){
		this.attackable = attackable;
		this.actor = actor;
		this.waittime = waittime;
		this.starttime = System.currentTimeMillis();
		this.endtime = this.starttime + this.waittime;
	}

	@Override
	public void execute() {
		this.attackable.attackedCommand(this.actor);
	}

	@Override
	public long getWaittime() {
		return waittime;
	}

	@Override
	public long getEndtime() {
		return endtime;
	}

}
