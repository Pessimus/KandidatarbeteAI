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
		System.out.println("Adding task");
		tasks.add(task);
	}

	public static void executeTasks(){
		long time = (long)(System.currentTimeMillis()/1000+0.5)*1000;
		for(ITask task : tasks){
			System.out.println("Checking task: " + time +" / " +task.getEndtime());
			if(task.getEndtime() <= time){
				System.out.println("Calling to execute");
				tasks.remove(task);
				task.execute();
			}
		}
	}

}
