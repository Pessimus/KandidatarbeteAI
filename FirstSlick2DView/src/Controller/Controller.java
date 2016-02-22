package Controller;

import Model.World;
import TestView.Toolkit;
import TestView.View;
import org.lwjgl.util.Dimension;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobias on 2016-02-20.
 */
public class Controller implements ActionListener{
	private World model;
	private View view;

	public static void main(String[] args){
		World world = new World(1920, 1080);
		new Controller(world, new View("Pathfind test", world));
	}

	public Controller(World model, View view){
		this.model = model;
		this.view = view;

		try{
			Dimension screen = Toolkit.getScreenDimension();
			AppGameContainer container = new AppGameContainer(view, screen.getWidth(), screen.getHeight(), true);

			container.start();
		}
		catch(SlickException e){
			e.printStackTrace();
			Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		;
	}
}
