package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by Martin on 07/04/2016.
 */
public class Interaction {

	public enum InteractionType{
		SOCIAL,HOSTILE
	}

	private PropertyChangeSupport pcs;

	private boolean active;

	private Character character1;
	private Character character2;

	private int character1Key;
	private int character2Key;

	private boolean character1Active;
	private boolean character2Active;

	private Inventory tradeOfferCharacter1;
	private Inventory tradeOfferCharacter2;

	private boolean acceptTradeCharacter1;
	private boolean acceptTradeCharacter2;

//----------------------------------------------Constructor-----------------------------------------------------------\\

	public Interaction(Character character1, Character character2){
		this.pcs = new PropertyChangeSupport(this);

		this.active = true;

		this.character1 = character1;
		this.character2 = character2;

		this.character1Key = character1.hashCode();
		this.character2Key = character2.hashCode();

		this.character1Active = false;
		this.character2Active = false;

		this.tradeOfferCharacter1 = new Inventory();
		this.tradeOfferCharacter2 = new Inventory();

		this.acceptTradeCharacter1 = false;
		this.acceptTradeCharacter2 = false;
	}

//---------------------------------------------Start Methods----------------------------------------------------------\\

	public void acceptInteraction(int key, PropertyChangeListener listener){
		if(active) {
			pcs.addPropertyChangeListener(listener);
		}
	}

	public void declineInteraction(){
		this.endInteraction();
	}

//---------------------------------------------Help Methods-----------------------------------------------------------\\

	private boolean interactable(){
		return (Math.abs(character1.getX()-character2.getX())<Constants.CHARACTER_INTERACTION_RADIUS)
				&& (Math.abs(character1.getY()-character2.getY())<Constants.CHARACTER_INTERACTION_RADIUS);
	}

	private boolean detectable(){
		return (Math.abs(character1.getX()-character2.getX())<Constants.CHARACTER_SURROUNDING_RADIUS)
				&& (Math.abs(character1.getY()-character2.getY())<Constants.CHARACTER_SURROUNDING_RADIUS);
	}

//--------------------------------------------Social Methods----------------------------------------------------------\\

	public void talk(InteractionType type){
		if(active){
			if(detectable()) {
				if(interactable()) {
					//TODO swich case
						//TODO fire propertyChange
						//TODO update needs of c1
						//TODO update needs of c2
				}
			}else {
				this.endInteraction();
			}
		}
	}

//---------------------------------------------Trade Methods----------------------------------------------------------\\

	public void removeFromTradeOffer(int key, IItem item){
		if(active) {
			if(detectable()) {
				if (interactable()) {
					if (key == character1Key) {
						acceptTradeCharacter1 = false;
						acceptTradeCharacter2 = false;
						//TODO fire propertyChange
						tradeOfferCharacter1.removeItem(item);
					} else if (key == character2Key) {
						acceptTradeCharacter1 = false;
						acceptTradeCharacter2 = false;
						//TODO fire propertyChange
						tradeOfferCharacter2.removeItem(item);
					}
				} else {
					this.endInteraction();
				}
			}
		}
	}

	public void removeFromTradeRequest(int key, IItem item){
		if(active) {
			if(detectable()) {
				if (interactable()) {
					if (key == character1Key) {
						acceptTradeCharacter1 = false;
						acceptTradeCharacter2 = false;
						//TODO fire propertyChange
						tradeOfferCharacter2.removeItem(item);
					} else if (key == character2Key) {
						acceptTradeCharacter1 = false;
						acceptTradeCharacter2 = false;
						//TODO fire propertyChange
						tradeOfferCharacter1.removeItem(item);
					}
				} else {
					this.endInteraction();
				}
			}
		}
	}

	public void addToTradeOffer(int key, IItem item){
		if(active) {
			if(detectable()) {
				if (interactable()) {
					if (key == character1Key) {
						if(character1.inventoryContains(item)) {
							acceptTradeCharacter1 = false;
							acceptTradeCharacter2 = false;
							//TODO fire propertyChange
							tradeOfferCharacter1.addItem(item);
						}else {//Item not in characters inventory
							//TODO fire propertyChange
						}
					} else if (key == character2Key) {
						if(character2.inventoryContains(item)) {
							acceptTradeCharacter1 = false;
							acceptTradeCharacter2 = false;
							//TODO fire propertyChange
							tradeOfferCharacter2.addItem(item);
						}else {//Item not in characters inventory
							//TODO fire propertyChange
						}
					}
				} else {
					this.endInteraction();
				}
			}
		}
	}

	public void addToTradeRequest(int key, IItem item){
		if(active) {
			if(detectable()) {
				if (interactable()) {
					if (key == character1Key) {
						if(character2.inventoryContains(item)) {
							acceptTradeCharacter1 = false;
							acceptTradeCharacter2 = false;
							//TODO fire propertyChange
							tradeOfferCharacter2.addItem(item);
						}else {//Item not in characters inventory
							//TODO fire propertyChange
						}
					} else if (key == character2Key) {
						if (character1.inventoryContains(item)) {
							acceptTradeCharacter1 = false;
							acceptTradeCharacter2 = false;
							//TODO fire propertyChange
							tradeOfferCharacter1.addItem(item);
						}else {//Item not in characters inventory
							//TODO fire propertyChange
						}
					}
				} else {
					this.endInteraction();
				}
			}
		}
	}

	public void acceptTrade(int key){
		if(active){
			if(detectable()) {
				if (interactable()) {
					if (key == character1Key) {
						//TODO fire propertyChange
						this.acceptTradeCharacter1 = true;
						if (acceptTradeCharacter2) {
							//TODO fire propertyChange
							transferTrade();
						}
					} else if (key == character2Key) {
						//TODO fire propertyChange
						this.acceptTradeCharacter2 = true;
						if (acceptTradeCharacter1) {
							//TODO fire propertyChange
							transferTrade();
						}
					}
				} else {
					this.endInteraction();
				}
			}
		}
	}

	public void declineTrade(int key){
		if(active){
			if(detectable()) {
				if (interactable()) {
					if (key == character1Key) {
						//TODO fire propertyChange
						this.acceptTradeCharacter1 = false;
					} else if (key == character2Key) {
						//TODO fire propertyChange
						this.acceptTradeCharacter2 = false;
					}
				} else {
					this.endInteraction();
				}
			}
		}
	}

	private void transferTrade(){
		Inventory c1i = this.tradeOfferCharacter1;
		this.tradeOfferCharacter1 = new Inventory();
		Inventory c2i = this.tradeOfferCharacter2;
		this.tradeOfferCharacter2 = new Inventory();

		for(IItem tmpItem : c1i.getItems()){
			character1.removeFromInventory(tmpItem);
			character2.addToInventory(tmpItem);
		}

		for(IItem tmpItem : c2i.getItems()){
			character2.removeFromInventory(tmpItem);
			character1.addToInventory(tmpItem);
		}

	}

//----------------------------------------------End Methods-----------------------------------------------------------\\

	public void endInteraction(){
		this.active = false;
		//TODO fire propertyChange
		character1 = null;
		character2 = null;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}

}
