package Controller;

import Model.RenderObject;
import Model.World;
import View.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

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
	private StateViewInit gameView;

	private final Queue<Integer[]> keyboardInputQueue;
	private final Queue<Integer[]> mouseInputQueue;

	private final Semaphore keyboardSema = new Semaphore(1);
	private final Semaphore mouseSema = new Semaphore(1);

	public static final int KEYBOARD_PRESSED_INTEGER = 0;
	public static final int KEYBOARD_RELEASED_INTEGER = 1;

	public static final int 		TARGET_FRAMERATE = 60;

	public static final boolean 	RUN_IN_FULLSCREEN = false;


	public static void main(String[] args){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		World model = new World(d.getWidth(), d.getHeight());
		StateViewInit view = new StateViewInit("HC", RUN_IN_FULLSCREEN, false, TARGET_FRAMERATE, (int)d.getWidth(), (int)d.getHeight());

		new Controller(view, model).run();
	}

	public Controller(StateViewInit view, World model){
		keyboardInputQueue = new LinkedList<>();
		mouseInputQueue = new LinkedList<>();
		setView(view);
		setModel(model);

		view.start();
	}

	private void run(){
		while(true){
			try {
				keyboardSema.acquire();
				Object[] tempList = keyboardInputQueue.toArray();
				keyboardSema.release();

				Integer[][] keyInts = (Integer[][])tempList;
				sendKeyboardInputToModel(keyInts);

				mouseSema.acquire();
				tempList = mouseInputQueue.toArray();
				mouseSema.release();

				Integer[][] mouseClicks = (Integer[][])tempList;
				sendMouseInputToModel(mouseClicks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean setView(StateViewInit view){
		if(view != null){
			gameView = view;
			gameView.addPropertyChangeListener(this); // TODO: 'View' should use PropertyChangeSupport
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
		List<RenderObject> objectList = gameModel.getRenderObjects();
		// getViewableObjects() is expected to return a 'List' of 3 arrays:
		// An array with all enum values for the objects;
		// An array with the x-coordinates for these objects
		// An array with the y-coordinates for these objects
		//gameView.drawObjects(objectList);
	}

	private synchronized void sendKeyboardInputToModel(Integer[][] keyboardClicks) {
		// Keyboard input
		if (keyboardClicks.length > 0) {
			new Thread() {
				@Override
				public void run() {
					// The array for key-clicks work like this:
					// keyboardClicks[0][0] = If the key was pressed (0) or released (1)
					// keyboardClicks[0][1] = What key was pressed, which is compared to Input static variables.
					for (Integer[] clicks : keyboardClicks) {
						if (clicks[0] == KEYBOARD_PRESSED_INTEGER)
							if (clicks[1] == Input.KEY_UP) {
								;
							}
							else if (clicks[1] == Input.KEY_DOWN) {
								;
							}
							else if (clicks[1] == Input.KEY_LEFT) {
								;
							}
							else if (clicks[1] == Input.KEY_RIGHT) {
								;
							}
					}
				}
			}.run();
		}
	}

	private synchronized void sendMouseInputToModel(Integer[][] mouseClicks){
		// Mouse input
		if(mouseClicks.length > 0) {
			new Thread() {
				@Override
				public void run() {
					// Call methods in the model according to what key was pressed!
					for(Integer[] clicks : mouseClicks) {
						// clicks[0] = If the mouse button is pressed or released!

						if (clicks[0] == Input.MOUSE_LEFT_BUTTON) {
							;
						}
						else if (clicks[0] == Input.MOUSE_RIGHT_BUTTON) {
							;
						}
					}
				}
			}.run();
		}
	}

	private void handleViewEvent(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("KEY_PRESSED") || evt.getPropertyName().equals("KEY_RELEASED")) {
			// If the 'View' is sending 'Keyboard'-inputs, put them in the correct queue.
			try {
				Integer[] newValue = (Integer[]) evt.getNewValue();
				keyboardSema.acquire();
				keyboardInputQueue.offer(newValue);
				keyboardSema.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else if(evt.getPropertyName().equals("MOUSE_PRESSED") || evt.getPropertyName().equals("MOUSE_RELEASED")){
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
		if(evt.getPropertyName().equals("update")){
			// Get coordinates of all objects in the viewable area!
			updateView();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt != null){
			if(!evt.getPropertyName().equals(null)){
				if(evt.getSource() instanceof StateBasedGame){
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
