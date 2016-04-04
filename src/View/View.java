package View;

import Model.Constants;
import Toolkit.RenderObject;
import Toolkit.InventoryRender;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.font.*;

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

	private boolean displayInventory = false;
	private boolean displayPlayerNeeds = false;
	private boolean displayPause = false;

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

    public View(int i, float scaleX, float scaleY) {
        stateNr = i;
		scaleGraphicsX = scaleX;
		scaleGraphicsY = scaleY;
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

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.scale(scaleGraphicsX,scaleGraphicsY);

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
						resourceMap.get(obj.getRenderType()).draw(obj.getX(), obj.getY(), width, height);
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
		TrueTypeFont font;
		java.awt.Font awtFont = new java.awt.Font("AngelCodeFont", java.awt.Font.BOLD, Constants.FONT_SIZE/Constants.ZOOM_LEVEL);
		font = new TrueTypeFont(awtFont, false);
		if(displayPlayerNeeds){
			float hungerStringYPos = gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL+Constants.MARGIN_TOP/Constants.ZOOM_LEVEL-Constants.HALF_TEXT_HEIGHT;
			float thirstStringYPos = gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL+Constants.MARGIN_TOP/Constants.ZOOM_LEVEL*2-Constants.HALF_TEXT_HEIGHT;
			float energyStringYPos = gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL+Constants.MARGIN_TOP/Constants.ZOOM_LEVEL*3-Constants.HALF_TEXT_HEIGHT;

			float barWidth = Constants.BOX_WIDTH/Constants.ZOOM_LEVEL-3*Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL-graphics.getFont().getWidth("Hunger");
			float barHeight = graphics.getFont().getHeight("Hunger")/Constants.ZOOM_LEVEL;

			float barXPos = Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL*2+graphics.getFont().getWidth("Hunger");

			float hungerPercent = (float)playerNeeds[0]/(float)Constants.CHARACTER_HUNGER_MAX;
			float thirstPercent = (float)playerNeeds[1]/(float)Constants.CHARACTER_THIRST_MAX;
			float energyPercent = (float)playerNeeds[2]/(float)Constants.CHARACTER_ENERGY_MAX;

			graphics.setColor(Color.gray);
			graphics.fillRect(0,gameContainer.getHeight()/scaleGraphicsY-Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL, Constants.BOX_WIDTH/Constants.ZOOM_LEVEL, Constants.BOX_HEIGHT/Constants.ZOOM_LEVEL);
			graphics.setColor(Color.white);
			graphics.setFont(font);
			graphics.drawString("Hunger:",Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL, hungerStringYPos);
			graphics.drawRect(barXPos, hungerStringYPos,barWidth,barHeight);
			if(hungerPercent < 0.2)
				graphics.setColor(Color.red);
			if(hungerPercent > 0)
				graphics.fillRect(barXPos+barWidth*(1-hungerPercent), hungerStringYPos, barWidth-barWidth*(1-hungerPercent), barHeight);
			graphics.setColor(Color.white);
			graphics.drawString("Thirst:",Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL, thirstStringYPos);
			if(thirstPercent < 0.2)
				graphics.setColor(Color.red);
			if(thirstPercent > 0)
				graphics.fillRect(barXPos+barWidth*(1-thirstPercent), thirstStringYPos, barWidth-barWidth*(1-thirstPercent), barHeight);
			graphics.setColor(Color.white);
			graphics.drawRect(barXPos, thirstStringYPos,barWidth,barHeight);
			graphics.drawString("Energy:",Constants.MARGIN_LEFT/Constants.ZOOM_LEVEL, energyStringYPos);
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
			int tilesPerRow, tilesPerColumn;
			tilesPerColumn = tilesPerRow = (int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			float lineWidth = Constants.GRID_LINE_WIDTH;
			for (int i = tilesPerRow; i >= 1 ; i--) {
				for (int j = tilesPerColumn; j >= 1; j--) {
					x=(int)(gameContainer.getWidth()/scaleGraphicsX)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*j;
					y=(int)(gameContainer.getHeight()/scaleGraphicsY)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*i;
					graphics.setLineWidth(Constants.GRID_LINE_WIDTH);
					graphics.drawRect(x-lineWidth, y-lineWidth, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL);
					if(inventoryIndex == (3-i)*tilesPerRow+(4-j)){
						float optionStartX = gameContainer.getWidth()-tilesPerRow*Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-lineWidth-Constants.OPTION_BOX_WIDTH/Constants.ZOOM_LEVEL;
						float optionStartY = gameContainer.getHeight()-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL/2;
						int eatStringWidth = graphics.getFont().getWidth("c: Consume");
						int dropStringWidth = graphics.getFont().getWidth("d: Drop");
						graphics.fillRect(x-lineWidth, y-lineWidth, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL);
						graphics.fillRect(optionStartX, optionStartY, Constants.OPTION_BOX_WIDTH/Constants.ZOOM_LEVEL, Constants.OPTION_BOX_HEIGHT/Constants.ZOOM_LEVEL);
						graphics.setColor(Color.black);
						graphics.drawString("c: Consume", optionStartX+Constants.OPTION_MARGIN_LEFT, optionStartY+Constants.OPTION_MARGIN_TOP);
						graphics.drawString("d: Drop", optionStartX+Constants.OPTION_MARGIN_LEFT*2+eatStringWidth, optionStartY+Constants.OPTION_MARGIN_TOP);
						graphics.drawString("b: Back", optionStartX+Constants.OPTION_MARGIN_LEFT*3+eatStringWidth+dropStringWidth, optionStartY+Constants.OPTION_MARGIN_TOP);
						graphics.setColor(Color.white);
					}
				}
			}

			int i,j;
			i=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			j=(int)Math.sqrt(Constants.MAX_INVENTORY_SLOTS);
			for(InventoryRender invRender : inventoryToRender) {
				x=(int)(gameContainer.getWidth()/scaleGraphicsX)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*i;
				y=(int)(gameContainer.getHeight()/scaleGraphicsY)-Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL*j;

				new Image(invRender.type.pathToResource).draw(x-lineWidth/2, y-lineWidth/2, 1f/Constants.ZOOM_LEVEL);
				graphics.fillRect(x+Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL-lineWidth, y+Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL-lineWidth,
						Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL, Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL);
				graphics.setColor(Color.black);
				if(invRender.amount < 10) {
					graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL+Constants.AMOUNT_DISPLAY_MARGIN-lineWidth,
							y + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL-lineWidth);
				}else {
					graphics.drawString(Integer.toString(invRender.amount), x + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL-lineWidth,
							y + Constants.SLOT_DISPLAY_SIZE/Constants.ZOOM_LEVEL-Constants.SLOT_DISPLAY_AMOUNT/Constants.ZOOM_LEVEL-lineWidth);
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
			float halfTextWidth = graphics.getFont().getWidth("Paused")/2;
			float halfTextHeight = graphics.getFont().getHeight("Paused")/2;
			float width = gameContainer.getWidth()/(2*scaleGraphicsX)-halfTextWidth;
			float height = gameContainer.getHeight()/(2*scaleGraphicsY)-halfTextHeight;
			graphics.drawString("Paused", width, height);
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


	public void setItemInFocus(int index){
		inventoryIndex = index;
	}


}
