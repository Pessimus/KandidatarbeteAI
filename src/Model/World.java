package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;


/**
 * Created by Martin on 23/02/2016.
 */
public class World implements Runnable{

	PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private HashMap<Integer,Character> characters;
	private LinkedList<ICollidable> collidablesR;
	private CollisionList collidables;
	private LinkedList<ITimeable> timeables;
	private LinkedList<ICollidable> statics; //List containing all collidables that does not move (or get destroyed or created too often)

	//--------Remove lists--------------
	private LinkedList<ICollidable> collidablestoberemoved = new LinkedList<>();
	private LinkedList<ICollidable> collideablesrtoberemoved = new LinkedList<>();
	private LinkedList<ITimeable> timeablestoberemoved = new LinkedList<>();
	private LinkedList<Character> characterstoberemoved = new LinkedList<>();

	private double width;
	private double height;
	private boolean pause;

	//TODO code this in a proper way --------------------------------
				public enum GAMESPEED {
					NORMAL(1), FAST(1.5), FASTER(2);

					private final double gameSpeed;

					GAMESPEED(double gameSpeed) {
						this.gameSpeed = gameSpeed;
					}

					public double getGameSpeed() {
						return gameSpeed;
					}
				}

				private static double gameSpeed;
				public static double getGameSpeed() {
					return gameSpeed;
				}
				public static void setGameSpeed(double gs) {
					gameSpeed = gs;
				}

	public World (double width, double height){
		this.width = width;
		this.height = height;
		this.collidables = new CollisionList();
		this.collidablesR = new LinkedList<>();
		this.timeables = new LinkedList<>();
		this.characters = new HashMap<>();
	}

	/**
	 * Update characters
	 * Update timeables
	 * Update collidables
	 */
	@Override
	public void run() {
		if (pause != true) {
			for (ITimeable timedObj : timeables) {
				timedObj.updateTimeable();
			}

			for (Character character : characters.values()) {
				if (!character.isAlive()) {//Character is dead and should be removed
					collidablestoberemoved.add(character);
					collideablesrtoberemoved.add(character);
					timeablestoberemoved.add(character);
					characterstoberemoved.add(character);
				}
			}

			removeObjects();

			//TODO rename (probably)
			this.collidables.handleCollision();

			//TODO Code for updating the character (movement and actions?)

			this.pcs.firePropertyChange("update",0, 1);//TODO change the way the loop in controller works.
		}
	}

	//TODO check if place is available.
	public Character addCharacter(float xPoss, float yPoss, int key) {
		Character character = new Character(xPoss, yPoss, key);

		this.collidablesR.add(character);
		this.collidables.add(character);
		this.timeables.add(character);
		this.characters.put(key, character);

		pcs.firePropertyChange("createdCharacter", null, character);

		return character;
	}

	public void removeObjects() {
		for (ICollidable collidable : this.collidablestoberemoved) {
			collidables.remove(collidable);
		}
		collidablestoberemoved.clear();

		for (ITimeable timeable : this.timeablestoberemoved) {
			timeables.remove(timeable);
		}
		timeablestoberemoved.clear();

		for (Character character : this.characterstoberemoved) {
			characters.remove(character.getKey());
		}
		characterstoberemoved.clear();

		for (ICollidable collidable : this.collideablesrtoberemoved) {
			collidablesR.remove(collidable);
		}
		collideablesrtoberemoved.clear();
	}

	public RenderObject[] getRenderObjects() {
		RenderObject[] renderObjects = new RenderObject[collidables.getSize()];

		for (int i = 0; i < collidablesR.size(); i++) {
			renderObjects[i] = collidablesR.get(i).getRenderObject();
		}
		return renderObjects;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	/* Pause the game, if P is pressed, pause() will pause the run lopp*/
	public void togglePause() {
		if (pause == false) {
			pause = true;
		}
		else {
			pause = false;
		}
	}

	//TODO better MVC praxis
	public LinkedList<InventoryRender> displayPlayerInventory() {
		return characters.get(Constants.PLAYER_CHARACTER_KEY).getRenderInventory();
	}

	//TODO REMOVE test method.
	/*public void hit() {
		this.characters.get(1).hit();
	}*/

 }


