package View;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oskar on 2016-02-26.
 */
public class View extends BasicGameState implements InputListener{

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Input input;
    private int stateNr;
    private TiledMap map;
    List<Integer> listToRender;
    private boolean initMap = true;

    //----------TESTVARIABLER UNDER, Ska kanske inte vara kvar ------------------
    int mouseX = 0;
    int mouseY = 0;
    int mouseXMoved = 0;
    int mouseYMoved = 0;

    public View(int i) {
        stateNr = i;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TiledMap("res/mapsquare.tmx");       //controller.getTiledMap();
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        map.render(0,0, mouseX/32,mouseY/32,50,40);

        /*
        semaphore.acquire();
        List<> renderList = new LinkedList<>(listToRender);
        semaphore.release();

        for (obj: renderList) {

            if(obj.name == "tree"){
                graphics.drawImage(obj.x, obj.y, treeImage);
            }
            if(obj.name == "stone"){
                graphics.drawImage(obj.x, obj.y, treeImage);
            }
            if(obj.name == "character"){
                graphics.drawImage(obj.x, obj.y, treeImage);
            }
        }
        */
    }

    @Override
    public int getID() {

        return stateNr;
    }

    public void keyPressed(int key, char c) {

        notifyKeyInput(key,"KEY_PRESSED");
    }

    public void keyReleased(int key, char c){
        notifyKeyInput(key,"KEY_RELEASED");
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy){   //Ska denna flyttas till modell?
        mouseXMoved = newx-oldx;
        mouseYMoved = newy-oldy;
        mouseX+=mouseXMoved;
        mouseY+=mouseYMoved;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){

        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }
    private void notifyKeyInput(int input, String control){   // control = "KEY_PRESSED" eller "KEY_RELEASED"

        pcs.firePropertyChange(control,0,input);
    }
    //private void notifyMouseInput(){}
}
