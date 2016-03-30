package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IItem {

	String getActions();
	int getAmount();
	void setAmount(int value);
	void addAmount(int value);
	void removeAmount(int value);

	//TODO maby change this to work for other collidables than characters
	void interacted(Character rhs);
	void consumed(Character rhs);
	void attacked(Character rhs);

	enum Type{
		GOLD_ITEM("res/gold_item.png"), WATER_ITEM("res/water_item.png"), WOOD_ITEM("res/wood_item.png"),
		STONE_ITEM("res/rock_item.png"), MEAT_ITEM("res/meat_item.png"), FISH_ITEM("res/fish_item.png"),
		CROPS_ITEM("res/Villager16x16.png");

		public String pathToResource;

		Type(String path){
			pathToResource = path;
		}
	}
	Type getType();
}
