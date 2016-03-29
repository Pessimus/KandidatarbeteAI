package Controller;

import Model.*;
import Model.Character;
import View.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Controller implements PropertyChangeListener {
//TODO-----------------------------------????-------------------------------------------------------------------------\\

//TODO REMOVE deprecated method
//	private void handleModelEvent(PropertyChangeEvent evt){
//		if(evt.getPropertyName().equals("update")){
//			//updateView();
//	}


//TODO-----------------------------------END ????---------------------------------------------------------------------\\
//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	//-------------------MVC variables------------------\\
	private World gameModel;
	private StateViewInit gameView;

	//--------------Controller variables----------------\\
	private PlayerBrain player = new PlayerBrain();
	private HashMap<Character, AbstractBrain> aiMap = new HashMap<>();

	//-----------------Model variables------------------\\
	//TODO MEMO check what it affects
	private int gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL;


	//-----------------View variables-------------------\\
	private final Queue<Integer[]> keyboardInputQueue;
	private final Queue<Integer[]> mouseInputQueue;
	private ModelToViewRectangle screenRect;
	private float mouseX;
	private float mouseY;
	private boolean showingPlayerInventory = false;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public Controller(){
		setModel(new World(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));
		setView(new StateViewInit(Constants.GAME_TITLE, Constants.RUN_IN_FULLSCREEN, Constants.GAME_GRAB_MOUSE, Constants.TARGET_FRAMERATE, (int) Constants.SCREEN_WIDTH, (int) Constants.SCREEN_HEIGHT));

		keyboardInputQueue = new LinkedList<>();
		mouseInputQueue = new LinkedList<>();

		mouseX = (float)Constants.SCREEN_WIDTH/2;
		mouseY = (float)Constants.SCREEN_HEIGHT/2;

		screenRect = new ModelToViewRectangle(Constants.DEFAULT_WORLD_VIEW_X, Constants.DEFAULT_WORLD_VIEW_Y, (float)Constants.SCREEN_WIDTH, (float)Constants.SCREEN_HEIGHT);

		//TODO this is hardcoded testing code. Remove after Testing is done!!
			player.setBody(gameModel.addCharacter(1000, 1000, Constants.PLAYER_CHARACTER_KEY));
			((Character)player.getBody()).godMode = true;

			this.gameModel.addFiniteResourcePoint(new Crops(100),1010,1010,5);

	}

//-----------------------------------------Initialization methods-----------------------------------------------------\\

	private void setView(StateViewInit view){
		gameView = view;
		gameView.addPropertyChangeListener(this);
	}

	public void setModel(World model){
		gameModel = model;
		gameModel.addPropertyChangeListener(this);
	}

//----------------------------------------------Run methods-----------------------------------------------------------\\

	public void start() {
		this.gameView.run();
	}

	private void runModel(){
		new Timer().scheduleAtFixedRate(new TimerTask() {
			public void run() {
				update();
			}
		}, 0, 1000 / gameSpeed);
	}

	public void update(){
		player.update();
		for (AbstractBrain brain : aiMap.values()) {
			brain.update();
		}
		updateModel();
		updateView();
	}

	/**
	 * Update various elements of the view, such as
	 * the saved location of the screen-view in regards to the world,
	 * acquire ALL renderable objects from the model and filter out the ones
	 * not visible in the screen-view and send these objects to the view.
	 */
	private void updateView(){
		List<RenderObject> temp = new LinkedList<>();


		// Move the screen-view over the world if the mouse is close
		// to either edge of the screen.
		if (mouseX >= Constants.SCREEN_EDGE_TRIGGER_MAX_X) {
			float width = (float)gameModel.getWidth();
			if (screenRect.getMaxX() < width) {
				screenRect.translatePosition(Constants.SCREEN_SCROLL_SPEED_X / Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL, 0);
			} else {
				screenRect.setMaxX(width);
			}
		} else if (mouseX <= Constants.SCREEN_EDGE_TRIGGER_MIN_X) {
			if (screenRect.getMinX() > 0) {
				screenRect.translatePosition(-Constants.SCREEN_SCROLL_SPEED_X / Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL, 0);
			} else {
				screenRect.setMinX(0);
			}
		}

		if (mouseY >= Constants.SCREEN_EDGE_TRIGGER_MAX_Y) {
			float height = (float)gameModel.getHeight();
			if (screenRect.getMaxY() < height) {
				screenRect.translatePosition(0, Constants.SCREEN_SCROLL_SPEED_Y / Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL);
			} else {
				screenRect.setMaxY(height);
			}
		} else if (mouseY <= Constants.SCREEN_EDGE_TRIGGER_MIN_Y) {
			if (screenRect.getMinY() > 0) {
				screenRect.translatePosition(0, -Constants.SCREEN_SCROLL_SPEED_Y / Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL);
			} else {
				screenRect.setMinY(0);
			}
		}

		// Get renderable objects from the model and proceed to
		// filter out the ones not currently inside the screen-view
		RenderObject[] obj = gameModel.getRenderObjects();

		for (RenderObject tempObj : obj) {
			float[] tempInts = convertFromModelToViewCoords(tempObj.getX(), tempObj.getY());
			temp.add(new RenderObject(tempInts[0], tempInts[1], tempObj.getRadius(), tempObj.getRenderType()));
		}

		gameView.setRenderPoint(screenRect.getMinX(), screenRect.getMinY());

		if(temp.size() > 0) {
			gameView.drawRenderObjects(temp);
		}

		if (showingPlayerInventory){
			gameView.drawInventory(gameModel.displayPlayerInventory());
		}
	}

	/**
	 * Uses input from the View to manipulate the Model.
	 */
	private void updateModel(){
		//TODO fix concurrency
		Object[] tempList = keyboardInputQueue.toArray();
		keyboardInputQueue.clear();

		if (tempList.length > 0) {
			Integer[][] tempKeyList = Arrays.copyOf(tempList, tempList.length, Integer[][].class);
			handleKeyboardInput(tempKeyList);
		}

		//TODO fix concurrency
		tempList = mouseInputQueue.toArray();
		mouseInputQueue.clear();

		if (tempList.length > 0) {
			Integer[][] tempMouseList = Arrays.copyOf(tempList, tempList.length, Integer[][].class);
			handleMouseInput(tempMouseList);
		}

		gameModel.uppdate();
	}

//--------------------------------------------Input Methods-----------------------------------------------------------\\

	private void handleKeyboardInput(Integer[][] keyboardClicks) {
		// Keyboard input
		if (keyboardClicks.length > 0) {
			// Call methods in the model according to what key was pressed!
			// The array for key-clicks work like this:
			for (Integer[] clicks : keyboardClicks) {
				// clicks[0] = Whether the key was pressed/released (1: Pressed, 0: Released)
				// clicks[1] = What key was pressed/released
				if (clicks[0] == View.INPUT_ENUM.KEY_PRESSED.value) {
					switch (clicks[1]){
						case Input.KEY_UP:
							player.movePlayerUp();
							break;
						case Input.KEY_DOWN:
							player.movePlayerDown();
							break;
						case Input.KEY_LEFT:
							player.movePlayerLeft();
							break;
						case Input.KEY_RIGHT:
							player.movePlayerRight();
							break;
						case Input.KEY_R:
							player.playerRunning();
							break;
						case Input.KEY_P:
							gameModel.togglePause();
							break;
						case Input.KEY_1:
							gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL;
							break;
						case Input.KEY_2:
							gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_FASTER;
							break;
						case Input.KEY_3:
							gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_FASTEST;
							break;
					}
				}else if(clicks[0] == View.INPUT_ENUM.KEY_RELEASED.value){
					switch (clicks[1]){
						case Input.KEY_UP:
							player.stopPlayerUp();
							break;
						case Input.KEY_DOWN:
							player.stopPlayerDown();
							break;
						case Input.KEY_LEFT:
							player.stopPlayerLeft();
							break;
						case Input.KEY_RIGHT:
							player.stopPlayerRight();
							break;
						case Input.KEY_R:
							player.playerWalking();
							break;
						case Input.KEY_I:
							showingPlayerInventory = !showingPlayerInventory;
							if(showingPlayerInventory){
								gameView.drawInventory(gameModel.displayPlayerInventory());
							}else{
								gameView.hidePlayerInventory();
							}
							break;
					}
				}
			}
		}
	}

//TODO --------------Decide what should happen in the different cases. Right now nothing happens!!----------------------
	private void handleMouseInput(Integer[][] mouseClicks){
		// Mouse input
		if(mouseClicks.length > 0) {
			// Call methods in the model according to what button was pressed!
			for(Integer[] clicks : mouseClicks) {
				// clicks[0] = If the mouse button is pressed/released/moved!
				// clicks[1] = What mouse-button was pressed/released
				// clicks[2] = x-value of where the mouse was pressed/released
				// clicks[3] = y-value of where the mouse was pressed/released

				if (clicks[0] == View.INPUT_ENUM.MOUSE_PRESSED.value) {
					//The mouse button was pressed.
					if(clicks[1] == Input.MOUSE_LEFT_BUTTON){
						//The left mouse button was pressed.
						//TODO WHAT SHOULD BE DONE HERE?!
						//float[] tempFloats = convertFromViewToModelCoords(clicks[2], clicks[3]);
						//gameModel.selectObject(tempFloats[0], tempFloats[1]);
					}

					if(clicks[1] == Input.MOUSE_RIGHT_BUTTON){
						//The right mouse button was pressed.
						//TODO WHAT SHOULD BE DONE HERE?!
					}
				}
				else if (clicks[0] == View.INPUT_ENUM.MOUSE_RELEASED.value) {
					//The mouse button was released.
					if(clicks[1] == Input.MOUSE_LEFT_BUTTON){
						//The left mouse button was released.
						//TODO WHAT SHOULD BE DONE HERE?!
					}

					if(clicks[1] == Input.MOUSE_RIGHT_BUTTON){
						//The right mouse button was released.
						//TODO WHAT SHOULD BE DONE HERE?!
					}
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
	}

//-------------------------------------Event handling methods---------------------------------------------------------\\

	//TODO (if possible) add ENUMS for where from the update was sent.
	//TODO Remove this step as there are no events from the model?
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt != null){
			if(evt.getPropertyName() != null){
				if(evt.getSource() instanceof BasicGameState){
					// If the source of the event is the 'View', handle that separately.
					handleViewEvent(evt);
				}
//				if(evt.getSource().equals(gameModel)){
//					// If the source of the event is the 'Model', handle that separately.
//					handleModelEvent(evt);
//				}
			}
		}
	}

	//TODO Comment and clean up.
	private void handleViewEvent(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals(View.INPUT_ENUM.KEY_PRESSED.toString()) || evt.getPropertyName().equals(View.INPUT_ENUM.KEY_RELEASED.toString())) {
			// If the 'View' is sending 'Keyboard'-inputs, put them in the correct queue.
			Integer[] newValue = (Integer[]) evt.getNewValue();
			keyboardInputQueue.offer(newValue);
		}
		else if(evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_PRESSED.toString()) || evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_RELEASED.toString())){
			// If the View is sending 'Mouse'-inputs, put them in the correct queue.
			Integer[] newValue = (Integer[]) evt.getNewValue();
			mouseInputQueue.offer(newValue);
		}
		else if(evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_MOVED.toString())){
			Integer[] newValue = (Integer[]) evt.getNewValue();
			mouseInputQueue.offer(newValue);
		}
		else if(evt.getPropertyName().equals("startController")){
			runModel();
		}
		else if(evt.getPropertyName().equals("createdCharacter")){
			Character character = (Character)evt.getNewValue();
			if(aiMap.containsKey(character)){
				AbstractBrain tempChar = aiMap.get(character);
				if(tempChar != null){
					tempChar.setBody(null);
				}
			}
			aiMap.put(character, new ArtificialBrain((ICharacterHandle)character));
		}
	}

//----------------------------------Model and View converting methods-------------------------------------------------\\

	private float[] convertFromModelToViewCoords(float x, float y){
		return new float[]{x - screenRect.getMinX(), y - screenRect.getMinY()};
	}

	private float[] convertFromViewToModelCoords(float x, float y){
		return new float[]{x + screenRect.getMinX(), y + screenRect.getMinY()};
	}

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
			return x >= minX && x <= maxX && y >= minY && y <= maxY;
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
			this.maxX += (minX - this.minX);
			this.minX = minX;
		}

		public void setMinY(float minY) {
			this.maxY += (minY - this.minY);
			this.minY = minY;
		}

		public void setMaxX(float maxX) {
			this.minX += (maxX - this.maxX);
			this.maxX = maxX;
		}

		public void setMaxY(float maxY) {
			this.minY += (maxY - this.maxY);
			this.maxY = maxY;
		}
	}

}
