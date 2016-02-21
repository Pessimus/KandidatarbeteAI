package TestView;

import Model.World;
import Model.WorldCell;
import org.lwjgl.util.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.*;

import org.lwjgl.util.Dimension;

/**
 * Created by Tobias on 2016-02-14.
 */
public class Toolkit {
	public static Dimension getScreenDimension(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();

		return new Dimension(width, height);
	}


	/**
	 *
	 * @param startCell
	 * @param endCell
	 * @return A list of cells representing the path to the endCell, if there is one. Otherwise returns null.
	 */
	public static <T extends WorldCell> List<T> AStarPathFinder(T startCell, T endCell, Map<Point, T> map){
		PriorityQueue<T> open = new PriorityQueue<>((Object o1, Object o2) -> {
			T c1 = (T)o1;
			T c2 = (T)o2;

			return c1.getAStarFinalCost()<c2.getAStarFinalCost() ? -1 :
					c1.getAStarFinalCost()>c2.getAStarFinalCost() ? 1 : 0;
		});

		LinkedList<T> closed = new LinkedList<>();
		closed.add(startCell);

		LinkedList<T> path = new LinkedList<>();

		addTraversableNeighboursToList(startCell, map, open, closed);

		return path;
	}

	public static <T extends WorldCell> void addTraversableNeighboursToList(T cell, Map<Point, T> map, Collection<T> list, List<T> exclude){
		if(!map.containsValue(cell)) {
			throw new IllegalArgumentException();
		}
		int posX = cell.getPosX();
		int posY = cell.getPosY();

		Point p = new Point(posX, posY);

		for(int col = posX - 1; col <= posX + 1; col++){
			for(int row = posY - 1; row <= posY + 1; row++){
				if(!(posX == row &&  posY == col)) {
					T tempCell = map.get(new Point(col, row));
					if(tempCell.isTraversable() && !exclude.contains(tempCell)){
						list.add(tempCell);
					}
				}
			}
		}
	}
}
