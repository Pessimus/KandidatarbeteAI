package Model;

import Model.Structures.Stockpile;
import Utility.Constants;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * Created by Martin on 2016-04-17.
 */
public class StockpileInteraction {
	private PropertyChangeSupport pcs;

	private Character character;
	private Stockpile stockpile;

	private Inventory stockpileInventory;

//----------------------------------------------Constructor-----------------------------------------------------------\\

	public StockpileInteraction(Character character, Stockpile stockpile){
		this.pcs = new PropertyChangeSupport(this);

		this.character = character;
		this.stockpile = stockpile;

		this.stockpileInventory = stockpile.getInventory();
	}

//---------------------------------------------Help Methods-----------------------------------------------------------\\

	private boolean interactable(){
		return (Math.abs(character.getX()-stockpile.getX())< Constants.CHARACTER_INTERACTION_RADIUS)
				&& (Math.abs(character.getY()-stockpile.getY())<Constants.CHARACTER_INTERACTION_RADIUS);
	}

	private boolean detectable(){
		return (Math.abs(character.getX()-stockpile.getX())<Constants.CHARACTER_SURROUNDING_RADIUS)
				&& (Math.abs(character.getY()-stockpile.getY())<Constants.CHARACTER_SURROUNDING_RADIUS);
	}

//-------------------------------------------Exchange methods---------------------------------------------------------\\

	public void addToStockpile(IItem item){
		if(detectable()) {
			if (interactable()) {
				if (character.inventoryContains(item)) {
					character.removeFromInventory(item);
					stockpileInventory.addItem(item);
				}
			}
		}else{
			this.endInteraction();
		}
	}

	public void takeFromStockpile(IItem item){
		if(detectable()) {
			if (interactable()) {
				if (stockpileInventory.contains(item)) {
					stockpileInventory.removeItem(item);
					character.addToInventory(item);
				}
			}
		}else{
			this.endInteraction();
		}
	}


	public void endInteraction(){
		character = null;
		stockpile = null;
		stockpileInventory = null;

		pcs.firePropertyChange("endStockpileInteraction", false, true);

		PropertyChangeListener[] listeners = pcs.getPropertyChangeListeners();
		int i = 0;
		while(i < listeners.length){
			pcs.removePropertyChangeListener(listeners[i]);
		}

	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}

}
