package Model;

import java.beans.PropertyChangeListener;

/**
 * Created by Martin on 2016-04-12.
 */
public interface ITask extends PropertyChangeListener{
	void execute();
	long getWaittime();
	long getEndtime();
	boolean toBeRemoved();
}
