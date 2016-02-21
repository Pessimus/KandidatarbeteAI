package Model;

import org.lwjgl.util.Point;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * Created by Tobias on 2016-02-20.
 */
public class World extends AbstractAction{
	private Map<Point, ? extends WorldCell> worldMap;

	private static final int MAX_WORLD_WIDTH = 2000;
	private static final int MAX_WORLD_HEIGHT = 2000;

	private static final int DEFAULT_WORLD_WIDTH = 100;
	private static final int DEFAULT_WORLD_HEIGHT = 100;

	private int worldWidth;
	private int worldHeight;

	public World(){
		worldHeight = DEFAULT_WORLD_HEIGHT;
		worldWidth = DEFAULT_WORLD_WIDTH;
	}

	public World(int width, int height){
		worldWidth = width;
		worldHeight = height;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
