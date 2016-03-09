package View;

import Model.RenderObject;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import javax.imageio.ImageIO;

/**
 * Created by Oskar on 2016-02-26.
 */
public class View extends BasicGameState implements InputListener{

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Input input;
    private int stateNr;
    private TiledMap map;
    private float scaleX, scaleY;
    List<RenderObject> listToRender = new LinkedList<>();

	private Semaphore semaphore = new Semaphore(1);

    public enum INPUT_ENUM {
		KEY_RELEASED(0), KEY_PRESSED(1),
		MOUSE_RELEASED(0), MOUSE_PRESSED(1), MOUSE_MOVED(2);

        public int value;
        //String

        INPUT_ENUM(int i){
            value = i;
        }
    }

    //----------TESTVARIABLER UNDER, Ska kanske inte vara kvar ------------------
    int mouseX = 0;
    int mouseY = 0;
    int mouseXMoved = 0;
    int mouseYMoved = 0;

    int collisionId = 21*23+1;

	// Since all images needs to be initialized in the 'init()' method,
	// they are stored in this map.
	private Map<RenderObject.RENDER_OBJECT_ENUM, Image> resourceMap = new HashMap<>();

    public View(int i) {
        stateNr = i;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TiledMap("res/mapsquare.tmx");       //controller.getTiledMap();

		for(RenderObject.RENDER_OBJECT_ENUM e : RenderObject.RENDER_OBJECT_ENUM.values()){
			resourceMap.put(e, new Image(e.pathToResource));
		}
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        scaleX = gameContainer.getScreenWidth()/(50*32);
        scaleY = gameContainer.getScreenWidth()/(40*32);
        graphics.scale(scaleX,scaleY);
        map.render(0,0, mouseX/32,mouseY/32,50,40);

		Object[] tempList = null;

		// Retrieve the 'listToRender' list as an array
		try {
			semaphore.acquire();
			tempList = listToRender.toArray();
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}

		if(tempList != null){
			if(tempList.length > 0){
				RenderObject[] renderList = Arrays.copyOf(tempList, tempList.length, RenderObject[].class);
				for (RenderObject obj: renderList) {
					resourceMap.get(obj.objectType).draw(obj.xPos, obj.yPos);
				}
			}
		}

		try {
			semaphore.acquire();
			listToRender.clear();
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}
    }

    @Override
    public int getID() {

        return stateNr;
    }
    @Override
    public void keyPressed(int key, char c) {
        //notifyKeyInput(new Integer[]{INPUT_ENUM.KEY_PRESSED.value, key});
		pcs.firePropertyChange(INPUT_ENUM.KEY_PRESSED.toString(), 0, new Integer[]{INPUT_ENUM.KEY_PRESSED.value, key});
    }

    @Override
    public void keyReleased(int key, char c){
        //notifyKeyInput(new Integer[]{INPUT_ENUM.KEY_RELEASED.value, key});
		pcs.firePropertyChange(INPUT_ENUM.KEY_RELEASED.toString(), 0, new Integer[]{INPUT_ENUM.KEY_RELEASED.value, key});
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy){   //Ska denna flyttas till modell?
        mouseXMoved = newx-oldx;
        mouseYMoved = newy-oldy;
        mouseX+=mouseXMoved;
        mouseY+=mouseYMoved;
		//pcs.firePropertyChange(INPUT_ENUM.MOUSE_MOVED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_MOVED.value,oldx, oldy, newx, newy});
    }

    @Override
    public void mousePressed(int button, int x, int y){
        //notifyMouseInput(new Integer[]{INPUT_ENUM.MOUSE_PRESSED.value, button, x, y});
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_PRESSED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_PRESSED.value, button, (int)(x/scaleX), (int)(y/scaleY)});
    }

	@Override
	public void mouseReleased(int button, int x, int y){
		//notifyMouseInput(new Integer[]{INPUT_ENUM.MOUSE_PRESSED.value, button, x, y});
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_RELEASED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_RELEASED.value, button, (int)(x/scaleX), (int)(y/scaleY)});
	}

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }


	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	public void addRenderObject(RenderObject obj){
		listToRender.add(obj);
	}
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!
	// TODO: HARDCODED TEST!!!!!



	// TODO: Maybe remove these if the above code is ok.
    private void notifyKeyInput(Integer[] vars){   // control = "KEY_PRESSED" eller "KEY_RELEASED"
        pcs.firePropertyChange(vars[0].toString(), 0, input);
    }
    private void notifyMouseInput(Integer[] vars){
        pcs.firePropertyChange(vars[0].toString(), 0, vars);
    }
}
