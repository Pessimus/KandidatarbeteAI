package View;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import sun.applet.Main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Oskar on 2016-03-01.
 */
public class MainTesting extends StateBasedGame {

    public static final int MENU_STATE = 0;
    public static final int PLAY_STATE = 1;

    public static void main(String[] args){
        try{
            MainTesting game = new MainTesting("test");
            AppGameContainer test = new AppGameContainer(game, 1366, 768, true);
            test.setTargetFrameRate(60);
            test.start();

        }
        catch(SlickException e){
            //e.printStackTrace();
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, e);
        }
    }  //Main initierar view

    public MainTesting(String title) {
        super(title);
        this.addState(new View(PLAY_STATE));
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        //this.getState(MENU_STATE).init(gameContainer, this);
        this.getState(PLAY_STATE).init(gameContainer, this);
        this.enterState(PLAY_STATE);
    }
}
