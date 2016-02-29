package View;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by Oskar on 2016-02-26.
 */
// TODO: Discard this class when merging into 'master'!
public class View extends StateBasedGame{
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public View(String name){
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {

	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
}
