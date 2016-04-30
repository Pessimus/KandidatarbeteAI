package Controller;

import Model.*;
import Model.Character;
import Utility.Constants;
import Utility.RenderObject;
import View.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Controller implements PropertyChangeListener {
//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	//-------------------MVC variables------------------\\
	private World gameModel;
	private StateViewInit gameView;

	//--------------Controller variables----------------\\
	private PlayerBrain player;
	private HashMap<Character, AbstractBrain> aiMap = new HashMap<>();

	//-----------------Model variables------------------\\
	private int gameSpeed = Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL;

	private boolean playerViewCentered = true;
	private int itemHighlighted = -1;


	//-----------------View variables-------------------\\
	private final Queue<Integer[]> keyboardInputQueue;
	private final Queue<Integer[]> mouseInputQueue;
	private ModelToViewRectangle screenRect;
	private double mouseX;
	private double mouseY;
	private double scaleGraphicsX;
	private double scaleGraphicsY;
	private boolean showingPlayerInventory = false;
	private boolean showingBuildOptions = false;


	private double playerXPos = 0, playerYPos = 0;

	private int characterIndex = 0;
	private Character currentCharacter;


//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public Controller(){
		scaleGraphicsX = Constants.SCREEN_WIDTH*Constants.ZOOM_LEVEL/Constants.STANDARD_SCREEN_WIDTH;
		scaleGraphicsY = Constants.SCREEN_HEIGHT*Constants.ZOOM_LEVEL/Constants.STANDARD_SCREEN_HEIGHT;
		//setModel(new World(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

		//TODO remove test
			setModel(new World(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, 100, 20, 100, 200, 20));

		setView(new StateViewInit(Constants.GAME_TITLE, Constants.RUN_IN_FULLSCREEN, Constants.GAME_GRAB_MOUSE,
				Constants.TARGET_FRAMERATE, (int)Constants.SCREEN_WIDTH, (int)Constants.SCREEN_HEIGHT, (float)scaleGraphicsX, (float)scaleGraphicsY));


		keyboardInputQueue = new LinkedList<>();
		mouseInputQueue = new LinkedList<>();

		mouseX = Constants.SCREEN_WIDTH/2;
		mouseY = Constants.SCREEN_HEIGHT/2;

		screenRect = new ModelToViewRectangle(Constants.DEFAULT_WORLD_VIEW_X, Constants.DEFAULT_WORLD_VIEW_Y, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

		//Add player character.
		Character pc;
		do{
			pc = gameModel.addCharacter(20, 20);
		} while(pc == null);
		pc.setKey(Constants.PLAYER_CHARACTER_KEY);
		player = new PlayerBrain(pc);
		pc.godMode = true;

		//Add AI characters.
		Random r = new Random();

		for(int i = 0; i < Constants.NUMBER_OF_NPCS; i++) {
			Character character = gameModel.addCharacter(r.nextInt((int)Constants.WORLD_WIDTH), r.nextInt((int)Constants.WORLD_HEIGHT));
			if(character != null) {
				aiMap.put(character, new ArtificialBrain(gameModel, character));
			}else {
				i--;
			}
		}

		currentCharacter = gameModel.getCharacterList().get(characterIndex);

	}

//-----------------------------------------Initialization methods-----------------------------------------------------\\

	private void setView(StateViewInit view){
		gameView = view;
		gameView.addPropertyChangeListener(this);
	}

	public void setModel(World model){
		gameModel = model;
	}

//----------------------------------------------Run methods-----------------------------------------------------------\\



	public void start() {
		this.gameView.run();
	}

	Timer timer;

	private void runModel(){
		timer = new Timer();

		TimerTask task = new TimerTask() {
			public void run() {
				update();
			}
		};

		timer.scheduleAtFixedRate(task, 0, 1000 / gameSpeed);
	}

	private boolean paused = false;
	public void update(){
		updateInput();

		if(!paused) {
			player.update();
			for (AbstractBrain brain : aiMap.values()) {
				brain.update();
			}
			gameModel.update();
			updateView();
			aiCleanup();
		}
	}

	public void aiCleanup() {
		LinkedList<Character> removeList = new LinkedList<>();
		Iterator ais = aiMap.entrySet().iterator();
		while (ais.hasNext()) {
			Character c = (Character) ((Map.Entry)ais.next()).getKey();
			if (!gameModel.getCharacterList().contains(aiMap.get(c).getBody())) {
				removeList.add(c);
			}
			//ais.remove();
		}
		for (Character c2 : removeList) {
			aiMap.remove(c2);
		}
	}

	/**
	 * Update various elements of the view, such as
	 * the saved location of the screen-view in regards to the world,
	 * acquire ALL renderable objects from the model and filter out the ones
	 * not visible in the screen-view and send these objects to the view.
	 */
	private void updateView(){
		List<RenderObject> temp = new LinkedList<>();
		gameView.drawNeeds(currentCharacter.getNeeds());


		int width = (int)gameModel.getWidth()+Constants.VIEW_BORDER_WIDTH*2;
		int height = (int)gameModel.getHeight()+Constants.VIEW_BORDER_HEIGHT*2;

		playerViewCentered = !gameModel.getCharacterList().isEmpty();

		//Centers the player in the middle of the screen
		if(playerViewCentered) {
			if (!gameModel.isPaused()){
				if(gameModel.getCharacterList().size() > 0) {
					playerXPos = currentCharacter.getX() + Constants.VIEW_BORDER_WIDTH;
					playerYPos = currentCharacter.getY() + Constants.VIEW_BORDER_HEIGHT;
					if(!currentCharacter.equals(player.getBody()) && currentCharacter.isAlive()){

						System.out.println("Current state: " + ((ArtificialBrain)aiMap.get(currentCharacter)).getCurrentState());
						System.out.println();
						System.out.println("State stack:");
						((ArtificialBrain)aiMap.get(currentCharacter)).getStateQueue().stream()
								.forEach(o -> System.out.print("\t" + o));
						System.out.println("\nGather stack:");
						((ArtificialBrain)aiMap.get(currentCharacter)).getGatherStack().stream()
								.forEach(o -> System.out.print("\t" + o));
						/*System.out.println("\nPath stack:\n");
						((ArtificialBrain)aiMap.get(currentCharacter)).getPathStack().stream()
								.forEach(o -> System.out.print("\t" + o));*/
						System.out.println("\nBuild stack: " +
						((ArtificialBrain)aiMap.get(currentCharacter)).getStructureStack());
						System.out.println("Resource to find: " + ((ArtificialBrain)aiMap.get(currentCharacter)).getResourceToFindStack());
						/*System.out.println("FARM?: " + ((ArtificialBrain) aiMap.get(currentCharacter)).getStructureMemory().contains(IStructure.StructureType.FARM));*/

					}
				}



				if (playerXPos - Constants.SCREEN_WIDTH / (2 * scaleGraphicsX) > 0) {
					if (playerXPos + Constants.SCREEN_WIDTH / (2 * scaleGraphicsX) < width) {
						screenRect.setMinX(playerXPos - Constants.SCREEN_WIDTH / (2 * scaleGraphicsX));
					} else {
						screenRect.setMaxX(width);
					}
				} else {
					screenRect.setMinX(0);
				}

				if (playerYPos - Constants.SCREEN_HEIGHT / (2 * scaleGraphicsY) > 0) {
					if (playerYPos + Constants.SCREEN_HEIGHT / (2 * scaleGraphicsY) < height) {
						screenRect.setMinY(playerYPos - Constants.SCREEN_HEIGHT / (2 * scaleGraphicsY));
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
			double[] tempDoubles = convertFromModelToViewCoords(tempObj.getX(), tempObj.getY());
			temp.add(new RenderObject(tempDoubles[0], tempDoubles[1], tempObj.getRadius(), tempObj.getRenderType()));
		}

		gameView.setRenderPoint(screenRect.getMinX(), screenRect.getMinY());

		if (temp.size() > 0) {
			gameView.drawRenderObjects(temp);
		}

		if (showingPlayerInventory) {
			gameView.drawInventory(currentCharacter.getRenderInventory());
			gameView.setCharacterName(currentCharacter.getName());
			gameView.setCharacterAge(currentCharacter.getAge());
		}

	}

	/**
	 * Uses input from the View to manipulate the Model.
	 */
	private void updateInput(){
		Object[] tempList = keyboardInputQueue.toArray();
		keyboardInputQueue.clear();

		if (tempList.length > 0) {
			Integer[][] tempKeyList = Arrays.copyOf(tempList, tempList.length, Integer[][].class);
			handleKeyboardInput(tempKeyList);
		}

		tempList = mouseInputQueue.toArray();
		mouseInputQueue.clear();

		if (tempList.length > 0) {
			Integer[][] tempMouseList = Arrays.copyOf(tempList, tempList.length, Integer[][].class);
			handleMouseInput(tempMouseList);
		}
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
					this.handleKeyPressed(clicks[1]);
				}else if(clicks[0] == View.INPUT_ENUM.KEY_RELEASED.value){
					this.handleKeyReleased(clicks[1]);
				}
			}
		}
	}

	private void handleKeyPressed(int pressedKey){
		switch (pressedKey) {
			case Input.KEY_X:
				this.gameModel.setShowingCurrentActivity();
				break;
			case Input.KEY_P:
				this.paused = !paused;
				gameView.displayPause();
				break;
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
			case Input.KEY_TAB:
				swapHighlightedCharacter();
				break;
			case Input.KEY_0:
				highlightPlayerCharacter();
				break;
			case Input.KEY_R:
				player.playerRunning();
				break;
			case Input.KEY_Q:
				if(!paused)
					player.attack();
				break;
			case Input.KEY_W:
				if(!paused)
					player.interact();
				break;
			case Input.KEY_E:
				if(!paused)
					player.consume();
				break;
			case Input.KEY_V:
				if(showingBuildOptions){
					gameView.displayBuildingInProcess();
					showingBuildOptions = false;
				}else if(showingPlayerInventory){
					showingPlayerInventory = false;
					gameView.hidePlayerInventory();
					gameView.highlightInventoryItem(0);
					itemHighlighted = -1;
				}
				playerViewCentered = !playerViewCentered;
				break;
			case Input.KEY_I:
				if(playerViewCentered && !paused && !showingBuildOptions) {
					showingPlayerInventory = !showingPlayerInventory;
					if (showingPlayerInventory) {
						gameView.drawInventory(gameModel.displayPlayerInventory());
					} else {
						gameView.hidePlayerInventory();
						gameView.highlightInventoryItem(0);
						itemHighlighted = -1;
					}
				}
				break;
			case Input.KEY_C:
				if(playerViewCentered && !paused){
					if(!showingPlayerInventory){
						gameView.displayBuildingInProcess();
						showingBuildOptions = !showingBuildOptions;
					}else if(itemHighlighted >= 0) {
						player.getBody().consumeItem(itemHighlighted);
					}
				}
				break;
			case Input.KEY_D:
				if (playerViewCentered && showingPlayerInventory && itemHighlighted >= 0 && !paused) {
					player.getBody().getInventory().remove(itemHighlighted);
					itemHighlighted = -1;
				}
				break;
			case Input.KEY_B:
				if (playerViewCentered && showingPlayerInventory && itemHighlighted >= 0 && !paused) {
					itemHighlighted = -1;
					gameView.highlightInventoryItem(0);
				}
				break;
			case Input.KEY_H:
				gameView.toggleShowCommands();
				break;
			case Input.KEY_S:
				gameView.toggleShowStats();
				break;
			case Input.KEY_1:
				handleNumberPressed(1);
				break;
			case Input.KEY_2:
				handleNumberPressed(2);
				break;
			case Input.KEY_3:
				handleNumberPressed(3);
				break;
			case Input.KEY_4:
				handleNumberPressed(4);
				break;
			case Input.KEY_5:
				handleNumberPressed(5);
				break;
			case Input.KEY_6:
				handleNumberPressed(6);
				break;
			case Input.KEY_7:
				handleNumberPressed(7);
				break;
			case Input.KEY_8:
				handleNumberPressed(8);
				break;
			case Input.KEY_9:
				handleNumberPressed(9);
				break;
			case Input.KEY_F1:
				changeSpeed(Constants.CONTROLLER_UPDATE_INTERVAL_NORMAL);
				break;
			case Input.KEY_F2:
				changeSpeed(Constants.CONTROLLER_UPDATE_INTERVAL_FASTER);
				break;
			case Input.KEY_F3:
				changeSpeed(Constants.CONTROLLER_UPDATE_INTERVAL_FASTEST);
				break;
		}

	}

	private void handleKeyReleased(int releasedKey){
		switch (releasedKey){
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
		}
	}

	private void swapHighlightedCharacter(){
		if(characterIndex >= gameModel.getCharacterList().size()-1) {
			characterIndex = 0;
		}else if(gameModel.getCharacterList().size()>0){
			characterIndex++;
		}
		if(gameModel.getCharacterList().size()>0)
			currentCharacter = gameModel.getCharacterList().get(characterIndex);
	}

	private void highlightPlayerCharacter(){
		if(((Character)player.getBody()).isAlive()) {
			characterIndex = Constants.PLAYER_CHARACTER_KEY;
			currentCharacter = gameModel.getCharacterList().get(characterIndex);
		}
	}

	private void changeSpeed(int updateInterval){
		if(gameSpeed != updateInterval) {
			timer.cancel();
			timer = new Timer();

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					update();
				}
			};

			gameSpeed = updateInterval;
			timer.scheduleAtFixedRate(task, updateInterval, 1000 / gameSpeed);
		}
	}

	private void handleNumberPressed(int number){
		if(playerViewCentered && !paused){
			if(showingPlayerInventory && player.getBody().getInventory().size() >= number){
				gameView.highlightInventoryItem(number);
				itemHighlighted = number-1;
			}else if(showingBuildOptions && number < 4){
				player.build(number);
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
						double[] tempDoubles = convertFromViewToModelCoords((clicks[2]/Constants.GRAPHICS_SCALE_X)/Constants.ZOOM_LEVEL, (clicks[3]/Constants.GRAPHICS_SCALE_Y)/Constants.ZOOM_LEVEL);
						player.moveToMouse(tempDoubles[0], tempDoubles[1]);
					}

					if(clicks[1] == Input.MOUSE_RIGHT_BUTTON){
						//The right mouse button was pressed.
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
				} else if(clicks[0] == View.INPUT_ENUM.MOUSE_WHEEL_MOVED.value){
					this.changeZoom(clicks[1]);
				}
			}
		}
	}

	private void changeZoom(int value) {
		double zoom;
		if(value>0){
			zoom = Constants.ZOOM_LEVEL + 0.1;
		}else {
			zoom = Constants.ZOOM_LEVEL - 0.1;
		}
		System.out.println(zoom);
		if(zoom >= 0.2 && zoom < 2) {
			Constants.ZOOM_LEVEL = zoom;
			scaleGraphicsX = Constants.SCREEN_WIDTH * Constants.ZOOM_LEVEL / Constants.STANDARD_SCREEN_WIDTH;
			scaleGraphicsY = Constants.SCREEN_HEIGHT * Constants.ZOOM_LEVEL / Constants.STANDARD_SCREEN_HEIGHT;
			gameView.setScale((float) scaleGraphicsX, (float) scaleGraphicsY);
		}
	}

//-------------------------------------Event handling methods---------------------------------------------------------\\

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(View.INPUT_ENUM.KEY_PRESSED.toString()) || evt.getPropertyName().equals(View.INPUT_ENUM.KEY_RELEASED.toString())) {
			// If the 'View' is sending 'Keyboard'-inputs, put them in the correct queue.
			Integer[] newValue = (Integer[]) evt.getNewValue();
			keyboardInputQueue.offer(newValue);
		}
		else if(evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_PRESSED.toString())
				|| evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_RELEASED.toString())
				|| evt.getPropertyName().equals(View.INPUT_ENUM.MOUSE_WHEEL_MOVED.toString())){
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

	private double[] convertFromModelToViewCoords(double x, double y){
		return new double[]{x - screenRect.getMinX() + Constants.VIEW_BORDER_WIDTH, y - screenRect.getMinY() + Constants.VIEW_BORDER_HEIGHT};
	}

	private double[] convertFromViewToModelCoords(double x, double y){
		return new double[]{x + screenRect.getMinX() - Constants.VIEW_BORDER_WIDTH, y + screenRect.getMinY() - Constants.VIEW_BORDER_HEIGHT};
	}

	private final class ModelToViewRectangle{
		double rectWidth, rectHeight;

		double minX, minY, maxX, maxY, scale;

		ModelToViewRectangle(double x, double y, double width, double height){
			rectWidth = width/scaleGraphicsX;
			rectHeight = height/scaleGraphicsY;

			minX = x/scaleGraphicsX;
			minY = y/scaleGraphicsY;
			maxX = (x + width)/scaleGraphicsX;
			maxY = (y + height)/scaleGraphicsY;
		}

		public void translatePosition(double deltaX, double deltaY){
			minX += deltaX;
			minY += deltaY;
			maxX += deltaX;
			maxY += deltaY;
		}

		public boolean contains(double x, double y){
			return x >= minX && x <= maxX && y >= minY && y <= maxY;
		}

		public double getMinX() {
			return minX;
		}

		public double getMinY() {
			return minY;
		}

		public double getMaxX() {
			return maxX;
		}

		public double getMaxY() {
			return maxY;
		}

		public double getWidth() {
			return rectWidth;
		}

		public double getHeight() {
			return rectHeight;
		}


		public void setMinX(double minX) {
			this.maxX += (minX - this.minX);
			this.minX = minX;
		}

		public void setMinY(double minY) {
			this.maxY += (minY - this.minY);
			this.minY = minY;
		}

		public void setMaxX(double maxX) {
			this.minX += (maxX - this.maxX);
			this.maxX = maxX;
		}

		public void setMaxY(double maxY) {
			this.minY += (maxY - this.maxY);
			this.maxY = maxY;
		}
	}

}
