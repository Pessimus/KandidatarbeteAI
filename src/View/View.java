package View;

import Utility.Constants;
import Utility.RenderObject;
import Utility.InventoryRender;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
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

    private volatile float scaleGraphicsX;
	private volatile float scaleGraphicsY;

	private RenderObject[] listToRender = {};
	private InventoryRender[] inventoryToRender = {};
	private int inventoryIndex = 0;

	private int[] playerNeeds;
	private String characterName;
	private int characterAge;

	private boolean displayInventory = false;
	private boolean displayPlayerNeeds = false;
	private boolean displayPause = false;
	private boolean displayBuildingInProcess = false;

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

    public View(int i, double scaleX, double scaleY) {
        stateNr = i;
		scaleGraphicsX = (float)scaleX;
		scaleGraphicsY = (float)scaleY;
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

		awtFont  = new java.awt.Font("SWTOR Trajan", java.awt.Font.BOLD, Constants.FONT_SIZE/Constants.ZOOM_LEVEL);
		font =  new TrueTypeFont(awtFont, false);

		pcs.firePropertyChange("startController", false, true);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		int tileOffsetX = -1*(renderPointX%Constants.WORLD_TILE_SIZE);
		int tileOffsetY = -1*(renderPointY%Constants.WORLD_TILE_SIZE);
		int tileIndexX  = renderPointX/Constants.WORLD_TILE_SIZE;
		int tileIndexY  = renderPointY/Constants.WORLD_TILE_SIZE;

		startX = renderPointX + tileOffsetX;
		startY = renderPointY + tileOffsetY;
		startTileX = tileIndexX;
		startTileY = tileIndexY;
		width = (int)Math.ceil(((Constants.SCREEN_WIDTH - tileOffsetX)/Constants.WORLD_TILE_SIZE)/scaleGraphicsX)+1;
		height = (int)Math.ceil(((Constants.SCREEN_HEIGHT - tileOffsetY)/Constants.WORLD_TILE_SIZE)/scaleGraphicsY)+1;
    }

	private int startX, startY, startTileX, startTileY, width, height;

	private Semaphore renderSema = new Semaphore(1);

	//Display Needs Variables
	float hungerStringYPos;
	float thirstStringYPos;
	float energyStringYPos;
	float nameStringYPos;
	float barWidth;
	float barHeight;
	float halfBarWidth;
	float barXPos;
	float hungerPercent;
	float thirstPercent;
	float energyPercent;
	java.awt.Font awtFont;
	TrueTypeFont font;

	//Display Pause Variables
	float halfTextWidth;
	float halfTextHeight;
	float pauseWidth;
	float pauseHeight;

	//Display Inventory
	int x,y;
	int i,j;
	int tilesPerRow, tilesPerColumn;
	float lineWidth = Constants.GRID_LINE_WIDTH;
	float optionStartX;
	float optionStartY;
	int eatStringWidth;
	int dropStringWidth;

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.scale(scaleGraphicsX,scaleGraphicsY);
		graphics.setFont(font);
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
						int imageWidth = resourceMap.get(obj.getRenderType()).getWidth();
						int imageHeight = resourceMap.get(obj.getRenderType()).getHeight();
						float imageScale, width, height;
						if(imageHeight >= imageWidth)
							imageScale = (float)(obj.getRadius()*2/imageHeight);
						else
							imageScale = (float)(obj.getRadius()*2/imageWidth);
						width = imageWidth*imageScale;
						height = imageHeight*imageScale;
						resourceMap.get(obj.getRenderType()).draw((float)obj.getX() - width/2, (float)obj.getY()- height/2, width, height);
					}
				}
			}
			semaphore.release();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to acquire semaphore to the 'listToRender' list!", e);
		}
			// ----------- Temporary display needs ----------- \\
		if(displayPlayerNeeds){
			nameStringYPos = gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL+Constants.MARGIN_TOP/Constants.ZOOM_LEVEL-Constants.HALF_TEXT_HEIGHT/Constants.ZOOM_LEVEL;
			hungerStringYPos = gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL+Constants.MARGIN_TOP/Constants.ZOOM_LEVEL*2-Constants.HALF_TEXT_HEIGHT/Constants.ZOOM_LEVEL;
			thirstStringYPos = gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL+Constants.MARGIN_TOP/Constants.ZOOM_LEVEL*3-Constants.HALF_TEXT_HEIGHT/Constants.ZOOM_LEVEL;
			energyStringYPos = gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL+Constants.MARGIN_TOP/Constants.ZOOM_LEVEL*4-Constants.HALF_TEXT_HEIGHT/Constants.ZOOM_LEVEL;

			barWidth = Constants.BOX_WIDTH/Constants.ZOOM_LEVEL-3*Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL-graphics.getFont().getWidth("Hunger");
			barHeight = graphics.getFont().getHeight("Hunger")/Constants.ZOOM_LEVEL;

			halfBarWidth = Constants.BOX_WIDTH/2;

			barXPos = Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL*2+graphics.getFont().getWidth("Hunger");

			hungerPercent = (float)playerNeeds[0]/(float)Constants.CHARACTER_HUNGER_MAX;
			thirstPercent = (float)playerNeeds[1]/(float)Constants.CHARACTER_THIRST_MAX;
			energyPercent = (float)playerNeeds[2]/(float)Constants.CHARACTER_ENERGY_MAX;

			graphics.setColor(Color.gray);

			new Image("res/ui_needs.png").draw(0,gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL, Constants.BOX_WIDTH/Constants.ZOOM_LEVEL, Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL);
			//graphics.fillRect(0,gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL, Constants.BOX_WIDTH/Constants.ZOOM_LEVEL, Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL);

			graphics.setColor(Color.white);
			graphics.drawString("Name:", Constants.MARGIN_LEFT / Constants.ZOOM_LEVEL, nameStringYPos);
			graphics.drawString(characterName, barXPos, nameStringYPos);
			graphics.drawString("Age:",halfBarWidth, nameStringYPos);
			graphics.drawString(Integer.toString(characterAge), halfBarWidth+Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL+graphics.getFont().getWidth("Age:"), nameStringYPos);

			//Hunger
			graphics.setColor(Color.white);
			graphics.drawString("Hunger:", Constants.MARGIN_LEFT / Constants.ZOOM_LEVEL, hungerStringYPos);
			//graphics.drawRect(barXPos, hungerStringYPos,barWidth,barHeight);

			if(hungerPercent > 0) {
				if(hungerPercent < Constants.NEEDS_CRITICAL_LEVEL) {
					graphics.setColor(new Color(238,0,0));
				}else if(hungerPercent > Constants.NEEDS_CONFORTABLE_LEVEL) {
					graphics.setColor(new Color(66,205,0));
				}else{
					graphics.setColor(new Color(255,230,0));
				}
				//X,Y,width,height
//				graphics.fillRect(barXPos + barWidth * (1 - hungerPercent), hungerStringYPos, barWidth - barWidth * (1 - hungerPercent), barHeight);
				graphics.fillRect(barXPos,hungerStringYPos,barWidth*hungerPercent,barHeight);
			}

			//Thirst
			graphics.setColor(Color.white);

			graphics.drawString("Thirst:",Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL, thirstStringYPos);
			//graphics.drawRect(barXPos, thirstStringYPos,barWidth,barHeight);

			if(thirstPercent > 0) {
				if(thirstPercent < Constants.NEEDS_CRITICAL_LEVEL) {
					graphics.setColor(new Color(238,0,0));
				}else if(thirstPercent > Constants.NEEDS_CONFORTABLE_LEVEL) {
					graphics.setColor(new Color(66,205,0));
				}else{
					graphics.setColor(new Color(255,230,0));
				}
//				graphics.fillRect(barXPos + barWidth * (1 - thirstPercent), thirstStringYPos, barWidth - barWidth * (1 - thirstPercent), barHeight);
				graphics.fillRect(barXPos,thirstStringYPos,barWidth*thirstPercent,barHeight);
			}

			//Energy
			graphics.setColor(Color.white);

			graphics.drawString("Energy:",Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL, energyStringYPos);
			//graphics.drawRect(barXPos, energyStringYPos,barWidth,barHeight);

			if(energyPercent > 0) {
				if(energyPercent < Constants.NEEDS_CRITICAL_LEVEL) {
					graphics.setColor(new Color(238,0,0));
				}else if(energyPercent > Constants.NEEDS_CONFORTABLE_LEVEL) {
					graphics.setColor(new Color(66,205,0));
				}else{
					graphics.setColor(new Color(255,230,0));
				}
//				graphics.fillRect(barXPos + barWidth * (1 - energyPercent), energyStringYPos, barWidth - barWidth * (1 - energyPercent), barHeight);
				graphics.fillRect(barXPos,energyStringYPos,barWidth*energyPercent,barHeight);
			}

			graphics.setColor(Color.white);

		}


		// ------------------------------------------ \\

		// ----------- Temporary display of the inventory ----------- \\

		if (displayInventory) {
			x=(int)(gameContainer.getWidth()/scaleGraphicsX)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*3;
			y=(int)(gameContainer.getHeight()/scaleGraphicsY)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*3;
			new Image("res/ui_inventory.png").draw(x-5,y-5,gameContainer.getWidth()/scaleGraphicsX-x+5,gameContainer.getHeight()/scaleGraphicsY-y+5);

			tilesPerColumn = tilesPerRow = (int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			for (int i = tilesPerRow; i >= 1 ; i--) {
				for (int j = tilesPerColumn; j >= 1; j--) {
					x=(int) (gameContainer.getWidth()/scaleGraphicsX)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*j;
					y=(int)(gameContainer.getHeight()/scaleGraphicsY)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*i;
					graphics.setLineWidth(Constants.GRID_LINE_WIDTH);
					//graphics.drawRect(x, y, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL);
					if(inventoryIndex == (3-i)*tilesPerRow+(4-j)){
						optionStartX = gameContainer.getWidth()/scaleGraphicsX-tilesPerRow*Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.OPTION_BOX_WIDTH/Constants.ZOOM_LEVEL;
						optionStartY = gameContainer.getHeight()/scaleGraphicsY-Constants.OPTION_BOX_HEIGHT/Constants.ZOOM_LEVEL;
						eatStringWidth = graphics.getFont().getWidth("c: Consume");
						dropStringWidth = graphics.getFont().getWidth("d: Drop");
						graphics.fillRect(x, y, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL);
						graphics.fillRect(optionStartX, optionStartY, Constants.OPTION_BOX_WIDTH/Constants.ZOOM_LEVEL, Constants.OPTION_BOX_HEIGHT/Constants.ZOOM_LEVEL);
						graphics.setColor(Color.black);
						graphics.drawString("c: Consume", optionStartX+Constants.OPTION_MARGIN_LEFT/Constants.ZOOM_LEVEL, optionStartY+Constants.OPTION_MARGIN_TOP/Constants.ZOOM_LEVEL);
						graphics.drawString("d: Drop", optionStartX+Constants.OPTION_MARGIN_LEFT/Constants.ZOOM_LEVEL*2+eatStringWidth, optionStartY+Constants.OPTION_MARGIN_TOP/Constants.ZOOM_LEVEL);
						graphics.drawString("b: Back", optionStartX+Constants.OPTION_MARGIN_LEFT/Constants.ZOOM_LEVEL*3+eatStringWidth+dropStringWidth, optionStartY+Constants.OPTION_MARGIN_TOP/Constants.ZOOM_LEVEL);
						graphics.setColor(Color.white);
					}
				}
			}


			i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			j=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			for(InventoryRender invRender : inventoryToRender) {
				x=(int)(gameContainer.getWidth()/scaleGraphicsX)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*i;
				y=(int)(gameContainer.getHeight()/scaleGraphicsY)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*j;

				new Image(invRender.type.pathToResource).draw(x+lineWidth/scaleGraphicsX, y+lineWidth/scaleGraphicsY, 1f/Constants.ZOOM_LEVEL);
				//graphics.fillRect(x+Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL, y+Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL,
				//		Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL);
				graphics.setColor(Color.black);
				if(invRender.amount < 10) {
					graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL+Constants.AMOUNT_DISPLAY_MARGIN,
							y + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL);
				}else {
					graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL,
							y + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL);
				}
				graphics.setColor(Color.white);
				i--;
				if(i==0 && j!=1){
					i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
					j--;
				}
			}
		}

		if(displayPause){
			halfTextWidth = graphics.getFont().getWidth("Paused")/2;
			halfTextHeight = graphics.getFont().getHeight("Paused")/2;
			pauseWidth = gameContainer.getWidth()/(2*scaleGraphicsX)-halfTextWidth;
			pauseHeight = gameContainer.getHeight()/(2*scaleGraphicsY)-halfTextHeight;
			graphics.drawString("Paused", pauseWidth, pauseHeight);
		}

		if(displayBuildingInProcess){
			halfTextWidth = graphics.getFont().getWidth("Building in process")/2;
			float buildingWidth = gameContainer.getWidth()/(2*scaleGraphicsX)-halfTextWidth;
			float buildingHeight = gameContainer.getHeight()/(4*scaleGraphicsY);
			graphics.drawString("Building in process", buildingWidth, buildingHeight);


		}
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

	public void setCharacterName(String name){
		characterName = name;
	}

	public void setCharacterAge(int age){
		characterAge = age;
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

	public void setRenderPoint(double x, double y){
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

	public void renderInventory(LinkedList<InventoryRender> inventoryItems){
		LinkedList<InventoryRender> tmp = new LinkedList<>();
		for(InventoryRender inventoryRen : inventoryItems){
			tmp.add(inventoryRen);
		}

		inventoryToRender = tmp.toArray(new InventoryRender[tmp.size()]);
		displayInventory = true;
		displayPlayerNeeds = true;
	}

	public void hideInventory(){
		displayInventory = false;
		displayPlayerNeeds = false;
	}

	public void togglePause(){
		displayPause = !displayPause;
	}

	public void toggleBuildingInProcess(){
		displayBuildingInProcess = !displayBuildingInProcess;
	}


	public void setItemInFocus(int index){
		inventoryIndex = index;
	}


}
