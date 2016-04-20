package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IItem <T extends IItem<T>>{

	enum Type{
		GOLD_ITEM("res/gold_item.png"), WATER_ITEM("res/water_item.png"), WOOD_ITEM("res/wood_item.png"),
		STONE_ITEM("res/rock_item.png"), MEAT_ITEM("res/meat_item.png"), FISH_ITEM("res/fish_item.png"),
		CROPS_ITEM("res/crops_item.png");

		public String pathToResource;

		Type(String path){
			pathToResource = path;
		}
	}

	T clone();

	//TODO add javadock
	String getActions();

	/** @return the amount of the item that is represented. */
	int getAmount();
	/**
	 * Sets the amount to the specified value.
	 * @param value the new amount of the item.
	 */
	void setAmount(int value);
	/**
	 * Increases the amount of the item by the specified value.
	 * @param value the amount to be added to the item.
	 */
	void addAmount(int value);
	/**
	 * Decreases the amount of the item by the specified value.
	 * @param value the amount to be removed from the item.
	 */
	void removeAmount(int value);

	//TODO maby change this to work for other collidables than characters
	/**
	 * Indicates that a Character is interacting with the item.
	 * @param rhs the character that is interacting with it.
	 */
	void interacted(Character rhs);
	/**
	 * Indicates that a Character is consuming the item.
	 * @param rhs the character that is consuming it.
	 */
	void consumed(Character rhs);
	/**
	 * Indicates that a Character is attacking the item.
	 * @param rhs the character that is attacking it.
	 */
	void attacked(Character rhs);

	/** @return the type of item that is represented. */
	Type getType();

	int[] getNeedsChange();
}
