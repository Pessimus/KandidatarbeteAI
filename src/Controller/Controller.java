package Controller;

import Model.World;
import View.View;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Controller implements PropertyChangeListener {
	private World gameModel;
	private View gameView;

	private final Queue<Integer> keyboardInputQueue;
	private final Queue<Integer> mouseInputQueue;

	private final Semaphore keyboardSema = new Semaphore(1);
	private final Semaphore mouseSema = new Semaphore(1);

	public static void main(String[] args){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		World model = new World(d.getWidth(), d.getHeight());
		View view = new View();
		new Controller(view, model);
	}

	public Controller(View view, World model){
		keyboardInputQueue = new LinkedList<>();
		mouseInputQueue = new LinkedList<>();
		setView(view);
		setModel(model);
	}

	public boolean setView(View view){
		if(view != null){
			gameView = view;
			//gameView.addPropertyListener(this); // TODO: 'View' should use PropertyChangeSupport
			return true;
		}

		return false;
	}

	public boolean setModel(World model){
		if(model != null){
			gameModel = model;
			//gameModel.addPropertyListener(this); // TODO: 'Model' should use PropertyChangeSupport
			return true;
		}

		return false;
	}

	private void updateView(){

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt != null){
			if(!evt.getPropertyName().equals(null)){
				if(evt.getSource().equals(gameView)){
					// If the source of the event is the 'View', handle that separately.
					if(evt.getPropertyName().equals("keyboard")) {
						// If the 'View' is sending 'Keyboard'-inputs, put them in the correct queue.
						try {
							keyboardSema.acquire();
							keyboardInputQueue.offer((Integer) evt.getNewValue());
							keyboardSema.release();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else if(evt.getPropertyName().equals("mouse")){
						// If the View is sending 'Mouse'-inputs, put them in the correct queue.
						try{
							mouseSema.acquire();
							mouseInputQueue.offer((Integer) evt.getNewValue());
							mouseSema.release();
						} catch (InterruptedException e){
							e.printStackTrace();
						}
					}
				}

				if(evt.getSource().equals(gameModel)){
					// If the source of the event is the 'Model', handle that separately.
					if(evt.getPropertyName().equals("input")) {
						// If the 'Model' is asking for inputs, provide that.
						try {
							keyboardSema.acquire();
							Integer[] keyInts = (Integer[])keyboardInputQueue.toArray();
							keyboardSema.release();

							mouseSema.acquire();
							Integer[] mouseInts = (Integer[])mouseInputQueue.toArray();
							mouseSema.release();

							//gameModel.provideInputs(keyInts, mouseInts); TODO: Implement a method in World that receives lists of integers.
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
