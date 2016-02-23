package TestView;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import java.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tobias on 2016-02-14.
 */
public class View extends BasicGame {

	int testGathering = 0;	//testvariabel

	int farmImageId  = 9*21 +20;
	int wheatImageId = 11*21 +16;
	int stoneImageId = 19*21 +21;
	int emptyImageId = 11*21 +19;
	int collisionId = 22*22;

	Image character;
	Image treeImage;
	int charX;
	int charY;
	Dimension d;
	TiledMap map;
	List<Tree> treeList = new LinkedList<Tree>();	//För att testa spawna objekt
	List<Stone> stoneList = new LinkedList<Stone>();


	public static void main(String[] args){
		try{
			View game = new View("Test");
			Dimension screen = Toolkit.getScreenDimension();
			AppGameContainer view = new AppGameContainer(game, screen.getWidth(), screen.getHeight(), false);
			view.setTargetFrameRate(60);
			view.start();
		}
		catch(SlickException e){
			//e.printStackTrace();
			Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, e);
		}
	}  //Main initierar view

	public View(String title) {
		super(title);
	}

	public void init(GameContainer gameContainer) throws SlickException {

		map = new TiledMap("res/terrain.tmx");
		character = new Image("res/Villager16x16.png");
		treeImage = new Image("res/Tree.png");
		d = Toolkit.getScreenDimension();
		charX = d.getWidth()/2;
		charY = d.getHeight()/2;


		//Oskars testkod nedan
		treeList.add(new Tree(4*32,4*32,5));
		treeList.add(new Tree(6*32,6*32,10));
		treeList.add(new Tree(0*32,0*32,10));

		for(int i=1; i<map.getWidth(); i++) {
			for(int j=1; j<map.getHeight(); j++) {
			 	if(map.getTileId((i),(j),2) == stoneImageId)
				{
					stoneList.add(new Stone(i*32,j*32,5));
				}
			}
		}
	}

	@Override
	public void update(GameContainer gameContainer, int i) throws SlickException {

		Input in = gameContainer.getInput();
		//System.out.println(charX);
		//System.out.println(d.getWidth());

		if (in.isKeyDown(Input.KEY_LEFT) && charX > 0) {
			if(map.getTileId((charX-1)/32, charY/32, 3) != collisionId)
				charX -= 1;
		}
		if (in.isKeyDown(Input.KEY_RIGHT) && charX < d.getWidth() - character.getWidth()) {
			if (map.getTileId((charX + 1) / 32, charY / 32, 3) != collisionId)
				charX += 1;
		}
		if (in.isKeyDown(Input.KEY_UP) && charY > 0) {
			if (map.getTileId(charX / 32, (charY-1) / 32, 3) != collisionId)
				charY -= 1;
		}
		if (in.isKeyDown(Input.KEY_DOWN) && charY < d.getHeight() - character.getHeight()) {
			if (map.getTileId(charX / 32, (charY+1) / 32, 3) != collisionId)
				charY += 1;
		}
		if(in.isKeyPressed(Input.KEY_T))
		{
			for (Tree t: treeList) {
				if((charX>(t.getXPos()-32) && charX<(t.getXPos()+64)) && (charY>(t.getYPos()-32) && charY<(t.getYPos()+64)))
				{
					t.gather();
					testGathering++;
					if(t.depleted)
					{
						treeList.remove(t);
					}
					break;
				}
			}
		}
		if(in.isKeyPressed(Input.KEY_S))
		{
			for (Stone s: stoneList) {
				if((charX>(s.getXPos()-32) && charX<(s.getXPos()+64)) && (charY>(s.getYPos()-32) && charY<(s.getYPos()+64)))
				{
					s.gather();
					testGathering++;
					if(s.depleted)
					{
						map.setTileId(s.getXPos()/32,s.getYPos()/32, 2, 0);
						stoneList.remove(s);
					}
					break;
				}
			}
		}
		if (in.isKeyDown(Input.KEY_ESCAPE))
		{
			gameContainer.exit();
		}
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		map.render(0,0,0); //renderar bakgrund...
		map.render(0,0,1); //cover...
		map.render(0,0,2); // och resurser.

		character.draw(charX, charY);

		for (Tree t: treeList) {
			graphics.drawImage(treeImage,t.xPos, t.yPos);
		}
		graphics.drawString(Integer.toString(testGathering),32,32);
		graphics.drawString(Integer.toString(stoneList.size()),64,32);
		graphics.drawString(Integer.toString(map.getTileId(charX/32,charY/32,2)), 96,32);
	}
}