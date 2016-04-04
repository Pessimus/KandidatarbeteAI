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

	//Temp constant for the scalar in controller
	public static final			double		STANDARD_SCREEN_WIDTH				=	1920.0;
	public static final			double		STANDARD_SCREEN_HEIGHT				=	1080.0;
	public static final			double		GRAPHICS_SCALE_X					=	SCREEN_WIDTH/STANDARD_SCREEN_WIDTH;
	public static final			double		GRAPHICS_SCALE_Y					=	SCREEN_HEIGHT/STANDARD_SCREEN_HEIGHT;

	public static final 		double 		SCREEN_EDGE_TRIGGER_MAX_X			=	SCREEN_WIDTH * 0.8;
	public static final 		double 		SCREEN_EDGE_TRIGGER_MAX_Y			=	SCREEN_HEIGHT * 0.8;
	public static final 		double 		SCREEN_EDGE_TRIGGER_MIN_X			=	SCREEN_WIDTH * 0.08;
	public static final 		double 		SCREEN_EDGE_TRIGGER_MIN_Y			=	SCREEN_HEIGHT * 0.08;


	public static final			float		SCREEN_SCROLL_SPEED_X				=	600f; // Pixels per second!
	public static final			float		SCREEN_SCROLL_SPEED_Y				=	600f; // Pixels per second!

	public static final 		int 		DEFAULT_WORLD_VIEW_X				= 	100;
	public static final 		int 		DEFAULT_WORLD_VIEW_Y				= 	100;

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
	public static final 		int			CHARACTER_UPDATE_INTERVAL			=	240;

	public static final 		double		CHARACTER_COLLISION_RADIUS			=	8;
	public static final 		double		CHARACTER_INTERACTION_RADIUS		=	20;
	public static final 		double		CHARACTER_SURROUNDING_RADIUS		=	150;

	public static final 		int			CHARACTER_HUNGER_MAX				=	100;
	public static final 		int			CHARACTER_THIRST_MAX				=	100;
	public static final 		int			CHARACTER_ENERGY_MAX				=	100;

	public static final			int			CHARACTER_HUNGER_UPDATE				=	360;
	public static final			int			CHARACTER_THIRST_UPDATE				=	270;
	public static final			int			CHARACTER_ENERGY_UPDATE				=	960;
	public static final			int			CHARACTER_AGE_UPDATE				=	600;

	public static final			int			CHARACTER_HUNGER_CHANGE				=	1;
	public static final			int			CHARACTER_THIRST_CHANGE				=	1;
	public static final			int			CHARACTER_ENERGY_CHANGE				=	1;

	//----------- Inventory Constants ------------- \\
	//Has to be a power of 2. Ex 1,4,9,16,25,36...
	public static final			int 		MAX_INVENTORY_SLOTS					=	16;

	public static final			int			SLOT_DISPLAY_SIZE					=	64;
	public static final			int			SLOT_DISPLAY_AMOUNT					=	20;
	public static final			int 		AMOUNT_DISPLAY_MARGIN				=	4;
	public static final			float		GRID_LINE_WIDTH						=	5f;
	public static final			int			MAX_AMOUNT							=	99;

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

	public static final 		int			CROP_HUNGER_CHANGE_CONSUME			=	15;
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

	//FISH\\
	public static final 		int			FISH_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			FISH_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			FISH_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			FISH_HUNGER_CHANGE_CONSUME			=	20;
	public static final 		int			FISH_ENERGY_CHANGE_CONSUME			=	5;
	public static final 		int			FISH_THIRST_CHANGE_CONSUME			=	-3;

	public static final 		int			FISH_HUNGER_CHANGE_ATTACK			=	30;
	public static final 		int			FISH_ENERGY_CHANGE_ATTACK			=	-5;
	public static final 		int			FISH_THIRST_CHANGE_ATTACK			=	-3;

	//COOKED FISH\\
	public static final 		int			COOKED_FISH_HUNGER_CHANGE_INTERACT	=	0;
	public static final 		int			COOKED_FISH_ENERGY_CHANGE_INTERACT	=	0;
	public static final 		int			COOKED_FISH_THIRST_CHANGE_INTERACT	=	0;

	public static final 		int			COOKED_FISH_HUNGER_CHANGE_CONSUME	=	30;
	public static final 		int			COOKED_FISH_ENERGY_CHANGE_CONSUME	=	10;
	public static final 		int			COOKED_FISH_THIRST_CHANGE_CONSUME	=	-3;

	public static final 		int			COOKED_FISH_HUNGER_CHANGE_ATTACK	=	30;
	public static final 		int			COOKED_FISH_ENERGY_CHANGE_ATTACK	=	-5;
	public static final 		int			COOKED_FISH_THIRST_CHANGE_ATTACK	=	-3;

	//WOOD\\
	public static final 		int			WOOD_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			WOOD_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			WOOD_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			WOOD_HUNGER_CHANGE_CONSUME			=	-5;
	public static final 		int			WOOD_ENERGY_CHANGE_CONSUME			=	0;
	public static final 		int			WOOD_THIRST_CHANGE_CONSUME			=	-5;

	public static final 		int			WOOD_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			WOOD_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			WOOD_THIRST_CHANGE_ATTACK			=	0;

	//WATER\\
	public static final 		int			WATER_HUNGER_CHANGE_INTERACT		=	0;
	public static final 		int			WATER_ENERGY_CHANGE_INTERACT		=	0;
	public static final 		int			WATER_THIRST_CHANGE_INTERACT		=	0;

	public static final 		int			WATER_HUNGER_CHANGE_CONSUME			=	0;
	public static final 		int			WATER_ENERGY_CHANGE_CONSUME			=	5;
	public static final 		int			WATER_THIRST_CHANGE_CONSUME			=	30;

	public static final 		int			WATER_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			WATER_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			WATER_THIRST_CHANGE_ATTACK			=	0;

	//MEAT\\
	public static final 		int			MEAT_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			MEAT_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			MEAT_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			MEAT_HUNGER_CHANGE_CONSUME			=	50;
	public static final 		int			MEAT_ENERGY_CHANGE_CONSUME			=	20;
	public static final 		int			MEAT_THIRST_CHANGE_CONSUME			=	-20;

	public static final 		int			MEAT_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			MEAT_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			MEAT_THIRST_CHANGE_ATTACK			=	0;

	//COOKED MEAT\\
	public static final 		int			COOKED_MEAT_HUNGER_CHANGE_INTERACT	=	0;
	public static final 		int			COOKED_MEAT_ENERGY_CHANGE_INTERACT	=	0;
	public static final 		int			COOKED_MEAT_THIRST_CHANGE_INTERACT	=	0;

	public static final 		int			COOKED_MEAT_HUNGER_CHANGE_CONSUME	=	65;
	public static final 		int			COOKED_MEAT_ENERGY_CHANGE_CONSUME	=	40;
	public static final 		int			COOKED_MEAT_THIRST_CHANGE_CONSUME	=	-20;

	public static final 		int			COOKED_MEAT_HUNGER_CHANGE_ATTACK	=	0;
	public static final 		int			COOKED_MEAT_ENERGY_CHANGE_ATTACK	=	0;
	public static final 		int			COOKED_MEAT_THIRST_CHANGE_ATTACK	=	0;

	// ----------- AI Constants ----------- \\

	public static final			int			GATHER_CROPS_STATE_TIME				=	90;			// Number of updates ("frames") that the character will wait when gathering crops
	public static final			int			GATHER_STONE_STATE_TIME				=	90;			// Number of updates ("frames") that the character will wait when gathering crops
	public static final			int			GATHER_WATER_STATE_TIME				=	30;			// Number of updates ("frames") that the character will wait when gathering water
	public static final			int			GATHER_MEAT_STATE_TIME				= 	60;			// Number of updates ("frames") that the character will wait when gathering meat
	public static final			int			GATHER_FISH_STATE_TIME				= 	90;			// Number of updates ("frames") that the character will wait when gathering fish
	public static final			int			GATHER_WOOD_STATE_TIME				= 	60;			// Number of updates ("frames") that the character will wait when gathering fish
	public static final			int			EAT_STATE_TIME						=	15;			// Number of updates ("frames") that the character will wait when gathering water
	public static final			int			DRINK_STATE_TIME					=	15;			// Number of updates ("frames") that the character will wait when gathering water

	// ----------- PATHFINDER Constants ----------- \\

	public static final		int				PATHFINDER_GRID_SIZE				=	16;
	public static final		double			PATHFINDER_PERPENDICULAR_COST		=	1.;
	public static final		double			PATHFINDER_DIAGONAL_COST			=	1.4;
	public static final 	Pathfinder		PATHFINDER_OBJECT					=	new Pathfinder(PATHFINDER_GRID_SIZE, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, PATHFINDER_PERPENDICULAR_COST, PATHFINDER_DIAGONAL_COST);

	// ----------- Display Needs ----------- \\
	public static final		int				BOX_WIDTH							=	600;
	public static final		int				BOX_HEIGHT							=	150;
	public static final		int				MARGIN_FROM_TOP						=	BOX_HEIGHT/4;
	public static final		int				MARGIN_FROM_LEFT					=	20;
	public static final 	int				HALF_TEXT_HEIGHT					=	9;

	// ----------- Display Item highlight options ----------- \\
	public static final		int				OPTION_BOX_WIDTH					=	500;
	public static final		int				OPTION_BOX_HEIGHT					=	SLOT_DISPLAY_SIZE/2;
	public static final		int				OPTION_MARGIN_TOP					=	OPTION_BOX_HEIGHT/6;
	public static final		int				OPTION_MARGIN_LEFT					=	60;
}
