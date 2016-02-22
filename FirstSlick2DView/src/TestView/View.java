package TestView;

import Model.Mountain;
import Model.Terrain;
import Model.World;
import Model.WorldCell;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.Point;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobias on 2016-02-14.
 */
public class View <T extends WorldCell> extends BasicGame {

	Image character;
	int charX;
	int charY;
	Dimension d;

	World model;

	Map<Point, T> worldMap;

	Image terrain, mountain;

	List<T> pathList;


	public View(String title, World model) {
		super(title);
		this.model = model;
	}

	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		character = new Image("res/Villager16x16.png");
		d = Toolkit.getScreenDimension();
		charX = d.getWidth()/2 + 8;
		charY = d.getHeight()/2 - 4;

		terrain = new Image("res/Grass16x16.png");
		mountain = new Image("res/Water16x16.png");
		mountain.setImageColor(0.1f, 0.1f, 0.1f);

		worldMap = model.getWorldMap();

		for(int x = 8; x < d.getWidth(); x+=16){
			for(int y = 8; y < d.getHeight(); y+=16){

				Point p = new Point(x, y);

				if(((y >= 200 && y <= 600 && x <= 800 && x >= 350)) ||
					(x == 328 && y <= 800 && y >= 700) ||
					(x == 648 && y <= 900 && y >= 200)) {
					worldMap.put(p, (T)new Mountain(x, y));
				} else{
					worldMap.put(p, (T)new Terrain(x, y));
				}
			}
		}

		pathList = AStarPathFinding(worldMap.get(new Point(charX, charY)), worldMap.get(new Point(72, 408)));

		if(pathList == null){
			System.out.println("Path null");
		}

		System.out.println(worldMap.get(new Point(charX + 16, charY)).getAStarHeuristicalCost());
	}


	@Override
	public void update(GameContainer gameContainer, int i) throws SlickException {
		Input in = gameContainer.getInput();

		/*
		if (in.isKeyDown(Input.KEY_LEFT) && charX > 0)
			charX -= 1;
		if (in.isKeyDown(Input.KEY_RIGHT) && charX < d.getWidth() - character.getWidth())
			charX += 1;

		if (in.isKeyDown(Input.KEY_UP) && charY > 0)
			charY -= 1;
		if (in.isKeyDown(Input.KEY_DOWN) && charY < d.getHeight() - character.getHeight())
			charY += 1;
		*/


	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		worldMap = model.getWorldMap();

		for(WorldCell cell : worldMap.values()) {
			if (pathList.contains(cell)){
				if (!cell.isTraversable()) {
					mountain.setImageColor(1f, 1f, 1f);
					mountain.draw(cell.getPosX(), cell.getPosY());
					mountain.setImageColor(0.1f, 0.1f, 0.1f);
				} else {
					terrain.setImageColor(1f, 1f, 1f);
					terrain.draw(cell.getPosX(), cell.getPosY());
					terrain.setImageColor(0f, 1f, 0f);
				}
			} else{
				if (!cell.isTraversable()) {
					mountain.draw(cell.getPosX(), cell.getPosY());
				} else {
					terrain.draw(cell.getPosX(), cell.getPosY());
				}
			}
		}

		character.draw(charX, charY);
	}

	PriorityQueue<T> open;

	boolean[][] closed = new boolean[1920][1080];

	private List<T> AStarPathFinding(T startCell, T endCell) {
		open = new PriorityQueue<>((Object o1, Object o2) -> {
			T c1 = (T) o1;
			T c2 = (T) o2;

			return c1.getAStarFinalCost() < c2.getAStarFinalCost() ? -1 :
					c1.getAStarFinalCost() > c2.getAStarFinalCost() ? 1 : 0;
		});

		//add the start location to open list.
		open.add((T)startCell);

		for (T cell : worldMap.values()) {
			if (cell != endCell) {
				int dx = cell.getPosX() - endCell.getPosX();
				int dy = cell.getPosY() - endCell.getPosY();
				cell.setAStarHeuristicalCost((int)(1000*Math.sqrt((dx * dx) + (dy * dy))));
			}
		}

		startCell.setAStarFinalCost(0);

		T current;

		while (true) {
			current = open.poll();
			if (current == null)
				break;

			closed[current.getPosX()][current.getPosY()] = true;

			if (current.equals(endCell)) {
				LinkedList<T> finalList = new LinkedList<>();

				if (closed[endCell.getPosX()][endCell.getPosY()]) {
					WorldCell temp = endCell;

					while (temp.getParentCell() != null) {
						finalList.addFirst((T)temp);
						temp = temp.getParentCell();
					}

					return finalList;
				}
			}

			WorldCell t;

			System.out.println("Node position: " + current.getPosX() + " : " + current.getPosY());
			if(current.isTraversable()) {
				if (current.getPosX() - 16 >= 0) {
					t = worldMap.get(new Point(current.getPosX() - 16, current.getPosY()));
					checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIRECT_COST);

					if (current.getPosY() - 16 >= 0) {
						t = worldMap.get(new Point(current.getPosX() - 16, current.getPosY() - 16));
						checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIAGONAL_COST);
					}

					if (current.getPosY() + 16 < Toolkit.getScreenDimension().getHeight()) {
						t = worldMap.get(new Point(current.getPosX() - 16, current.getPosY() + 16));
						checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIAGONAL_COST);
					}
				}

				if (current.getPosY() - 16 >= 0) {
					t = worldMap.get(new Point(current.getPosX(), current.getPosY() - 16));
					checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIRECT_COST);
				}

				if (current.getPosY() + 16 < Toolkit.getScreenDimension().getHeight()) {
					t = worldMap.get(new Point(current.getPosX(), current.getPosY() + 16));
					checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIRECT_COST);
				}

				if (current.getPosX() + 16 < Toolkit.getScreenDimension().getWidth()) {
					t = worldMap.get(new Point(current.getPosX() + 16, current.getPosY()));
					checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIRECT_COST);

					if (current.getPosY() - 16 >= 0) {
						t = worldMap.get(new Point(current.getPosX() + 16, current.getPosY() - 16));
						checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIAGONAL_COST);
					}

					if (current.getPosY() + 16 < Toolkit.getScreenDimension().getHeight()) {
						t = worldMap.get(new Point(current.getPosX(), current.getPosY() + 16));
						checkAndUpdateCost(current, t, current.getAStarFinalCost() + WorldCell.DIAGONAL_COST);
					}
				}
			}
		}

		return null;
	}

	private void checkAndUpdateCost(WorldCell current, WorldCell t, int cost){
		if(t == null || closed[t.getPosX()][t.getPosY()])
			return;

		int t_final_cost = t.getAStarHeuristicalCost() + cost;

		boolean inOpen = open.contains(t);
		if(!inOpen || t_final_cost < t.getAStarFinalCost()){
			t.setAStarFinalCost(t_final_cost);
			t.setParentCell(current);

			if(!inOpen){
				open.add((T)t);
			}
		}
	}
}