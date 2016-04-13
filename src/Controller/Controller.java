package Controller;

import Model.*;
import Model.Character;
import Utility.Constants;
import Utility.RenderObject;
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
	private PlayerBrain player;
	private HashMap<Character, AbstractBrain> aiMap = new HashMap<>();

	//-----------------Model variables------------------\\
	//TODO MEMO check what it affects
	private int gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL;

	private boolean playerViewCentered = true;
	private int itemHighlighted = -1;


	//-----------------View variables-------------------\\
	private final Queue<Integer[]> keyboardInputQueue;
	private final Queue<Integer[]> mouseInputQueue;
	private ModelToViewRectangle screenRect;
	private float mouseX;
	private float mouseY;
	private float scaleGraphicsX;
	private float scaleGraphicsY;
	private boolean showingPlayerInventory = false;
	private boolean showingBuildOptions = false;


//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public Controller(){
		scaleGraphicsX = (float)(Constants.SCREEN_WIDTH*Constants.ZOOM_LEVEL/Constants.STANDARD_SCREEN_WIDTH);
		scaleGraphicsY = (float)(Constants.SCREEN_HEIGHT*Constants.ZOOM_LEVEL/Constants.STANDARD_SCREEN_HEIGHT);
		//setModel(new World(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

		//TODO remove test
			setModel(new World(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, 100, 20, 100, 200));

		setView(new StateViewInit(Constants.GAME_TITLE, Constants.RUN_IN_FULLSCREEN, Constants.GAME_GRAB_MOUSE, Constants.TARGET_FRAMERATE, (int)Constants.SCREEN_WIDTH, (int)Constants.SCREEN_HEIGHT, scaleGraphicsX, scaleGraphicsY));


		keyboardInputQueue = new LinkedList<>();
		mouseInputQueue = new LinkedList<>();

		mouseX = (float)Constants.SCREEN_WIDTH/2;
		mouseY = (float)Constants.SCREEN_HEIGHT/2;

		screenRect = new ModelToViewRectangle(Constants.DEFAULT_WORLD_VIEW_X, Constants.DEFAULT_WORLD_VIEW_Y, (float)Constants.SCREEN_WIDTH, (float)Constants.SCREEN_HEIGHT);

		//TODO this is hardcoded testing code. Remove after Testing is done!!

				player = new PlayerBrain(gameModel.addCharacter(0, 0, Constants.PLAYER_CHARACTER_KEY));

				((Character)player.getBody()).godMode = true;

				Character character = gameModel.addCharacter(1100, 1100, 2);
				aiMap.put(character, new ArtificialBrain(gameModel, character));

				Random r = new Random();

				for(int i = 3; i < 1; i++) {
					character = gameModel.addCharacter(r.nextInt(9600), r.nextInt(9600), i);
					aiMap.put(character, new ArtificialBrain(gameModel, character));
				}

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
		gameView.drawNeeds(player.getBody().getNeeds());
		int width = (int)gameModel.getWidth()+Constants.VIEW_BORDER_WIDTH*2;
		int height = (int)gameModel.getHeight()+Constants.VIEW_BORDER_HEIGHT*2;

		//Centers the player in the middle of the screen
		if(playerViewCentered) {
			if (!gameModel.isPaused()){
				float playerXPos = player.getBody().getX()+Constants.VIEW_BORDER_WIDTH;
				float playerYPos = player.getBody().getY()+Constants.VIEW_BORDER_HEIGHT;


				if (playerXPos - Constants.SCREEN_WIDTH / (2 * scaleGraphicsX) > 0) {
					if (playerXPos + Constants.SCREEN_WIDTH / (2 * scaleGraphicsX) < width) {
						screenRect.setMinX((float)(playerXPos - Constants.SCREEN_WIDTH / (2 * scaleGraphicsX)));
					} else {
						screenRect.setMaxX(width);
					}
				} else {
					screenRect.setMinX(0);
				}

				if (playerYPos - Constants.SCREEN_HEIGHT / (2 * scaleGraphicsY) > 0) {
					if (playerYPos + Constants.SCREEN_HEIGHT / (2 * scaleGraphicsY) < height) {
						screenRect.setMinY((float)(playerYPos - Constants.SCREEN_HEIGHT / (2 * scaleGraphicsY)));
					} else {
						screenRect.setMaxY(height);
					}
				} else {
					screenRect.setMinY(0);
				}
				//Spectator mode: Choose where you want to be on the screen!
			}
		}else {

			// Move the screen-view over the world if the mouse is close
			// to either edge of the screen.
			if (mouseX >= Constants.SCREEN_EDGE_TRIGGER_MAX_X) {
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
		}

		// Get renderable objects from the model and proceed to
		// filter out the ones not currently inside the screen-view
		RenderObject[] obj = gameModel.getRenderObjects();

		for (RenderObject tempObj : obj) {
			float[] tempFloats = convertFromModelToViewCoords(tempObj.getX(), tempObj.getY());
			temp.add(new RenderObject(tempFloats[0], tempFloats[1], tempObj.getRadius(), tempObj.getRenderType()));
		}

		gameView.setRenderPoint(screenRect.getMinX(), screenRect.getMinY());

		if (temp.size() > 0) {
			gameView.drawRenderObjects(temp);
		}

		if (showingPlayerInventory) {
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

		gameModel.update();
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
					switch (clicks[1]) {
						case Input.KEY_UP:
							if(!gameModel.isPaused())
								player.movePlayerUp();
							break;
						case Input.KEY_DOWN:
							if(!gameModel.isPaused())
								player.movePlayerDown();
							break;
						case Input.KEY_LEFT:
							if(!gameModel.isPaused())
								player.movePlayerLeft();
							break;
						case Input.KEY_RIGHT:
							if(!gameModel.isPaused())
								player.movePlayerRight();
							break;
						case Input.KEY_R:
							player.playerRunning();
							break;
						case Input.KEY_P:
							gameModel.togglePause();
							gameView.displayPause();
							break;
						case Input.KEY_Q:
							if(!gameModel.isPaused())
								player.attack();
							break;
						case Input.KEY_W:
							if(!gameModel.isPaused())
								player.interact();
							break;
						case Input.KEY_E:
							if(!gameModel.isPaused())
								player.consume();
							break;
						case Input.KEY_B:
							if (playerViewCentered && showingPlayerInventory && !gameModel.isPaused()) {
								itemHighlighted = -1;
								gameView.highlightInventoryItem(0);
							} else{
								//TODO hide display
								gameView.displayBuildingInProcess();
								showingBuildOptions = !showingBuildOptions;
							}
							break;
						case Input.KEY_C:
							if (playerViewCentered && showingPlayerInventory && itemHighlighted >= 0 && !gameModel.isPaused()) {
								player.getBody().consumeItem(itemHighlighted);
							}
							break;
						case Input.KEY_D:
							if (playerViewCentered && showingPlayerInventory && itemHighlighted >= 0 && !gameModel.isPaused()) {
								player.getBody().getInventory().remove(itemHighlighted);
								itemHighlighted = -1;
							}
							break;
						case Input.KEY_1:
							if (playerViewCentered && !gameModel.isPaused()) {
								if(showingPlayerInventory && player.getBody().getInventory().size() >= 1){
									gameView.highlightInventoryItem(1);
									itemHighlighted = 0;
								}else if(showingBuildOptions){
									player.build(1);
								}
							}else{
								gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL;
							}
							break;
						case Input.KEY_2:
							if (playerViewCentered && !gameModel.isPaused()) {
								if(showingPlayerInventory && player.getBody().getInventory().size() >= 2){
									gameView.highlightInventoryItem(2);
									itemHighlighted = 1;
								}else if(showingBuildOptions){
									player.build(2);
								}
							}else{
								gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_FASTER;
							}
							break;
						case Input.KEY_3:
							if(playerViewCentered && !gameModel.isPaused()){
								if(showingPlayerInventory && player.getBody().getInventory().size() >= 3){
									gameView.highlightInventoryItem(3);
									itemHighlighted = 2;
								}else if(showingBuildOptions){
									player.build(3);
								}
							}else{
								gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_FASTEST;
							}
							break;
						case Input.KEY_4:
							if(playerViewCentered && showingPlayerInventory && !gameModel.isPaused()){
								if(player.getBody().getInventory().size() >= 4){
									gameView.highlightInventoryItem(4);
									itemHighlighted = 3;
								}
							}
							break;
						case Input.KEY_5:
							if(playerViewCentered && showingPlayerInventory && !gameModel.isPaused()){
								if(player.getBody().getInventory().size() >= 5){
									gameView.highlightInventoryItem(5);
									itemHighlighted = 4;
								}
							}
							break;
						case Input.KEY_6:
							if(playerViewCentered && showingPlayerInventory && !gameModel.isPaused()){
								if(player.getBody().getInventory().size() >= 6){
									gameView.highlightInventoryItem(6);
									itemHighlighted = 5;
								}
							}

							break;
						case Input.KEY_7:
							if(playerViewCentered && showingPlayerInventory && !gameModel.isPaused()){
								if(player.getBody().getInventory().size() >= 7){
									gameView.highlightInventoryItem(7);
									itemHighlighted = 6;
								}
							}

							break;
						case Input.KEY_8:
							if(playerViewCentered && showingPlayerInventory && !gameModel.isPaused()) {
								if (player.getBody().getInventory().size() >= 8) {
									gameView.highlightInventoryItem(8);
									itemHighlighted = 7;
								}
							}
							break;
						case Input.KEY_9:
							if(playerViewCentered && showingPlayerInventory && !gameModel.isPaused()){
								if(player.getBody().getInventory().size() >= 9){
									gameView.highlightInventoryItem(9);
									itemHighlighted = 8;
								}
							}
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
							if(playerViewCentered) {
								if(!gameModel.isPaused()) {
									showingPlayerInventory = !showingPlayerInventory;
									if (showingPlayerInventory) {
										gameView.drawInventory(gameModel.displayPlayerInventory());
									} else {
										gameView.hidePlayerInventory();
										gameView.highlightInventoryItem(0);
										itemHighlighted = -1;
									}
								}
							}else{
								showingPlayerInventory = false;
								gameView.hidePlayerInventory();
							}
							break;
						case Input.KEY_V:
							if(showingBuildOptions){
								gameView.displayBuildingInProcess();
								showingBuildOptions = false;
							}
							playerViewCentered = !playerViewCentered;
							if(!playerViewCentered && showingPlayerInventory){
								showingPlayerInventory = false;
								gameView.hidePlayerInventory();
								gameView.highlightInventoryItem(0);
								itemHighlighted = -1;
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
						float[] tempFloats = convertFromViewToModelCoords((clicks[2]/(float)Constants.GRAPHICS_SCALE_X)/Constants.ZOOM_LEVEL, (clicks[3]/(float)Constants.GRAPHICS_SCALE_Y)/Constants.ZOOM_LEVEL);
						player.moveToMouse(tempFloats[0], tempFloats[1]);
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
	}

//----------------------------------Model and View converting methods-------------------------------------------------\\

	private float[] convertFromModelToViewCoords(float x, float y){
		return new float[]{x - screenRect.getMinX() + Constants.VIEW_BORDER_WIDTH, y - screenRect.getMinY() + Constants.VIEW_BORDER_HEIGHT};
	}

	private float[] convertFromViewToModelCoords(float x, float y){
		return new float[]{x + screenRect.getMinX() - Constants.VIEW_BORDER_WIDTH, y + screenRect.getMinY() - Constants.VIEW_BORDER_HEIGHT};
	}

	private final class ModelToViewRectangle{
		float rectWidth, rectHeight;

		float minX, minY, maxX, maxY, scale;

		ModelToViewRectangle(float x, float y, float width, float height){
			rectWidth = width/scaleGraphicsX;
			rectHeight = height/scaleGraphicsY;

			minX = x/scaleGraphicsX;
			minY = y/scaleGraphicsY;
			maxX = (x + width)/scaleGraphicsX;
			maxY = (y + height)/scaleGraphicsY;
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
