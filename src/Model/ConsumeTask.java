package Model;

/**
 * Created by Martin on 2016-04-12.
 */
public class ConsumeTask implements ITask {

	private ICollidable consumable;
	private Character actor;
	private int waittime;

	public ConsumeTask(ICollidable consumable, Character actor, int waittime){
		this.consumable = consumable;
		this.actor = actor;
		this.waittime = waittime;
	}

	@Override
	public void execute() {
		this.consumable.consumedCommand(this.actor);
	}

	@Override
	public int getWaittime() {
		return waittime;
	}

}
