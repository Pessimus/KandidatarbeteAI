package View;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Martin on 03/03/2016.
 */


public class StateViewInit  extends StateBasedGame {

	private View view;

	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 1;


	public StateViewInit(String title){
		super(title);

		this.view = new View(PLAY_STATE);

		this.addState(this.view);

		try{
			//MainTesting game = new MainTesting("test");
			AppGameContainer test = new AppGameContainer(this, 1366, 768, false);
			test.setMouseGrabbed(true);
			test.setTargetFrameRate(60);
			test.start();

		}
		catch(SlickException e){
			//e.printStackTrace();
			Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, e);
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

	}
}
