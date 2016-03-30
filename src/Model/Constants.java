package Model;

import Controller.Pathfinder;

import java.awt.*;

/**
 * Created by Tobias on 2016-03-09.
 */
public final class Constants {
	public static final			String		GAME_TITLE							=	"AI for NPCs";

	// ----------- Controller Constants ----------- \\

	public static final 		double 		SCREEN_WIDTH 						= 	Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
	public static final 		double 		SCREEN_HEIGHT 						= 	Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;

	public static final 		double 		SCREEN_EDGE_TRIGGER_MAX_X			=	SCREEN_WIDTH * 0.8;
	public static final 		double 		SCREEN_EDGE_TRIGGER_MAX_Y			=	SCREEN_HEIGHT * 0.8;
	public static final 		double 		SCREEN_EDGE_TRIGGER_MIN_X			=	SCREEN_WIDTH * 0.08;
	public static final 		double 		SCREEN_EDGE_TRIGGER_MIN_Y			=	SCREEN_HEIGHT * 0.08;

	public static final			float		SCREEN_SCROLL_SPEED_X				=	200f; // Pixels per second!
	public static final			float		SCREEN_SCROLL_SPEED_Y				=	200f; // Pixels per second!

	public static final 		float 		DEFAULT_WORLD_VIEW_X				= 	100f;
	public static final 		float 		DEFAULT_WORLD_VIEW_Y				= 	100f;

	public static final			float		WORLD_WIDTH							=	9600f;
	public static final			float		WORLD_HEIGHT						=	9600f;
	public static final 		int 		TARGET_FRAMERATE 					= 	60;
	public static final 		boolean		GAME_GRAB_MOUSE						= 	false;
	public static final 		boolean 	RUN_IN_FULLSCREEN 					= 	false;

	public static final 		int 		CONTROLLER_UPDATE_INTERVAL_NORMAL 	= 	60; // Updates per second
	public static final 		int 		CONTROLLER_UPDATE_INTERVAL_FASTER 	= 	120; // Updates per second
	public static final 		int 		CONTROLLER_UPDATE_INTERVAL_FASTEST 	= 	240; // Updates per second

	public static final 		int 		WORLD_TILE_SIZE						= 	32;
	public static final 		int 		VERTICAL_TILES						=	(int) (SCREEN_HEIGHT /WORLD_TILE_SIZE);
	public static final 		int 		HORIZONTAL_TILES 					=	(int) (SCREEN_WIDTH /WORLD_TILE_SIZE);

	//----------- Character Constants ------------- \\
	public static final			int			PLAYER_CHARACTER_KEY				= 	1;
	public static final			float		CHARACTER_WALK_SPEED				=	6f;
	public static final			float		CHARACTER_RUN_SPEED					=	12f;
	public static final 		int			CHARACTER_UPDATE_INTERVALL			=	60;

	public static final 		double		CHARACTER_COLLISION_RADIUS			=	5;
	public static final 		double		CHARACTER_INTERACTION_RADIUS		=	20;
	public static final 		double		CHARACTER_SURROUNDING_RADIUS		=	50;

	public static final 		int			CHARACTER_HUNGER_MAX				=	100;
	public static final 		int			CHARACTER_THIRST_MAX				=	100;
	public static final 		int			CHARACTER_ENERGY_MAX				=	100;

	public static final			int			CHARACTER_HUNGER_UPDATE				=	20;
	public static final			int			CHARACTER_THIRST_UPDATE				=	40;
	public static final			int			CHARACTER_ENERGY_UPDATE				=	20;
	public static final			int			CHARACTER_AGE_UPDATE				=	60;

	public static final			int			CHARACTER_HUNGER_CHANGE				=	1;
	public static final			int			CHARACTER_THIRST_CHANGE				=	1;
	public static final			int			CHARACTER_ENERGY_CHANGE				=	1;

	//----------- Inventory Constants ------------- \\

	public static final			int 		MAX_INVENTORY_SLOTS					=	9;

	//----------- Animal Constants ------------- \\
	public static final 		double		ANIMAL_COLLISION_RADIUS				=	5;
	public static final 		double		ANIMAL_INTERACTION_RADIUS			=	20;
	public static final 		double		ANIMAL_SURROUNDING_RADIUS			=	50;

	public static final 		int			ANIMAL_HUNGER_CHANGE_CONSUME		=	20;
	public static final 		int			ANIMAL_ENERGY_CHANGE_CONSUME		=	-5;
	public static final 		int			ANIMAL_THIRST_CHANGE_CONSUME		=	0;

	public static final 		int			ANIMAL_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			ANIMAL_ENERGY_CHANGE_ATTACK			=	-10;
	public static final 		int			ANIMAL_THIRST_CHANGE_ATTACK			=	0;

	//-------------------------- Resource Constants -------------------------- \\
	public static final 		int 		TREE_UPDATE_INTERVALL				=	120;
	public static final 		int			TREE_INCREASE_AMOUNT				=	10;

	//------------------------------Item Constants------------------------------\\
	//CROPS\\
	public static final 		int			CROP_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			CROP_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			CROP_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			CROP_HUNGER_CHANGE_CONSUME			=	10;
	public static final 		int			CROP_ENERGY_CHANGE_CONSUME			=	1;
	public static final 		int			CROP_THIRST_CHANGE_CONSUME			=	-1;

	public static final 		int			CROP_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			CROP_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			CROP_THIRST_CHANGE_ATTACK			=	0;

	//STONE\\
	public static final 		int			STONE_HUNGER_CHANGE_INTERACT		=	0;
	public static final 		int			STONE_ENERGY_CHANGE_INTERACT		=	0;
	public static final 		int			STONE_THIRST_CHANGE_INTERACT		=	0;

	public static final 		int			STONE_HUNGER_CHANGE_CONSUME			=	-20;
	public static final 		int			STONE_ENERGY_CHANGE_CONSUME			=	-10;
	public static final 		int			STONE_THIRST_CHANGE_CONSUME			=	-20;

	public static final 		int			STONE_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			STONE_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			STONE_THIRST_CHANGE_ATTACK			=	0;

	//GOLD\\
	public static final 		int			GOLD_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			GOLD_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			GOLD_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			GOLD_HUNGER_CHANGE_CONSUME			=	-20;
	public static final 		int			GOLD_ENERGY_CHANGE_CONSUME			=	-10;
	public static final 		int			GOLD_THIRST_CHANGE_CONSUME			=	-20;

	public static final 		int			GOLD_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			GOLD_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			GOLD_THIRST_CHANGE_ATTACK			=	0;

	// ----------- PATHFINDER Constants ----------- \\

	public static final		int				PATHFINDER_GRID_SIZE				=	16;
	public static final		double			PATHFINDER_PERPENDICULAR_COST		=	1.;
	public static final		double			PATHFINDER_DIAGONAL_COST			=	1.4;
	public static final 	Pathfinder		PATHFINDER_OBJECT					=	new Pathfinder(PATHFINDER_GRID_SIZE, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, PATHFINDER_PERPENDICULAR_COST, PATHFINDER_DIAGONAL_COST);
}
