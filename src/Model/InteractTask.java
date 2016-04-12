package Model;

/**
 * Created by Martin on 2016-04-12.
 */
public class InteractTask implements ITask {

	private ICollidable interactable;
	private Character actor;
	private int waittime;

	public InteractTask(ICollidable interactable, Character actor, int waittime){
		this.interactable = interactable;
		this.actor = actor;
		this.waittime = waittime;
	}

	@Override
	public void execute() {
		this.interactable.interactedCommand(this.actor);
	}

	@Override
	public int getWaittime() {
		return waittime;
	}

}
