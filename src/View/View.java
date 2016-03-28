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

    private volatile float scaleGraphics = 1f;

    //List<RenderObject> listToRender = new LinkedList<>();
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

    public View(int i) {
        stateNr = i;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TiledMap("res/mapSquare.tmx");       //controller.getTiledMap();

		for(RenderObject.RENDER_OBJECT_ENUM e : RenderObject.RENDER_OBJECT_ENUM.values()){
			resourceMap.put(e, new Image(e.pathToResource));
		}

		for(Model.IItem.Type e : Model.IItem.Type.values()){
			inventoryMap.put(e, new Image(e.pathToResource));
		}

		pcs.firePropertyChange("startController", false, true);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		tempWidth = (int)Math.ceil(Constants.SCREEN_WIDTH/Constants.WORLD_TILE_SIZE/scaleGraphics);
		tempHeight = (int)Math.ceil(Constants.SCREEN_HEIGHT/Constants.WORLD_TILE_SIZE/scaleGraphics);
    }


    public void zoomOut(){
        if(scaleGraphics > 0.5f)
           scaleGraphics -= 0.1f;
    }

    public void zoomIn(){
        if(scaleGraphics < 3f){
            scaleGraphics += 0.1f;
        }
    }



	private int tempWidth;
	private int tempHeight;

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.scale(scaleGraphics,scaleGraphics);

		try {
			//renderPointSema.acquire();
			map.render(0,0, renderPointX/Constants.WORLD_TILE_SIZE, renderPointY/Constants.WORLD_TILE_SIZE, tempWidth, tempHeight);
			//map.render(0, 0, renderPointX/Constants.WORLD_TILE_SIZE, renderPointY/Constants.WORLD_TILE_SIZE, width, height);
			//renderPointSema.release();

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
				graphics.drawRect((int)(gameContainer.getWidth()/scaleGraphics)-64*3, (int)(gameContainer.getHeight()/scaleGraphics)-224, 128,32);
				graphics.drawString("Inventory", (int)(gameContainer.getWidth()/scaleGraphics)-182, (int)(gameContainer.getHeight()/scaleGraphics)-218);
				for (int i = 1; i < 4; i++) {
					for (int j = 1; j < 4; j++) {
						x=(int)(gameContainer.getWidth()/scaleGraphics)-64*i;
						y=(int)(gameContainer.getHeight()/scaleGraphics)-64*j;
						graphics.setLineWidth(5f);
						graphics.drawRect(x, y, 64, 64);
					}
				}

				int i,j;
				i=3;
				j=3;
				for(Model.InventoryRender invRender : inventoryToRender) {
					x=(int)(gameContainer.getWidth()/scaleGraphics)-64*i;
					y=(int)(gameContainer.getHeight()/scaleGraphics)-64*j;

					graphics.drawImage(new Image(invRender.type.pathToResource), x, y);
					graphics.fillRect(x+44, y+44, 20, 20);
					graphics.setColor(Color.black);
					if(invRender.amount < 10)
						graphics.drawString(Integer.toString(invRender.amount), x+48, y+48);
					else
						graphics.drawString(Integer.toString(invRender.amount), x+44, y+46);
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

		/*
        //Functioanlity for moving the camera view around the map. Keep the mouse to one side to move the camera view.
        if (Mouse.getX() > d.getWidth()-d.getWidth()/10 && renderpointx < map.getWidth()-width) {
            renderpointx += 1;
        }
        if (Mouse.getX() < d.getWidth()/10 && renderpointx > 0) {
            renderpointx -= 1;
        }
        if (Mouse.getY() < d.getHeight()-d.getHeight()/10 && renderpointy < map.getHeight()-height) {
            renderpointy += 1;
        }
        if (Mouse.getY() > d.getHeight()/10 && renderpointy > 0) {
            renderpointy -= 1;
        }


        if(Keyboard.isKeyDown(Input.KEY_ADD) || Keyboard.isKeyDown(Input.KEY_Z)) {
            zoomIn();
        }

        if(Keyboard.isKeyDown(Input.KEY_SUBTRACT)|| Keyboard.isKeyDown(Input.KEY_X)) {
            zoomOut();
        }

        if(Mouse.getEventDWheel() > 0)
            zoomIn();

        if(Mouse.getEventDWheel() < 0)
            zoomOut();
        */


		//listToRender = null;
		//tempRenderList = null;

	}

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
