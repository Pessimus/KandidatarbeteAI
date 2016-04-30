package Model;

import java.util.LinkedList;

/**
 * Created by Martin on 2016-04-12.
 */
public class Schedule {

	private static LinkedList<ITask> tasks;


	public static void init(){
		tasks = new LinkedList<>();
	}

	public static void addTask(ITask task){
		tasks.add(task);
	}

	public static void executeTasks(){
		//long time = (long)(System.currentTimeMillis()/1000+0.5)*1000;
		LinkedList<ITask> rm = new LinkedList<>();
		LinkedList<ITask> copy = (LinkedList<ITask>)tasks.clone();
		for(ITask task : copy){
			if(task.toBeRemoved()){
				task.interrupt();
				rm.add(task);
			//}else if (task.getEndtime() <= time) {
			}else if (task.updateTick()) {
				//tasks.remove(task);
				rm.add(task);
				task.execute();
			}
		}
		for(ITask t : rm){
			tasks.remove(t);
		}
	}

}
