package Model;

import org.lwjgl.util.Point;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tobias on 2016-02-20.
 */
public class World <T extends WorldCell> extends AbstractAction{
	private Map<Point, T> worldMap;

	public static final int MAX_WORLD_WIDTH = 2000;
	public static final int MAX_WORLD_HEIGHT = 2000;

	public static final int DEFAULT_WORLD_WIDTH = 100;
	public static final int DEFAULT_WORLD_HEIGHT = 100;

	private int worldWidth;
	private int worldHeight;

	public World(){
		worldHeight = DEFAULT_WORLD_HEIGHT;
		worldWidth = DEFAULT_WORLD_WIDTH;

		worldMap = new HashMap<>();
	}

	public World(int width, int height){
		worldWidth = width;
		worldHeight = height;

		worldMap = new HashMap<>();
	}

	public Map<Point, ? extends WorldCell> getWorldMap() {
		return worldMap;
	}

	public void setWorldMap(Map<Point, T> worldMap) {
		this.worldMap = worldMap;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}


	@Override
	public void actionPerformed(ActionEvent e) {

	}


}
