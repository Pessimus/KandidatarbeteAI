package View;

import Utility.RenderObject;
import Utility.InventoryRender;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Martin on 03/03/2016.
 */


public class StateViewInit extends StateBasedGame implements Runnable {

	private View view;

	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 1;

	private AppGameContainer gameContainer;

	/**
	 * TODO: Javadoc!
	 * @param title What the title of the window should be
	 * @param fullscreen Wether the game should update in fullscreen/windowed mode
	 * @param grabMouse Wether the mouse
	 * @param targetFramerate
	 * @param resWidth
	 * @param resHeight
	 */
	public StateViewInit(String title, boolean fullscreen, boolean grabMouse, int targetFramerate, int resWidth, int resHeight, float scaleX, float scaleY){
		super(title);

		this.view = new View(PLAY_STATE, scaleX, scaleY);

		this.addState(this.view);

		try{
			gameContainer = new AppGameContainer(this, resWidth, resHeight, fullscreen);
			gameContainer.setMouseGrabbed(grabMouse);
			gameContainer.setTargetFrameRate(targetFramerate);
			gameContainer.setShowFPS(false);
		}
		catch(SlickException e){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Exception in StateViewInit constructor when creating AppGameContainer!", e);
		}

	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		this.view.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.view.removePropertyChangeListener(listener);
	}


	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		//this.getState(PLAY_STATE).init(gameContainer, this);
		this.enterState(PLAY_STATE);
	}

	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	//private RenderObject[] renderObjectList;
	public void drawRenderObjects(List<RenderObject> objectList) {
		/*
		for (RenderObject o : objectList) {
			view.addRenderObject(o);
		}
		*/
		RenderObject[] tempList = Arrays.copyOf(objectList.toArray(), objectList.toArray().length, RenderObject[].class);
		view.setRenderList(tempList);
	}
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!


	@Override
	public void run() {
		try {
			gameContainer.start();
		}
		catch (SlickException e){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "SlickException when starting the AppGameContainer in SlickViewInit!", e);
		}
	}

	public void setRenderPoint(double x, double y){
		view.setRenderPoint(x, y);
	}

	public void drawNeeds(int[] needs){
		view.setPlayerNeeds(needs.clone());
	}

	public void setCharacterName(String name){
		view.setCharacterName(name);
	}

	public void setCharacterAge(int age){
		view.setCharacterAge(age);
	}

	public void highlightInventoryItem(int index){
		view.setItemInFocus(index);
	}

	public void displayPause(){
		view.togglePause();
	}

	public void displayBuildingInProcess(){
		view.toggleBuildingInProcess();
	}


	public void drawInventory(LinkedList<InventoryRender> inventoryItems){
		view.renderInventory(inventoryItems);
	}

	public void hidePlayerInventory(){
		view.hideInventory();
	}

	public void toggleShowCommands() {
		view.toggleShowCommands();
	}

	public void toggleShowStats() {
		view.toggleShowStats();
	}

	public void setScale(float scaleX, float scaleY){
		view.setScale(scaleX,scaleY);
	}
}
