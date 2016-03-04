package Controller;

import Model.RenderObject;
import Model.World;
import View.*;
import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Controller implements PropertyChangeListener, Runnable {
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

		//new Thread(new Controller(view, model)).start();
		new Controller(view, model).run();
	}

	public Controller(StateViewInit view, World model){
		keyboardInputQueue = new LinkedList<>();
		mouseInputQueue = new LinkedList<>();
		setView(view);
		setModel(model);

		new Thread(view).start();
		//view.run();
	}

	public void run(){
		while(true){
			try {
				keyboardSema.acquire();
				Object[] tempList = keyboardInputQueue.toArray();
				keyboardInputQueue.clear();
				keyboardSema.release();

				if (tempList.length > 0) {
					Integer[][] tempKeyList = new Integer[tempList.length][];
					for (int i = 0; i < tempList.length; i++) {
						tempKeyList[i] = (Integer[]) tempList[i];
					}
					sendKeyboardInputToModel(tempKeyList);
				}

				mouseSema.acquire();
				tempList = mouseInputQueue.toArray();
				mouseInputQueue.clear();
				mouseSema.release();

				if (tempList.length > 0) {
					Integer[][] tempMouseList = new Integer[tempList.length][];
					for (int i = 0; i < tempList.length; i++) {
						tempMouseList[i] = (Integer[]) tempList[i];
					}
					sendMouseInputToModel(tempMouseList);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Semaphores were interrupted!", e);
			} catch (ClassCastException e) {
				e.printStackTrace();
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Typecasting of input-arrays failed!", e);
			}
		}

		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		/*
		new Timer().scheduleAtFixedRate(new TimerTask(){
			public void run() {
				System.out.println("RUNNED");
				try {
					keyboardSema.acquire();
					Object[] tempList = keyboardInputQueue.toArray();
					keyboardInputQueue.clear();
					keyboardSema.release();

					if (tempList.length > 0) {
						Integer[][] tempKeyList = new Integer[tempList.length][];
						for (int i = 0; i < tempList.length; i++) {
							tempKeyList[i] = (Integer[]) tempList[i];
						}
						sendKeyboardInputToModel(tempKeyList);
					}

					mouseSema.acquire();
					tempList = mouseInputQueue.toArray();
					mouseInputQueue.clear();
					mouseSema.release();

					if (tempList.length > 0) {
						Integer[][] tempMouseList = new Integer[tempList.length][];
						for (int i = 0; i < tempList.length; i++) {
							tempMouseList[i] = (Integer[]) tempList[i];
						}
						sendMouseInputToModel(tempMouseList);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Semaphores were interrupted!", e);
				} catch (ClassCastException e) {
					e.printStackTrace();
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Typecasting of input-arrays failed!", e);
				}
			}
		}, 1, 1);
		*/
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
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
			gameModel.addPropertyChangeListener(this); // TODO: 'Model' should use PropertyChangeSupport
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
		/*
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		gameView.drawRenderObjects(gameModel.getCharacter());
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		// TODO: HARDCODED TEST!!!!!
		*/
	}

	private synchronized void sendKeyboardInputToModel(Integer[][] keyboardClicks) {
		// Keyboard input
		if (keyboardClicks.length > 0) {
			new Thread() {
				@Override
				public void run() {
					// The array for key-clicks work like this:
					for (Integer[] clicks : keyboardClicks) {
						// clicks[0] = Wether the key was pressed/released
						// clicks[1] = What key was pressed/released

						if (clicks[0] == View.INPUT_ENUM.KEY_PRESSED.value)
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
						// clicks[1] = What mouse-button was pressed/released
						// clicks[2] = x-value of where the mouse was pressed/released
						// clicks[3] = y-value of where the mouse was pressed/released

						if (clicks[0] == View.INPUT_ENUM.MOUSE_PRESSED.value) {
							if(clicks[1] == Input.MOUSE_LEFT_BUTTON){
								/*
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								gameModel.moveCharacterTo(clicks[2], clicks[3]);
								updateView();
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								*/
							}
						}
						else if (clicks[0] == View.INPUT_ENUM.MOUSE_RELEASED.value) {
							;
						}
					}
				}
			}.run();
		}
	}

	private void handleViewEvent(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(View.INPUT_ENUM.KEY_PRESSED.toString()) || evt.getPropertyName().equals(View.INPUT_ENUM.KEY_RELEASED.toString())) {
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
		else if(evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_PRESSED.toString()) || evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_RELEASED.toString())){
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
				//if(evt.getSource().equals(gameView)){
					// If the source of the event is the 'View', handle that separately.
					handleViewEvent(evt);
				//}

				if(evt.getSource().equals(gameModel)){
					// If the source of the event is the 'Model', handle that separately.
					handleModelEvent(evt);
				}
			}
		}
	}
}
