package Model;

/**
 * Created by Tobias on 2016-02-20.
 */
public abstract class WorldCell{// implements Comparable{
	private int posX;

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public boolean isTraversable() {
		return traversable;
	}

	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}

	public WorldCell getParentCell() {
		return parentCell;
	}

	public void setParentCell(WorldCell parentCell) {
		this.parentCell = parentCell;
	}

	private int posY;

	private static final boolean DEFAULT_TRAVERSIBLE = true;
	private boolean traversable;

	//    A* Pathfinding variables

	private static final int DIAGONAL_COST = 1414;
	private static final int DIRECT_COST = 1000;

	private WorldCell parentCell = null;

	private int AStarHeuristicalCost = 0;
	private int AStarFinalCost = 0;

	protected WorldCell(){
		posX = 0;
		posY = 0;

		traversable = DEFAULT_TRAVERSIBLE;
	}

	protected WorldCell(int x, int y){
		posX = x;
		posY = y;

		traversable = DEFAULT_TRAVERSIBLE;
	}

	protected WorldCell(int x, int y, boolean traversable){
		posX = x;
		posY = y;

		this.traversable = traversable;
	}

	public int getAStarHeuristicalCost(){
		return AStarHeuristicalCost;
	}
	public void setAStarHeuristicalCost(int heuristical){
		AStarHeuristicalCost = heuristical;
	}

	public int getAStarFinalCost(){
		return AStarFinalCost;
	}
	public void setAStarFinalCost(int finalCost){
		AStarFinalCost = finalCost;
	}

	/*
	public boolean compareTo(){
		
	}
	*/
}
