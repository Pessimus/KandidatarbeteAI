package View;

import Model.RenderObject;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.GLUtils;
import org.newdawn.slick.state.StateBasedGame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.RenderObject.*;

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
	 * @param fullscreen Wether the game should uppdate in fullscreen/windowed mode
	 * @param grabMouse Wether the mouse
	 * @param targetFramerate
	 * @param resWidth
	 * @param resHeight
	 */
	public StateViewInit(String title, boolean fullscreen, boolean grabMouse, int targetFramerate, int resWidth, int resHeight, float scale){
		super(title);

		this.view = new View(PLAY_STATE, scale);

		this.addState(this.view);

		try{
			gameContainer = new AppGameContainer(this, resWidth, resHeight, fullscreen);
			gameContainer.setMouseGrabbed(grabMouse);
			gameContainer.setTargetFrameRate(targetFramerate);
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

	public void setRenderPoint(float x, float y){
		view.setRenderPoint(x, y);
	}

	public void drawNeeds(int[] needs){
		view.setPlayerNeeds(needs.clone());
	}

	public void drawInventory(LinkedList<Model.InventoryRender> inventoryItems){
		view.renderInventory(inventoryItems);
	}

	public void hidePlayerInventory(){
		view.hideInventory();
	}
}
