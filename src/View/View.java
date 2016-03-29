package View;

import Model.Constants;
import Model.RenderObject;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Oskar on 2016-02-26.
 */
public class View extends BasicGameState implements InputListener{

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private int stateNr;
    private TiledMap map;

	private volatile int renderPointX = (int)Constants.DEFAULT_WORLD_VIEW_X;
	private volatile int renderPointY = (int)Constants.DEFAULT_WORLD_VIEW_Y;

    private volatile float scaleGraphics;
	private int tempWidth;
	private int tempHeight;

	private RenderObject[] listToRender = {};
	private Model.InventoryRender[] inventoryToRender = {};

	private boolean displayInventory = false;

	private final Semaphore semaphore = new Semaphore(1);
	private final Map<RenderObject.RENDER_OBJECT_ENUM, Image> resourceMap = new HashMap<>();
	private final Map<Model.IItem.Type, Image> inventoryMap = new HashMap<>();

    public enum INPUT_ENUM {
		KEY_RELEASED(0), KEY_PRESSED(1),
		MOUSE_RELEASED(0), MOUSE_PRESSED(1), MOUSE_MOVED(2),
		MOUSE_WHEEL_MOVED(0);

        public int value;
        //String

        INPUT_ENUM(int i){
            value = i;
        }
    }

    public View(int i, float scale) {
        stateNr = i;
		scaleGraphics = scale;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TiledMap("res/mapSquare.tmx");

		//Link together all render-objects with a specific image
		for(RenderObject.RENDER_OBJECT_ENUM e : RenderObject.RENDER_OBJECT_ENUM.values()){
			resourceMap.put(e, new Image(e.pathToResource));
		}

		//Link together all IItem-enums with a specific image
		for(Model.IItem.Type e : Model.IItem.Type.values()){
			inventoryMap.put(e, new Image(e.pathToResource));
		}

		pcs.firePropertyChange("startController", false, true);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		tempWidth = (int)Math.ceil(Constants.SCREEN_WIDTH/Constants.WORLD_TILE_SIZE);
		tempHeight = (int)Math.ceil(Constants.SCREEN_HEIGHT/Constants.WORLD_TILE_SIZE);
    }

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.scale(scaleGraphics,scaleGraphics);

		try {
			map.render(0,0, renderPointX/Constants.WORLD_TILE_SIZE, renderPointY/Constants.WORLD_TILE_SIZE, tempWidth, tempHeight);

			semaphore.acquire();
			if(listToRender != null){
				if(listToRender.length > 0) {
					for (RenderObject obj : listToRender) {
						resourceMap.get(obj.getRenderType()).draw(obj.getX(), obj.getY());
					}
				}
			}


			if (displayInventory) {
				int x,y;
				for (int i = 1; i < Math.sqrt(Constants.MAX_INVENTORY_SLOTS)+1; i++) {
					for (int j = 1; j < Math.sqrt(Constants.MAX_INVENTORY_SLOTS)+1; j++) {
						x=(int)(gameContainer.getWidth()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*i;
						y=(int)(gameContainer.getHeight()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*j;
						graphics.setLineWidth(5f);
						graphics.drawRect(x, y, Constants.SLOT_DISPLAY_SIZE, Constants.SLOT_DISPLAY_SIZE);
					}
				}

				int i,j;
				i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
				j=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
				for(Model.InventoryRender invRender : inventoryToRender) {
					x=(int)(gameContainer.getWidth()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*i;
					y=(int)(gameContainer.getHeight()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*j;

					graphics.drawImage(new Image(invRender.type.pathToResource), x, y);
					graphics.fillRect(x+Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT, y+Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT,
							Constants.SLOT_DISPLAY_AMOUNT, Constants.SLOT_DISPLAY_AMOUNT);
					graphics.setColor(Color.black);
					if(invRender.amount < 10) {
						graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT+Constants.AMOUNT_DISPLAY_MARGIN,
								y + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT+Constants.AMOUNT_DISPLAY_MARGIN);
					}else {
						graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT,
								y + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT);
					}
					graphics.setColor(Color.white);
					i--;
					if(i==0 && j!=1){
						i=3;
						j--;
					}
					/*for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {


							graphics.fillRect(gameContainer.getWidth() - 30 - 64 * j, gameContainer.getHeight() - 30 - 64 * i, 30, 30);
							graphics.setColor(Color.black);
							graphics.drawString("3", gameContainer.getWidth() - 20 - 64 * j, gameContainer.getHeight() - 20 - 64 * i);
							graphics.setColor(Color.white);
						}
					}*/
				}
			}
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}
	}

	/*
	//Zoom function???

	public void zoomOut(){
		if(scaleGraphics > 0.5f)
			scaleGraphics -= 0.1f;
	}

	public void zoomIn(){
		if(scaleGraphics < 3f){
			scaleGraphics += 0.1f;
		}
	}
	*/

	//-----Getters and Setters
    @Override
    public int getID() {
        return stateNr;
    }

    @Override
    public void keyPressed(int key, char c) {
		pcs.firePropertyChange(INPUT_ENUM.KEY_PRESSED.toString(), 0, new Integer[]{INPUT_ENUM.KEY_PRESSED.value, key});

    }

    @Override
    public void keyReleased(int key, char c){
		pcs.firePropertyChange(INPUT_ENUM.KEY_RELEASED.toString(), 0, new Integer[]{INPUT_ENUM.KEY_RELEASED.value, key});
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_MOVED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_MOVED.value,oldx, oldy, newx, newy});
    }

    @Override
    public void mousePressed(int button, int x, int y){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_PRESSED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_PRESSED.value, button, x, y});
    }

	@Override
	public void mouseReleased(int button, int x, int y){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_RELEASED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_RELEASED.value, button, x, y});
	}

	@Override
	public void mouseWheelMoved(int var){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_WHEEL_MOVED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_WHEEL_MOVED.value, var});
	}

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }

	public void setRenderList(RenderObject[] objList){
		try {
			semaphore.acquire();
			listToRender = objList;
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}
	}

	public void setRenderPoint(float x, float y){
		renderPointX = (int) x;
		renderPointY = (int) y;
	}

	public void renderInventory(LinkedList<Model.InventoryRender> inventoryItems){
		LinkedList<Model.InventoryRender> tmp = new LinkedList<>();
		for(Model.InventoryRender inventoryRen : inventoryItems){
			tmp.add(inventoryRen);
		}

		inventoryToRender = tmp.toArray(new Model.InventoryRender[tmp.size()]);
		displayInventory = true;
	}

	public void hideInventory(){
		displayInventory = false;
	}
}
