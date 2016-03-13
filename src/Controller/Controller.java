package Controller;

import Model.Constants;
import Model.RenderObject;
import Model.World;
import View.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;

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
	/* MVC */
	private World gameModel;
	private StateViewInit gameView;

	/* Help Objects */
	private final Queue<Integer[]> keyboardInputQueue;
	private final Queue<Integer[]> mouseInputQueue;

	private final Semaphore keyboardSema = new Semaphore(1);
	private final Semaphore mouseSema = new Semaphore(1);

	private Semaphore screenRectSema = new Semaphore(1);
	private ModelToViewRectangle screenRect;

	private float mouseX;
	private float mouseY;

	public Pathfinder pathCalculator = new Pathfinder(16, 9600, 9600, 1, 1.4);

	private final class ModelToViewRectangle{
		float rectWidth, rectHeight;

		float minX, minY, maxX, maxY;

		ModelToViewRectangle(float x, float y, float width, float height){
			rectWidth = width;
			rectHeight = height;

			minX = x;
			minY = y;
			maxX = x + width;
			maxY = y + height;
		}

		public void translatePosition(float deltaX, float deltaY){
			minX += deltaX;
			minY += deltaY;
			maxX += deltaX;
			maxY += deltaY;
		}

		public boolean contains(float x, float y){
			return x > minX && x < maxX && y > minY && y < maxY;
		}

		public float getMinX() {
			return minX;
		}

		public float getMinY() {
			return minY;
		}

		public float getMaxX() {
			return maxX;
		}

		public float getMaxY() {
			return maxY;
		}

		public float getWidth() {
			return rectWidth;
		}

		public float getHeight() {
			return rectHeight;
		}


		public void setMinX(float minX) {
			this.maxX += minX - this.minX;
			this.minX = minX;
		}

		public void setMinY(float minY) {
			this.maxY += minY - this.minY;
			this.minY = minY;
		}

		public void setMaxX(float maxX) {
			this.minX += maxX - this.maxX;
			this.maxX = maxX;
		}

		public void setMaxY(float maxY) {
			this.minY += maxY - this.maxY;
			this.maxY = maxY;
		}
	}

	public static void main(String[] args){
		World model = new World(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		StateViewInit view = new StateViewInit(Constants.GAME_TITLE, Constants.RUN_IN_FULLSCREEN, Constants.GAME_GRAB_MOUSE, Constants.TARGET_FRAMERATE, (int)Constants.WORLD_WIDTH, (int)Constants.WORLD_HEIGHT);

		new Controller(view, model).run();
		view.run();
	}

	public Controller(StateViewInit view, World model){
		keyboardInputQueue = new LinkedList<>();
		mouseInputQueue = new LinkedList<>();
		setView(view);
		setModel(model);

		mouseX = ((float)Constants.SCREEN_WIDTH - Constants.DEFAULT_WORLD_VIEW_X)/2;
		mouseY = ((float)Constants.SCREEN_HEIGHT - Constants.DEFAULT_WORLD_VIEW_Y)/2;

		screenRect = new ModelToViewRectangle(Constants.DEFAULT_WORLD_VIEW_X, Constants.DEFAULT_WORLD_VIEW_Y, (float)Constants.SCREEN_WIDTH, (float)Constants.SCREEN_HEIGHT);
	}

	@Override
	public void run(){
		new Timer().scheduleAtFixedRate(new TimerTask(){
			public void run() {
				updateModel();
				updateView();
			}
		}, 0, Constants.CONTROLLER_UPDATE_INTERVAL);
	}

	public synchronized boolean setView(StateViewInit view){
		if(view != null){
			gameView = view;
			gameView.addPropertyChangeListener(this); // TODO: 'View' should use PropertyChangeSupport
			return true;
		}

		return false;
	}

	public synchronized boolean setModel(World model){
		if(model != null){
			gameModel = model;
			gameModel.addPropertyChangeListener(this); // TODO: 'Model' should use PropertyChangeSupport
			return true;
		}

		return false;
	}

	private void updateView(){
		List<RenderObject> temp = new LinkedList<>();

		try {
			screenRectSema.acquire();
			if (mouseX >= Constants.SCREEN_EDGE_TRIGGER_MAX_X) {
				if (screenRect.getMaxX() < gameModel.getWidth()) {
					//float temp = (float)(mouseX - Constants.SCREEN_EDGE_TRIGGER_MAX_X);
					screenRect.translatePosition(Constants.SCREEN_SCROLL_SPEED_X / Constants.CONTROLLER_UPDATE_INTERVAL, 0);
				} else {
					//screenRect.translatePosition((float)gameModel.getWidth() - screenRect.getMaxX(), 0);
					screenRect.setMaxX((float) gameModel.getWidth());
				}
			} else if (mouseX <= Constants.SCREEN_EDGE_TRIGGER_MIN_X) {
				if (screenRect.getMinX() > 0) {
					//float temp = (float)(mouseX - Constants.SCREEN_EDGE_TRIGGER_MIN_X);
					screenRect.translatePosition(-Constants.SCREEN_SCROLL_SPEED_X / Constants.CONTROLLER_UPDATE_INTERVAL, 0);
				} else {
					//screenRect.translatePosition(-screenRect.getMinX(), 0);
					screenRect.setMinX(0);
				}
			}

			if (mouseY >= Constants.SCREEN_EDGE_TRIGGER_MAX_Y) {
				if (screenRect.getMaxY() < gameModel.getHeight()) {
					//float temp = (float)(mouseY - Constants.SCREEN_EDGE_TRIGGER_MAX_Y);
					screenRect.translatePosition(0, Constants.SCREEN_SCROLL_SPEED_Y / Constants.CONTROLLER_UPDATE_INTERVAL);
				} else {
					//screenRect.translatePosition(0, (float)gameModel.getHeight() - screenRect.getMaxY());
					screenRect.setMaxY((float) gameModel.getHeight());
				}
			} else if (mouseY <= Constants.SCREEN_EDGE_TRIGGER_MIN_Y) {
				if (screenRect.getMinY() > 0) {
					//float temp = (float)(mouseY - Constants.SCREEN_EDGE_TRIGGER_MIN_Y);
					screenRect.translatePosition(0, -Constants.SCREEN_SCROLL_SPEED_Y / Constants.CONTROLLER_UPDATE_INTERVAL);
				} else {
					//screenRect.translatePosition(0, -screenRect.getMinY());
					screenRect.setMinY(0);
				}
			}
			List<RenderObject> obj = gameModel.getRenderObjects();

			for (RenderObject tempObj : obj) {
				if (screenRect.contains(tempObj.getX(), tempObj.getY())) {
					float[] tempInts = convertFromModelToViewCoords(tempObj.getX(), tempObj.getY());
					temp.add(new RenderObject(tempInts[0], tempInts[1], tempObj.getRadius(), tempObj.getObjectType()));
				}
			}

			gameView.setRenderPoint(screenRect.getMinX(), screenRect.getMinY());
			screenRectSema.release();
		}
		catch (InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Semaphores were interrupted in 'updateView()' method!", e);
		}

		if(temp.size() > 0) {
			gameView.drawRenderObjects(temp);
		}
	}

	/**
	 * Uses input from the View to manipulate the Model.
	 */
	private void updateModel(){
		try {
			keyboardSema.acquire();
			Object[] tempList = keyboardInputQueue.toArray();
			keyboardInputQueue.clear();
			keyboardSema.release();

			if (tempList != null) {
				if (tempList.length > 0) {
					Integer[][] tempKeyList = Arrays.copyOf(tempList, tempList.length, Integer[][].class);
					sendKeyboardInputToModel(tempKeyList);
				}
			}

			mouseSema.acquire();
			tempList = mouseInputQueue.toArray();
			mouseInputQueue.clear();
			mouseSema.release();

			if (tempList != null) {
				if (tempList.length > 0) {
					Integer[][] tempMouseList = Arrays.copyOf(tempList, tempList.length, Integer[][].class);
					sendMouseInputToModel(tempMouseList);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Semaphores were interrupted in 'run()' method!", e);
		} catch (ClassCastException e) {
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Typecasting of input-arrays failed!", e);
		}

		gameModel.update();
	}

	private synchronized void sendKeyboardInputToModel(Integer[][] keyboardClicks) {
		// Keyboard input
		if (keyboardClicks.length > 0) {
			// Call methods in the model according to what key was pressed!

			new Thread() {
				@Override
				public void run() {
					// The array for key-clicks work like this:
					for (Integer[] clicks : keyboardClicks) {
						// clicks[0] = Whether the key was pressed/released (1: Pressed, 0: Released)
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
			// Call methods in the model according to what button was pressed!

			new Thread() {
				@Override
				public void run() {
					// Call methods in the model according to what button was pressed or whether the mouse was moved!
					for(Integer[] clicks : mouseClicks) {
						// clicks[0] = If the mouse button is pressed/released/moved!
						// clicks[1] = What mouse-button was pressed/released
						// clicks[2] = x-value of where the mouse was pressed/released
						// clicks[3] = y-value of where the mouse was pressed/released

						if (clicks[0] == View.INPUT_ENUM.MOUSE_PRESSED.value) {
							if(clicks[1] == Input.MOUSE_LEFT_BUTTON){

								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!

								//gameModel.moveCharacterTo(clicks[2], clicks[3]);

								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!
								// TODO: HARDCODED TEST!!!!!

							}
						}
						else if (clicks[0] == View.INPUT_ENUM.MOUSE_RELEASED.value) {
							;
						}

						// clicks[0] = If the mouse button is pressed/released/moved!
						// clicks[1] = x-value of the old mouse position
						// clicks[2] = y-value of the old mouse position
						// clicks[3] = x-value of the new mouse position
						// clicks[4] = y-value of the new mouse position
						else if (clicks[0] == View.INPUT_ENUM.MOUSE_MOVED.value) {
							mouseX = clicks[3];
							mouseY = clicks[4];
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
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Semaphores were interrupted in 'handleViewEvent()' method!", e);
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
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Semaphores were interrupted in 'handleViewEvent()' method!", e);
			}
		}
		else if(evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_MOVED.toString())){
			try{
				Integer[] newValue = (Integer[]) evt.getNewValue();
				mouseSema.acquire();
				mouseInputQueue.offer(newValue);
				mouseSema.release();
			} catch (InterruptedException e){
				e.printStackTrace();
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Semaphores were interrupted in 'handleViewEvent()' method!", e);
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
				if(evt.getSource() instanceof BasicGameState){
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

	private float[] convertFromModelToViewCoords(float x, float y){
		return new float[]{x - screenRect.getMinX(), y - screenRect.getMinY()};
	}
}
