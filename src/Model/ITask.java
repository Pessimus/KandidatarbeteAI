package Model;

import java.beans.PropertyChangeListener;

/**
 * Created by Martin on 2016-04-12.
 */
public interface ITask extends PropertyChangeListener{
	void execute();
	void interrupt();
	boolean toBeRemoved();

	//Using update tick
	boolean updateTick();

	//Using time
	//long getWaittime();
	//long getEndtime();


}
