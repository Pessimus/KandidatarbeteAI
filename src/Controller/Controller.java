package Controller;

import Model.World;
import View.View;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Controller implements PropertyChangeListener {
	private World gameModel;
	private View gameView;

	private final Queue<Integer[]> keyboardInputQueue;
	private final Queue<Integer[]> mouseInputQueue;

	private final Semaphore keyboardSema = new Semaphore(1);
	private final Semaphore mouseSema = new Semaphore(1);

	// TODO: Remove this enum!
	public enum objects{
		CHARACTER,STRUCTURE,TERRAIN;
	}

	public static void main(String[] args){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		World model = new World(d.getWidth(), d.getHeight());
		View view = new View("Kandidat");
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
			//gameView.addPropertyChangeListener(this); // TODO: 'View' should use PropertyChangeSupport
			return true;
		}

		return false;
	}

	public boolean setModel(World model){
		if(model != null){
			gameModel = model;
			//gameModel.addPropertyChangeListener(this); // TODO: 'Model' should use PropertyChangeSupport
			return true;
		}

		return false;
	}

	private void updateView(){
		//List<Enum> objectList = gameModel.getViewableObjects();
		// getViewableObjects() is expected to return a 'List' of 3 arrays:
		// An array with all enum values for the objects;
		// An array with the x-coordinates for these objects
		// An array with the y-coordinates for these objects
		//gameView.drawObjects(objectList);
	}

	private void updateModel(){

	}

	private void handleViewEvent(PropertyChangeEvent evt){
		if(evt.getPropertyName().equals("keyboard")) {
			// If the 'View' is sending 'Keyboard'-inputs, put them in the correct queue.
			try {
				Integer[] newValue = (Integer[]) evt.getNewValue();
				keyboardSema.acquire();
				keyboardInputQueue.offer(newValue);
				keyboardSema.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if(evt.getPropertyName().equals("mouse")){
			// If the View is sending 'Mouse'-inputs, put them in the correct queue.
			try{
				Integer[] newValue = (Integer[]) evt.getNewValue();
				mouseSema.acquire();
				mouseInputQueue.offer(newValue);
				mouseSema.release();
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	private void handleModelEvent(PropertyChangeEvent evt){
		if(evt.getPropertyName().equals("getInput")) {
			// If the 'Model' is asking for inputs, provide that.
			try {
				keyboardSema.acquire();
				Object[] tempList = keyboardInputQueue.toArray();
				keyboardSema.release();

				Integer[] keyInts = (Integer[])tempList;

				mouseSema.acquire();
				tempList = mouseInputQueue.toArray();
				mouseSema.release();
				Integer[] mouseInts = (Integer[])tempList;

				//gameModel.provideInputs(keyInts, mouseInts); TODO: Implement a method in World that receives lists of integers.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if(evt.getPropertyName().equals("update")){
			// Get coordinates of all objects in the viewable area!
			updateView();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt != null){
			if(!evt.getPropertyName().equals(null)){
				if(evt.getSource().equals(gameView)){
					// If the source of the event is the 'View', handle that separately.
					handleViewEvent(evt);
				}

				if(evt.getSource().equals(gameModel)){
					// If the source of the event is the 'Model', handle that separately.
					handleModelEvent(evt);
				}
			}
		}
	}
}
