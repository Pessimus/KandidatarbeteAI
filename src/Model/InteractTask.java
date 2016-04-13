package Model;

/**
 * Created by Martin on 2016-04-12.
 */
public class InteractTask implements ITask {

	private ICollidable interactable;
	private Character actor;
	private long waittime;
	private long starttime;
	private long endtime;

	public InteractTask(ICollidable interactable, Character actor, long waittime){
		this.interactable = interactable;
		this.actor = actor;
		this.waittime = waittime;
		this.starttime = System.currentTimeMillis();
		this.endtime = this.starttime + this.waittime;
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

}
