package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface IItem {
	String getActions();
	int getAmount();
	void setAmount(int value);
	enum Type{
		GOLD_ITEM, WATER_ITEM, WOOD_ITEM, STONE_ITEM, MEAT_ITEM, FISH_ITEM, CROPS_ITEM
	}
	Type getType();
}
