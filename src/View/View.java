package View;

import Model.World;
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
	private volatile float scaleGUIX;
	private volatile float scaleGUIY;

	private RenderObject[] listToRender = {};
	private InventoryRender[] inventoryToRender = {};
	private int inventoryIndex = 0;

	private int[] playerNeeds;
	private String characterName;
	private int characterAge;

	private boolean displayInventory = false;
	private boolean displayPlayerNeeds = false;
	private boolean displayPause = false;
	private boolean displayCommands = false;
	private boolean displayStats = false;
	private boolean displayBuildingInProcess = false;

	private final Semaphore semaphore = new Semaphore(1);
	private final Map<RenderObject.RENDER_OBJECT_ENUM, Image> resourceMap = new HashMap<>();
	private final Map<Model.IItem.Type, Image> inventoryMap = new HashMap<>();

	public enum INPUT_ENUM {
		KEY_RELEASED(0), KEY_PRESSED(1),
		MOUSE_RELEASED(0), MOUSE_PRESSED(1), MOUSE_MOVED(2),
		MOUSE_WHEEL_MOVED(3);

        public int value;
        //String

        INPUT_ENUM(int i){
            value = i;
        }
    }

    public View(int i, float scaleX, float scaleY) {
        stateNr = i;
		scaleGraphicsX = scaleX;
		scaleGraphicsY = scaleY;
		scaleGUIX = (float)(scaleX/Constants.ZOOM_LEVEL);
		scaleGUIY = (float)(scaleY/Constants.ZOOM_LEVEL);
    }

	public void setScale(float scaleX, float scaleY){
		scaleGraphicsX = scaleX;
		scaleGraphicsY = scaleY;
		scaleGUIX = (float)(scaleX/Constants.ZOOM_LEVEL);
		scaleGUIY = (float)(scaleY/Constants.ZOOM_LEVEL);
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

		awtFont  = new java.awt.Font("Verdana", java.awt.Font.BOLD, Constants.FONT_SIZE);
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
		graphics.scale((float)(1/Constants.ZOOM_LEVEL),(float)(1/Constants.ZOOM_LEVEL));

		if(displayPlayerNeeds){
			this.displayNeeds(gameContainer, graphics);
		}

		if (displayInventory) {
			this.displayInventory(gameContainer, graphics);
		}

		if(displayCommands){
			this.displayCommands(gameContainer, graphics);
		}

		if(displayPause){
			halfTextWidth = graphics.getFont().getWidth("Paused")/2;
			halfTextHeight = graphics.getFont().getHeight("Paused")/2;
			pauseWidth = gameContainer.getWidth()/(2*scaleGUIX)-halfTextWidth;
			pauseHeight = gameContainer.getHeight()/(2*scaleGUIY)-halfTextHeight;
			graphics.drawString("Paused", pauseWidth, pauseHeight);
		}

		if(displayBuildingInProcess){
			this.displayBuildingInProcess(gameContainer, graphics);
		}

		if(displayStats){
			this.displayStats(gameContainer, graphics);
		}
	}

	private void displayStats(GameContainer gameContainer, Graphics graphics) throws SlickException {
		//float xPosText = gameContainer.getWidth()/(2*scaleGraphicsX)-halfTextWidth;
		float xPosText = borderMargin;
		//float yPosText = gameContainer.getHeight()/(4*scaleGraphicsY);
		float yPosText = borderMargin*2;
		float ydiff = graphics.getFont().getHeight("N");
		float xLength = (float)(graphics.getFont().getWidth("Number of structures: ")*1.5);
		new Image("res/ui_needs.png").draw(xPosText-borderMargin,yPosText-borderMargin*2,xLength+borderMargin*2,ydiff*6+borderMargin*4);
		graphics.setColor(Color.black);

		graphics.drawString("Number of characters   :   " + World.nbrCharacters, xPosText, yPosText);
		graphics.drawString("Number of Animals       :   "+ World.nbrAnimals		, xPosText, yPosText+ydiff);
		graphics.drawString("Number of trees            :   "+ World.nbrTrees		, xPosText, yPosText+2*ydiff);
		graphics.drawString("Number of structures   :   "+ World.nbrStructures	, xPosText, yPosText+3*ydiff);
		graphics.drawString("Number of TIME	            :   "+ World.nbrTime			, xPosText, yPosText+4*ydiff);
		graphics.drawString("FPS                        :   "+ World.fps			, xPosText, yPosText+5*ydiff);
	}

	private void displayBuildingInProcess(GameContainer gameContainer, Graphics graphics) throws SlickException {
		float xDiffText = graphics.getFont().getWidth("1")/2;
		x=(int)(gameContainer.getWidth()/9/scaleGUIX);
		y=(int)(gameContainer.getHeight()/scaleGUIY)-x-borderMargin*2;
		Image tmpImg = new Image("res/ui_building_options.png");

		tmpImg.draw(x * 3, y, x, x);
		resourceMap.get(RenderObject.RENDER_OBJECT_ENUM.HOUSE).draw(x * 3 + x / 4, y + x / 4, x / 2, x / 2);
		graphics.drawString("1", x * 3 + x / 2 - xDiffText, y + 20);

		tmpImg.draw(x * 4, y, x, x);
		resourceMap.get(RenderObject.RENDER_OBJECT_ENUM.FARM).draw(x * 4 + x / 4, y + x / 4, x / 2, x / 2);
		graphics.drawString("2", x * 4 + x / 2 - xDiffText, y + 20);

		tmpImg.draw(x * 5, y, x, x);
		resourceMap.get(RenderObject.RENDER_OBJECT_ENUM.STOCKPILE).draw(x * 5 + x / 4, y + x / 4, x / 2, x / 2);
		graphics.drawString("3", x*5+x/2-xDiffText, y+20);
	}

	int borderMargin = 10;
	private void drawInventoryBackground(GameContainer gameContainer, Graphics graphics) throws SlickException {

		x=(int)(gameContainer.getWidth()/scaleGUIX)-Constants.SLOT_DISPLAY_SIZE*3-borderMargin*2;
		y=(int)(gameContainer.getHeight()/scaleGUIY)-Constants.SLOT_DISPLAY_SIZE*3-borderMargin*2;
		new Image("res/ui_inventory.png").draw(x,y,gameContainer.getWidth()/scaleGUIX-x+borderMargin*2,gameContainer.getHeight()/scaleGUIY-y+borderMargin*2);

		tilesPerColumn = tilesPerRow = (int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
		for (int i = tilesPerRow; i >= 1 ; i--) {
			for (int j = tilesPerColumn; j >= 1; j--) {
				x=(int) (gameContainer.getWidth()/scaleGUIX)-Constants.SLOT_DISPLAY_SIZE*j-borderMargin;
				y=(int)(gameContainer.getHeight()/scaleGUIY)-Constants.SLOT_DISPLAY_SIZE*i-borderMargin;
				graphics.setLineWidth(Constants.GRID_LINE_WIDTH);
				//graphics.drawRect(x, y, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL);
				if(inventoryIndex == (3-i)*tilesPerRow+(4-j)){
					//Selected item.
					optionStartX = gameContainer.getWidth()/scaleGUIX-tilesPerRow*Constants.SLOT_DISPLAY_SIZE-Constants.OPTION_BOX_WIDTH-borderMargin*2;
					optionStartY = gameContainer.getHeight()/scaleGUIY-Constants.OPTION_BOX_HEIGHT;
					eatStringWidth = graphics.getFont().getWidth("c: Consume");
					dropStringWidth = graphics.getFont().getWidth("d: Drop");

//					graphics.fillRect(optionStartX, optionStartY, Constants.OPTION_BOX_WIDTH / Constants.ZOOM_LEVEL, Constants.OPTION_BOX_HEIGHT / Constants.ZOOM_LEVEL);
					new Image("res/ui_inventory_text_holder.png").draw(optionStartX, optionStartY, Constants.OPTION_BOX_WIDTH, Constants.OPTION_BOX_HEIGHT);

//					graphics.setColor(new Color(180, 180, 180));
//					graphics.fillRect(x, y, Constants.SLOT_DISPLAY_SIZE / Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_SIZE / Constants.ZOOM_LEVEL);
					new Image("res/ui_inventory_selected.png").draw(x, y, Constants.SLOT_DISPLAY_SIZE, Constants.SLOT_DISPLAY_SIZE);

					graphics.setColor(Color.black);
					graphics.drawString("c: Consume", optionStartX+Constants.OPTION_MARGIN_LEFT, optionStartY+Constants.OPTION_MARGIN_TOP);
					graphics.drawString("d: Drop", optionStartX+Constants.OPTION_MARGIN_LEFT*2+eatStringWidth, optionStartY+Constants.OPTION_MARGIN_TOP);
					graphics.drawString("b: Back", optionStartX+Constants.OPTION_MARGIN_LEFT*3+eatStringWidth+dropStringWidth, optionStartY+Constants.OPTION_MARGIN_TOP);
					graphics.setColor(Color.white);
				}
			}
		}
	}

	private void displayInventory(GameContainer gameContainer, Graphics graphics) throws SlickException {

		drawInventoryBackground(gameContainer, graphics);

		i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
		j=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
		for(InventoryRender invRender : inventoryToRender) {
			x=(int)(gameContainer.getWidth()/scaleGUIX)-Constants.SLOT_DISPLAY_SIZE*i-borderMargin;
			y=(int)(gameContainer.getHeight()/scaleGUIY)-Constants.SLOT_DISPLAY_SIZE*j-borderMargin;

			Image img = new Image(invRender.type.pathToResource);
			float scale;
			if(img.getHeight()>img.getWidth()){
				scale = 0.8f*Constants.SLOT_DISPLAY_SIZE/img.getHeight();
			}else{
				scale = 0.8f*Constants.SLOT_DISPLAY_SIZE/img.getWidth();
			}
			img.draw(x+lineWidth/scaleGUIX, y+lineWidth/scaleGUIY, scale);
			//graphics.fillRect(x+Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL, y+Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL,
			//		Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL);
			graphics.setColor(Color.white);
			if(invRender.amount < 10) {
				graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT+Constants.AMOUNT_DISPLAY_MARGIN,
						y + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT);
			}else {
				graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT,
						y + Constants.SLOT_DISPLAY_SIZE-Constants.SLOT_DISPLAY_AMOUNT);
			}
			graphics.setColor(Color.white);
			i--;
			if(i==0 && j!=1){
				i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
				j--;
			}
		}
	}

	private void displayCommands(GameContainer gameContainer, Graphics graphics) throws SlickException {
		float ydiff = graphics.getFont().getHeight("N");
		float xLength = graphics.getFont().getWidth("TAB :  Change character")+5;

		float xPosText =(int)(gameContainer.getWidth()/scaleGUIX)-xLength;
		float yPosText = ydiff;
		new Image("res/ui_needs.png").draw(xPosText-borderMargin,yPosText-borderMargin*2,xLength+borderMargin*2,ydiff*6+borderMargin*4);
		graphics.setColor(Color.black);
		graphics.drawString("X     :  Show activity", xPosText, yPosText + 0 * ydiff);
		graphics.drawString("P     :  Pause", 			xPosText, yPosText + 1 * ydiff);
		graphics.drawString("F1   :  Normal speed", 	xPosText, yPosText + 2 * ydiff);
		graphics.drawString("F2   :  Faster speed", 	xPosText, yPosText + 3 * ydiff);
		graphics.drawString("F3   :  Fastest speed", 	xPosText, yPosText + 4 * ydiff);
		graphics.drawString("TAB :  Change character", 	xPosText, yPosText + 5 * ydiff);
	}

	private void displayNeeds(GameContainer gameContainer, Graphics graphics) throws SlickException {

		clacNeedDispVariables(gameContainer, graphics);
		//graphics.setColor(Color.gray);

		new Image("res/ui_needs.png").draw(0,gameContainer.getHeight()/scaleGUIY-Constants.BOX_HEIGHT, Constants.BOX_WIDTH, Constants.BOX_HEIGHT);
		//graphics.fillRect(0,gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL, Constants.BOX_WIDTH/Constants.ZOOM_LEVEL, Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL);

		graphics.setColor(Color.white);
		graphics.drawString("Name:", Constants.MARGIN_LEFT, nameStringYPos);
		graphics.drawString(characterName, barXPos, nameStringYPos);
		graphics.drawString("Age:",halfBarWidth, nameStringYPos);
		graphics.drawString(Integer.toString(characterAge), halfBarWidth+Constants.MARGIN_LEFT+graphics.getFont().getWidth("Age:"), nameStringYPos);

		//Hunger
		graphics.drawString("Hunger:", Constants.MARGIN_LEFT, hungerStringYPos);
		drawNeed(hungerPercent,hungerStringYPos,graphics);

		//Thirst
		graphics.drawString("Thirst:",Constants.MARGIN_LEFT, thirstStringYPos);
		drawNeed(thirstPercent,thirstStringYPos,graphics);

		//Energy
		graphics.drawString("Energy:",Constants.MARGIN_LEFT, energyStringYPos);
		drawNeed(energyPercent,energyStringYPos,graphics);
	}

	private void clacNeedDispVariables(GameContainer gameContainer, Graphics graphics){
		nameStringYPos = gameContainer.getHeight()/scaleGUIY-Constants.BOX_HEIGHT+Constants.MARGIN_TOP-Constants.HALF_TEXT_HEIGHT;
		hungerStringYPos = gameContainer.getHeight()/scaleGUIY-Constants.BOX_HEIGHT+Constants.MARGIN_TOP*2-Constants.HALF_TEXT_HEIGHT;
		thirstStringYPos = gameContainer.getHeight()/scaleGUIY-Constants.BOX_HEIGHT+Constants.MARGIN_TOP*3-Constants.HALF_TEXT_HEIGHT;
		energyStringYPos = gameContainer.getHeight()/scaleGUIY-Constants.BOX_HEIGHT+Constants.MARGIN_TOP*4-Constants.HALF_TEXT_HEIGHT;

		barWidth = Constants.BOX_WIDTH-3*Constants.MARGIN_LEFT-graphics.getFont().getWidth("Hunger");
		barHeight = graphics.getFont().getHeight("Hunger");

		halfBarWidth = Constants.BOX_WIDTH/2;

		barXPos = Constants.MARGIN_LEFT*2+graphics.getFont().getWidth("Hunger");

		hungerPercent = (float)playerNeeds[0]/(float)Constants.CHARACTER_HUNGER_MAX;
		thirstPercent = (float)playerNeeds[1]/(float)Constants.CHARACTER_THIRST_MAX;
		energyPercent = (float)playerNeeds[2]/(float)Constants.CHARACTER_ENERGY_MAX;
	}

	private void drawNeed(float percent,float yPoss, Graphics graphics){
		if(percent > 0) {
			if(percent < Constants.NEEDS_CRITICAL_LEVEL) {
				graphics.setColor(new Color(238,0,0));
			}else if(percent > Constants.NEEDS_CONFORTABLE_LEVEL) {
				graphics.setColor(new Color(66,205,0));
			}else{
				graphics.setColor(new Color(255,230,0));
			}
//				graphics.fillRect(barXPos + barWidth * (1 - energyPercent), energyStringYPos, barWidth - barWidth * (1 - energyPercent), barHeight);
			graphics.fillRect(barXPos,yPoss,barWidth*percent,barHeight);
		}

		graphics.setColor(Color.white);
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

	public void toggleShowCommands() {
		displayCommands = !displayCommands;
	}

	public void toggleShowStats(){
		displayStats = !displayStats;
	}

}
