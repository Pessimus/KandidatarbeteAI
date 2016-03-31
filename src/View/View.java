package View;

import Model.Constants;
import Model.RenderObject;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Oskar on 2016-02-26.
 */
public class View extends BasicGameState implements InputListener{

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private int stateNr;
    private TiledMap map;

	private volatile int renderPointX = (int)Constants.DEFAULT_WORLD_VIEW_X;
	private volatile int renderPointY = (int)Constants.DEFAULT_WORLD_VIEW_Y;

    private volatile float scaleGraphics;
	private int tempWidth;
	private int tempHeight;

	private RenderObject[] listToRender = {};
	private Model.InventoryRender[] inventoryToRender = {};

	private int[] playerNeeds;

	private boolean displayInventory = false;
	private boolean displayPlayerNeeds = false;
	private final Semaphore semaphore = new Semaphore(1);
	private final Map<RenderObject.RENDER_OBJECT_ENUM, Image> resourceMap = new HashMap<>();
	private final Map<Model.IItem.Type, Image> inventoryMap = new HashMap<>();

    public enum INPUT_ENUM {
		KEY_RELEASED(0), KEY_PRESSED(1),
		MOUSE_RELEASED(0), MOUSE_PRESSED(1), MOUSE_MOVED(2),
		MOUSE_WHEEL_MOVED(0);

        public int value;
        //String

        INPUT_ENUM(int i){
            value = i;
        }
    }

    public View(int i, float scale) {
        stateNr = i;
		scaleGraphics = scale;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new TiledMap("res/mapSquare.tmx");

		//Link together all render-objects with a specific image
		for(RenderObject.RENDER_OBJECT_ENUM e : RenderObject.RENDER_OBJECT_ENUM.values()){
			resourceMap.put(e, new Image(e.pathToResource));
		}

		//Link together all IItem-enums with a specific image
		for(Model.IItem.Type e : Model.IItem.Type.values()){
			inventoryMap.put(e, new Image(e.pathToResource));
		}

		pcs.firePropertyChange("startController", false, true);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		//tempWidth = (int)Math.ceil(Constants.SCREEN_WIDTH/Constants.WORLD_TILE_SIZE/scaleGraphics);
		//tempHeight = (int)Math.ceil(Constants.SCREEN_HEIGHT/Constants.WORLD_TILE_SIZE/scaleGraphics);
		//tempWidth = (int)Math.ceil(Constants.SCREEN_WIDTH/Constants.WORLD_TILE_SIZE);
		//tempHeight = (int)Math.ceil(Constants.SCREEN_HEIGHT/Constants.WORLD_TILE_SIZE);
		int tileOffsetX = -1*(renderPointX%Constants.WORLD_TILE_SIZE);
		int tileOffsetY = -1*(renderPointY%Constants.WORLD_TILE_SIZE);
		int tileIndexX  = renderPointX/Constants.WORLD_TILE_SIZE;
		int tileIndexY  = renderPointY/Constants.WORLD_TILE_SIZE;

		startX = renderPointX + tileOffsetX;
		startY = renderPointY + tileOffsetY;
		startTileX = tileIndexX;
		startTileY = tileIndexY;

		width = (int)Math.ceil(((Constants.SCREEN_WIDTH - tileOffsetX)/Constants.WORLD_TILE_SIZE)/scaleGraphics)+1;
		height = (int)Math.ceil(((Constants.SCREEN_HEIGHT - tileOffsetY)/Constants.WORLD_TILE_SIZE)/scaleGraphics)+1;
    }

	private int startX, startY, startTileX, startTileY, width, height;

	private Semaphore renderSema = new Semaphore(1);

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.scale(scaleGraphics,scaleGraphics);

		try{
			renderSema.acquire();
			graphics.translate(-renderPointX, -renderPointY);
			map.render(startX, startY, startTileX, startTileY, width, height);
			graphics.translate(renderPointX, renderPointY);
			renderSema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}

		try {
			semaphore.acquire();
			if(listToRender != null){
				if(listToRender.length > 0) {
					for (RenderObject obj : listToRender) {
						resourceMap.get(obj.getRenderType()).draw(obj.getX(), obj.getY());
					}
				}
			}
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}
			// ----------- Temporary display of the inventory ----------- \\

		if(displayPlayerNeeds){
			float hungerStringYPos = gameContainer.getHeight()/scaleGraphics-Constants.BOX_HEIGHT+Constants.MARGIN_FROM_TOP-Constants.HALF_TEXT_HEIGHT;
			float thirstStringYPos = gameContainer.getHeight()/scaleGraphics-Constants.BOX_HEIGHT+Constants.MARGIN_FROM_TOP*2-Constants.HALF_TEXT_HEIGHT;
			float energyStringYPos = gameContainer.getHeight()/scaleGraphics-Constants.BOX_HEIGHT+Constants.MARGIN_FROM_TOP*3-Constants.HALF_TEXT_HEIGHT;

			float barWidth = Constants.BOX_WIDTH-3*Constants.MARGIN_FROM_LEFT-graphics.getFont().getWidth("Hunger");
			float barHeight = graphics.getFont().getHeight("Hunger");

			float barXPos = Constants.MARGIN_FROM_LEFT*2+graphics.getFont().getWidth("Hunger");

			float hungerPercent = (float)playerNeeds[0]/(float)Constants.CHARACTER_HUNGER_MAX;
			float thirstPercent = (float)playerNeeds[1]/(float)Constants.CHARACTER_THIRST_MAX;
			float energyPercent = (float)playerNeeds[2]/(float)Constants.CHARACTER_ENERGY_MAX;

			graphics.setColor(Color.gray);
			graphics.fillRect(0,gameContainer.getHeight()/scaleGraphics-Constants.BOX_HEIGHT, Constants.BOX_WIDTH, Constants.BOX_HEIGHT);
			graphics.setColor(Color.white);
			graphics.drawString("Hunger:",Constants.MARGIN_FROM_LEFT, hungerStringYPos);
			graphics.drawRect(barXPos, hungerStringYPos,barWidth,barHeight);
			if(hungerPercent < 0.2)
				graphics.setColor(Color.red);
			if(hungerPercent > 0)
				graphics.fillRect(barXPos+barWidth*(1-hungerPercent), hungerStringYPos, barWidth-barWidth*(1-hungerPercent), barHeight);
			graphics.setColor(Color.white);
			graphics.drawString("Thirst:",Constants.MARGIN_FROM_LEFT, thirstStringYPos);
			if(thirstPercent < 0.2)
				graphics.setColor(Color.red);
			if(thirstPercent > 0)
				graphics.fillRect(barXPos+barWidth*(1-thirstPercent), thirstStringYPos, barWidth-barWidth*(1-thirstPercent), barHeight);
			graphics.setColor(Color.white);
			graphics.drawRect(barXPos, thirstStringYPos,barWidth,barHeight);
			graphics.drawString("Energy:",Constants.MARGIN_FROM_LEFT, energyStringYPos);
			graphics.drawRect(barXPos, energyStringYPos,barWidth,barHeight);
			if(energyPercent < 0.2)
				graphics.setColor(Color.red);
			if(energyPercent > 0)
				graphics.fillRect(barXPos+barWidth*(1-energyPercent), energyStringYPos, barWidth-barWidth*(1-energyPercent), barHeight);
			graphics.setColor(Color.white);
		}


		// ------------------------------------------ \\

		// ----------- Temporary display of the inventory ----------- \\

		if (displayInventory) {
			int x,y;
			float lineWidth = Constants.GRID_LINE_WIDTH;
			for (int i = 1; i < Math.sqrt(Constants.MAX_INVENTORY_SLOTS)+1; i++) {
				for (int j = 1; j < Math.sqrt(Constants.MAX_INVENTORY_SLOTS)+1; j++) {
					x=(int)(gameContainer.getWidth()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*i;
					y=(int)(gameContainer.getHeight()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*j;
					graphics.setLineWidth(Constants.GRID_LINE_WIDTH);
					graphics.drawRect(x-lineWidth, y-lineWidth, Constants.SLOT_DISPLAY_SIZE, Constants.SLOT_DISPLAY_SIZE);
				}
			}

			int i,j;
			i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			j=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			for(Model.InventoryRender invRender : inventoryToRender) {
				x=(int)(gameContainer.getWidth()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*i;
				y=(int)(gameContainer.getHeight()/scaleGraphics)-Constants.SLOT_DISPLAY_SIZE*j;

				graphics.drawImage(new Image(invRender.type.pathToResource), x-lineWidth/2, y-lineWidth/2);
				graphics.fillRect(x+Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT-lineWidth, y+Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT-lineWidth,
						Constants.SLOT_DISPLAY_AMOUNT, Constants.SLOT_DISPLAY_AMOUNT);
				graphics.setColor(Color.black);
				if(invRender.amount < 10) {
					graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT+Constants.AMOUNT_DISPLAY_MARGIN-lineWidth,
							y + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT+Constants.AMOUNT_DISPLAY_MARGIN-lineWidth);
				}else {
					graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT-lineWidth,
							y + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT-lineWidth);
				}
				graphics.setColor(Color.white);
				i--;
				if(i==0 && j!=1){
					i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
					j--;
				}
			}
		}
		// ------------------------------------------ \\
	}

	/*
	//Zoom function???

	public void zoomOut(){
		if(scaleGraphics > 0.5f)
			scaleGraphics -= 0.1f;
	}

	public void zoomIn(){
		if(scaleGraphics < 3f){
			scaleGraphics += 0.1f;
		}
	}
	*/


	//-----Getters and Setters
    @Override
    public int getID() {
        return stateNr;
    }

	public void setPlayerNeeds(int[] needs){
		playerNeeds = needs.clone();
	}

	// ----------- Key, mouse and property events ----------- \\

    @Override
    public void keyPressed(int key, char c) {
		pcs.firePropertyChange(INPUT_ENUM.KEY_PRESSED.toString(), 0, new Integer[]{INPUT_ENUM.KEY_PRESSED.value, key});

    }

    @Override
    public void keyReleased(int key, char c){
		pcs.firePropertyChange(INPUT_ENUM.KEY_RELEASED.toString(), 0, new Integer[]{INPUT_ENUM.KEY_RELEASED.value, key});
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_MOVED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_MOVED.value,oldx, oldy, newx, newy});
    }

    @Override
    public void mousePressed(int button, int x, int y){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_PRESSED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_PRESSED.value, button, x, y});
    }

	@Override
	public void mouseReleased(int button, int x, int y){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_RELEASED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_RELEASED.value, button, x, y});
	}

	@Override
	public void mouseWheelMoved(int var){
		pcs.firePropertyChange(INPUT_ENUM.MOUSE_WHEEL_MOVED.toString(), 0, new Integer[]{INPUT_ENUM.MOUSE_WHEEL_MOVED.value, var});
	}

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }


	// -------- Set renderList and renderPoint -------- \\

	public void setRenderList(RenderObject[] objList){
		try {
			semaphore.acquire();
			listToRender = objList;
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}
	}

	public void setRenderPoint(float x, float y){
		try{
			renderSema.acquire();
			renderPointX = (int) x;
			renderPointY = (int) y;
			renderSema.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}
	}



	// ----------- Render and hide inventory ----------- \\

	public void renderInventory(LinkedList<Model.InventoryRender> inventoryItems){
		LinkedList<Model.InventoryRender> tmp = new LinkedList<>();
		for(Model.InventoryRender inventoryRen : inventoryItems){
			tmp.add(inventoryRen);
		}

		inventoryToRender = tmp.toArray(new Model.InventoryRender[tmp.size()]);
		displayInventory = true;
		displayPlayerNeeds = true;
	}

	public void hideInventory(){
		displayInventory = false;
		displayPlayerNeeds = false;
	}
}
