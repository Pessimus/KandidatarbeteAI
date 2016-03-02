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

    //----------TESTVARIABLER UNDER, Ska ej vara kvar ------------------
    int mouseX = 0;
    int mouseY = 0;

    public View(int i) {
        stateNr = i;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TiledMap("res/mapsquare.tmx");       //controller.getTiledMap();
        //addPropertyChangeListener();
    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        map.render(0,0, mouseX/32,mouseY/32,25,20);

        graphics.drawString((Integer.toString(mouseX)),100,100);
        graphics.drawString((Integer.toString(mouseY)),200,100);
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

        //notifyPropertyChangeListener(key);
    }

    //public void keyReleased(int key, char c){
    //}

    public void mouseMoved(int oldx, int oldy, int newx, int newy){
        if(oldx>1360){
            mouseX = newx + 1360;
        }
        else if(oldx<1370){
            mouseX = newx - 1360;
        }
        else{
            mouseX = newx;
        }
        if(oldy>760){
            mouseY = newy + 760;
        }
        else{
            mouseY = newy;
        }

    }

    public void addPropertyChangeListener(PropertyChangeListener listener){

        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }
    private void notifyPropertyChangeListener(int key){
        pcs.firePropertyChange("newInput",0,key);
    }
}
