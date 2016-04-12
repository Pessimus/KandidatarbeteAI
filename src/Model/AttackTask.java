package Model;

/**
 * Created by Martin on 2016-04-12.
 */
public class AttackTask implements ITask {

	private ICollidable attackable;
	private Character actor;
	private int waittime;

	public AttackTask(ICollidable attackable, Character actor, int waittime){
		this.attackable = attackable;
		this.actor = actor;
		this.waittime = waittime;
	}

	@Override
	public void execute() {
		this.attackable.attackedCommand(this.actor);
	}

	@Override
	public int getWaittime() {
		return waittime;
	}

}
