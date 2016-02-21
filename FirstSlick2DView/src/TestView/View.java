package TestView;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobias on 2016-02-14.
 */
public class View extends BasicGame {

	Image character;
	int charX;
	int charY;
	Dimension d;

	public static void main(String[] args){
		try{
			View game = new View("Test");
			Dimension screen = Toolkit.getScreenDimension();
			AppGameContainer view = new AppGameContainer(game, screen.getWidth(), screen.getHeight(), true);

			view.start();
		}
		catch(SlickException e){
			e.printStackTrace();
			Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public View(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		character = new Image("res/Villager16x16.png");
		d = Toolkit.getScreenDimension();
		charX = d.getWidth()/2;
		charY = d.getHeight()/2;
	}



	@Override
	public void update(GameContainer gameContainer, int i) throws SlickException {
		Input in = gameContainer.getInput();

		System.out.println(charX);
		System.out.println(d.getWidth());

		if (in.isKeyDown(Input.KEY_LEFT) && charX > 0)
			charX -= 1;
		if (in.isKeyDown(Input.KEY_RIGHT) && charX < d.getWidth() - character.getWidth())
			charX += 1;

		if (in.isKeyDown(Input.KEY_UP) && charY > 0)
			charY -= 1;
		if (in.isKeyDown(Input.KEY_DOWN) && charY < d.getHeight() - character.getHeight())
			charY += 1;
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		character.draw(charX, charY);
	}
}