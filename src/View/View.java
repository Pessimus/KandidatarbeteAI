package View;

import Model.RenderObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
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
    private Input input;
    private int stateNr;
    private TiledMap map;
    private int renderpointx = 50;
    private int renderpointy = 50;
    java.awt.Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    private float scaler = 3f;
    List<RenderObject> listToRender = new LinkedList<>();


	private final Semaphore semaphore = new Semaphore(1);
	private final Map<RenderObject.RENDER_OBJECT_ENUM, Image> resourceMap = new HashMap<>();

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
    int wheel = Mouse.getDWheel();

    int collisionId = 21*23+1;

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

	RenderObject[] tempRenderList = null;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		Object[] tempList = null;

		try {
			semaphore.acquire();
			tempList = listToRender.toArray();
			listToRender.clear();
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
            System.out.println(e);
		}

		if(tempList != null){
			if(tempList.length > 0){
				tempRenderList = Arrays.copyOf(tempList, tempList.length, RenderObject[].class);
			}
		}
    }


    public void zoomOut(){
        if(scaler > 0.5f)
           scaler -= 0.05f;
    }

    public void zoomIn(){
        if(scaler < 3f){
            scaler += 0.05f;
        }
    }


    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        int width = (int)Math.ceil(gameContainer.getScreenWidth()/map.getTileWidth()/scaler);
        int height = (int)Math.ceil(gameContainer.getScreenHeight()/map.getTileWidth()/scaler);
        graphics.scale(scaler,scaler);
       // map.render(0,0, mouseX/32,mouseY/32,50,40);
        map.render(0,0, renderpointx, renderpointy, width, height);

        //map.render(0, 0, renderpointx, renderpointy, 50, 40);

		if(tempRenderList != null){

			for (RenderObject obj: tempRenderList) {
				resourceMap.get(obj.getObjectType()).draw(obj.getX(), obj.getY());
			}
		}
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

        //Functionality for zooming in and out
        if(Keyboard.isKeyDown(Input.KEY_ADD) || Keyboard.isKeyDown(Input.KEY_Z)) {
            zoomIn();
        }

        if(Keyboard.isKeyDown(Input.KEY_SUBTRACT)|| Keyboard.isKeyDown(Input.KEY_X)) {
            zoomOut();
        }



        //System.out.println(Keyboard.getKeyIndex("+"));

		tempRenderList = null;

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
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_PRESSED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_PRESSED.value, button, x, y});
    }

	@Override
	public void mouseReleased(int button, int x, int y){
		//notifyMouseInput(new Integer[]{INPUT_ENUM.MOUSE_PRESSED.value, button, x, y});
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_RELEASED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_RELEASED.value, button, x, y});
	}

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }

	public boolean addRenderObject(RenderObject obj){
		boolean returns = false;
		try {
			semaphore.acquire();
			returns = listToRender.add(obj);
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}

		return returns;
	}

	// TODO: Maybe remove these if the above code is ok.
    private void notifyKeyInput(Integer[] vars){   // control = "KEY_PRESSED" eller "KEY_RELEASED"
        pcs.firePropertyChange(vars[0].toString(), 0, vars);
    }
    private void notifyMouseInput(Integer[] vars){
        pcs.firePropertyChange(vars[0].toString(), 0, vars);
    }
}
