package Utility;

/**
 * Created by Martin on 03/03/2016.
 */
public final class RenderObject{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private final double xPos;
	private final double yPos;
	private final double radius;

	private RENDER_OBJECT_ENUM objectType;

	public enum RENDER_OBJECT_ENUM{
		CHARACTER("res/Villager16x16.png"), CHARACTER2("res/female.png"),
		LAKE("res/lake.png"),
		STONE("res/stone1.png"), STONE2("res/stone2.png"),
		CROPS("res/crops.png"),
		WOOD("res/tree1.png"), WOOD2("res/tree2.png"),
		COW_LEFT("res/cow_left.png"),COW_RIGHT("res/cow_right.png"),
		GOLD("res/gold1.png"), GOLD2("res/gold2.png"),
		HOUSE("res/house1.png"), HOUSE2("res/stone2.png"),
		STOCKPILE("res/stockpile.png"), STOCKPILE_FULL("res/stockpile_full.png"), STOCKPILE_STONE("res/stockpile_stone.png"), STOCKPILE_WOOD("res/stockpile_wood.png"),
		FARM("res/windmill.png"),
		CONSTRUCTION("res/construction_site.png"),
		SLEEPING("res/sleeping.png"),
		FISHING("res/fishing.png"),
		HUNTING("res/hunting.png"),
		FORESTING("res/foresting.png"),
		EXPLORING("res/exploring.png"),
		BUILDING("res/building.png"),
		PICKING("res/picking.png"),
		FARMING("res/farming.png"),
		STORING("res/storing.png"),
		THINK_FORESTING("res/forestingThink.png"),  THINK_PICKING("res/pickingThink.png"), THINK_FISHING("res/fishingThink.png"), THINK_EXPLORING("res/exploringThink.png"),
		THINK_SLEEPING("res/sleepingThink.png"), THINK_FARMING("res/farmingThink.png"), THINK_BUILDING("res/buildingThink.png"), THINK_HUNTING("res/huntingThink.png"),
		THINK_DRINKING("res/drinkingThink.png"), DRINKING("res/drinking.png"), THINK_SOWING("res/sowingThink.png"),
		SOWING("res/sowing.png"), THINK_STORING("res/storingThink.png"),
		EMPTY("res/empty.png"),
		MEAT("res/meat_item.png");

		public String pathToResource;

		RENDER_OBJECT_ENUM(String path){
			pathToResource = path;
		}
	}

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public RenderObject(double x, double y, double r, RENDER_OBJECT_ENUM type){
		xPos = x;
		yPos = y;
		radius = r;
		objectType = type;

	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	public final double getX() {
		return xPos;
	}

	public final double getY() {
		return yPos;
	}

	public final double getRadius() {
		return radius;
	}

	public final RENDER_OBJECT_ENUM getRenderType() {
		return objectType;
	}

}
