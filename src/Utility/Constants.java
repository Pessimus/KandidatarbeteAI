package Utility;

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

    /*
    public static final         int         GRASS_START_PIXEL_X                 =   650;
    public static final         int         GRASS_END_PIXEL_X                   =   8630;
    public static final         int         GRASS_START_PIXEL_Y                 =   770;
    public static final         int         GRASS_END_PIXEL_Y                   =   8750;

    public static final         int         SAND_START_PIXEL_X                  =   390;
    public static final         int         SAND_END_PIXEL_X                    =   8890;
    public static final         int         SAND_START_PIXEL_Y                  =   550;
    public static final         int         SAND_END_PIXEL_Y                    =   8970;
    */

    public static final         int         VIEW_BORDER_WIDTH                   =   650;
    public static final         int         VIEW_BORDER_HEIGHT                  =   770;


	//Zoom level not final
	public static 				int			ZOOM_LEVEL							= 	1;
	public static final			int			FONT_SIZE							=	18;

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

	public static final			float		WORLD_WIDTH                         =   7980f;
	public static final			float		WORLD_HEIGHT            			=	7980f;
	public static final 		int 		TARGET_FRAMERATE 					= 	60;
	public static final 		boolean		GAME_GRAB_MOUSE						= 	false;
	public static final 		boolean 	RUN_IN_FULLSCREEN 					= 	false;

	public static final 		int 		CONTROLLER_UPDATE_INTERVAL_NORMAL 	= 	60; // Updates per second
	public static final 		int 		CONTROLLER_UPDATE_INTERVAL_FASTER 	= 	240; // Updates per second
	public static final 		int 		CONTROLLER_UPDATE_INTERVAL_FASTEST 	= 	600; // Updates per second

	public static final 		int 		WORLD_TILE_SIZE						= 	32;
	public static final 		int 		VERTICAL_TILES						=	(int) (SCREEN_HEIGHT /WORLD_TILE_SIZE);
	public static final 		int 		HORIZONTAL_TILES 					=	(int) (SCREEN_WIDTH /WORLD_TILE_SIZE);

	//----------- Character Constants ------------- \\
	public static final			int			PLAYER_CHARACTER_KEY				= 	0;
	public static final			float		CHARACTER_WALK_SPEED				=	4f;
	public static final			float		CHARACTER_RUN_SPEED					=	7f;
	public static final 		int			CHARACTER_UPDATE_INTERVAL			=	60;


	public static final			int			NUMBER_OF_NPCS						=	20; // Used in initialization of Controller to deterimine the number of NPCs to spawn


	public static final			int			NUMBER_OF_FEMALE_NAMES				=	4275;
	public static final			int			NUMBER_OF_MALE_NAMES				=	1219;

	public static final 		int			CHARACTER_ATTACKED_TIME				=	2*60;
	public static final 		int			CHARACTER_ATTACKED_ENERGY_CHANGE	=	10;


	public static final 		double		CHARACTER_COLLISION_RADIUS			=	12;
	public static final 		double		CHARACTER_INTERACTION_RADIUS		=	28;
	public static final 		double		CHARACTER_SURROUNDING_RADIUS		=	500;

	public static final 		int			CHARACTER_HUNGER_MAX				=	100;
	public static final 		int			CHARACTER_THIRST_MAX				=	100;
	public static final 		int			CHARACTER_ENERGY_MAX				=	100;

    public static final         int         CHARACTER_SOCIAL_MAX                =   100;
	public static final 		int			CHARACTER_SOCIAL_NEEDS_MODIFIER		=	100; //(1/100)
    public static final         int         CHARACTER_INTIMACY_MAX              =   100;

	public static final			int			CHARACTER_HUNGER_UPDATE				=	200;
	public static final			int			CHARACTER_THIRST_UPDATE				=	150;
	public static final			int			CHARACTER_ENERGY_UPDATE				=	200;
	public static final 		int 		CHARACTER_SOCIAL_UPDATE 			= 	300;

	public static final			int			CHARACTER_AGE_UPDATE				=	60*60*5;
	public static final 		int			CHARACTER_MIN_DEATH_AGE				=	80;
	public static final 		int			CHARACTER_MAX_AGE					=	100;
	public static final			int			CHARACTER_DEATH_AGE_SPANN			=	CHARACTER_MAX_AGE-CHARACTER_MIN_DEATH_AGE;

	public static final			int			CHARACTER_PREGNANCY_TIME			=	(CHARACTER_AGE_UPDATE/4)*3;

	public static final			int			CHARACTER_HUNGER_CHANGE				=	2;
	public static final			int			CHARACTER_THIRST_CHANGE				=	1;
	public static final			int			CHARACTER_ENERGY_CHANGE				=	1;
	public static final			int			CHARACTER_SOCIAL_CHANGE				=	1;

	//----------- Inventory Constants ------------- \\
	//Has to be a power of 2. Ex 1,4,9,16,25,36...
	public static final			int 		MAX_INVENTORY_SLOTS					=	9;

	public static final			int			SLOT_DISPLAY_SIZE					=	64;
	public static final			int			SLOT_DISPLAY_AMOUNT					=	20;
	public static final			int 		AMOUNT_DISPLAY_MARGIN				=	4;
	public static final			float		GRID_LINE_WIDTH						=	5f;
	public static final			int			MAX_AMOUNT							=	99;

	//----------- Structure Constants ------------- \\
	public static final			double		HOUSE_COLLISION_RADIUS				=	50;
	public static final			double		STOCKPILE_COLLISION_RADIUS			=	50;
	public static final			double		FARM_COLLISION_RADIUS				=	100;
	public static final 		int 		HOUSE_MAX_CAPACITY					=	2;

	public static final			int			MAX_INTEGRETY_HOUSE					=	10;
	public static final			int			MAX_INTEGRETY_FARM					=	10;
	public static final			int			MAX_INTEGRETY_STOCKPILE				=	10;
	public static final			int 		FARM_BUILD_TIME						=	60*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			FARM_INTERACTION_TIME				=	10*60;
	public static final			int			FARM_ATTACKED_TIME					=	1*60;
	public static final			int 		HOUSE_BUILD_TIME					=	35*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			HOUSE_INTERACTION_TIME				=	20*60;
	public static final			int			HOUSE_ATTACKED_TIME					=	1*60;
	public static final			int 		STOCKPILE_BUILD_TIME				=	20*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			STOCKPILE_INTERACTION_TIME			=	0;
	public static final			int			STOCKPILE_ATTACKED_TIME				=	1*60;


	public static final			int			HOUSE_WOOD_COST						=	60;
	public static final			int			HOUSE_STONE_COST					=	40;
	public static final			int			FARM_WOOD_COST						=	80;
	public static final			int			FARM_STONE_COST						=	10;
	public static final			int			STOCKPILE_WOOD_COST					=	30;
	public static final			int			STOCKPILE_STONE_COST				=	15;


	//----------- Animal Constants ------------- \\
	public static final 		double		ANIMAL_COLLISION_RADIUS				=	30;
	public static final 		double		ANIMAL_INTERACTION_RADIUS			=	100;
	public static final 		double		ANIMAL_SURROUNDING_RADIUS			=	200;
	public static final			int 		ANIMAL_MIN_STEP_AMOUNT				=	600;
	public static final 		int			ANIMAL_STEP_AMOUNT_DIFF				=	60*5;

	public static final 		int 		ANIMAL_INTERACTED_TIME				=	20*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final 		int			ANIMAL_CONSUMED_TIME				=	5*60;
	public static final 		int			ANIMAL_ATTACKED_TIME				=	5*CONTROLLER_UPDATE_INTERVAL_NORMAL;

	public static final 		int			ANIMAL_HUNGER_CHANGE_CONSUME		=	20;
	public static final 		int			ANIMAL_ENERGY_CHANGE_CONSUME		=	-5;
	public static final 		int			ANIMAL_THIRST_CHANGE_CONSUME		=	0;

	public static final 		int			ANIMAL_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			ANIMAL_ENERGY_CHANGE_ATTACK			=	-10;
	public static final 		int			ANIMAL_THIRST_CHANGE_ATTACK			=	0;

	public static final			int			ANIMAL_SOCIAL_CHANGE_INTERACT		=	10;

	public static final 		int 		ANIMAL_MATING_COUNTER_MAX			=	2400;//60*60;
	public static final 		int 		ANIMAL_MATING_COUNTER_INTERVALL		=	60*10;

	//-------------------------- Resource Constants -------------------------- \\
	public static final 		int 		TREE_UPDATE_INTERVAL 				=	12000;

	public static final			float		TREE_SPAWN_RADIUS					=	500;
	public static final 		float		TREE_COLLISION_RADIUS				=	75;
	public static final			double		LAKE_COLLISION_RADIUS				=	100;
	public static final			double		STONE_COLLISION_RADIUS				= 	10;
	public static final			int 		WOOD_MAX_RESOURCES					=	20;
	public static final			int 		WOOD_INITIAL_RESOURCES				=	20;
	public static final 		int 		WOOD_RESOURCE_GAIN					=	1;

	public static final			float 		CROPS_COLLISION_RADIUS 				=	40;
	public static final			int			CROPS_INITIAL_AMOUNT				=	100;
	public static final			int			CROPS_YEILD_AMOUNT					=	10;

	public static final			int			CROPS_INTERACTED_TIME				=	6*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			CROPS_ATTACKED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			CROPS_CONSUMED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			GOLD_INTERACTED_TIME				=	8*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			GOLD_ATTACKED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			GOLD_CONSUMED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			MEAT_INTERACTED_TIME				=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			MEAT_ATTACKED_TIME					=	2*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			MEAT_CONSUMED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			STONE_INTERACTED_TIME				=	7*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			STONE_ATTACKED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			STONE_CONSUMED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			WATER_INTERACTED_TIME				=	3*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			WATER_ATTACKED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			WATER_CONSUMED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			WOOD_INTERACTED_TIME				=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			WOOD_ATTACKED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;
	public static final			int			WOOD_CONSUMED_TIME					=	1*CONTROLLER_UPDATE_INTERVAL_NORMAL;


	//------------------------------Item Constants------------------------------\\
	//CROPS\\
	public static final 		int			CROP_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			CROP_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			CROP_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			CROP_HUNGER_CHANGE_CONSUME			=	3;
	public static final 		int			CROP_ENERGY_CHANGE_CONSUME			=	1;
	public static final 		int			CROP_THIRST_CHANGE_CONSUME			=	-1;

	public static final 		int			CROP_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			CROP_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			CROP_THIRST_CHANGE_ATTACK			=	0;

	//STONE\\
	public static final 		int			STONE_HUNGER_CHANGE_INTERACT		=	0;
	public static final 		int			STONE_ENERGY_CHANGE_INTERACT		=	0;
	public static final 		int			STONE_THIRST_CHANGE_INTERACT		=	0;

	public static final 		int			STONE_HUNGER_CHANGE_CONSUME			=	-2;
	public static final 		int			STONE_ENERGY_CHANGE_CONSUME			=	-1;
	public static final 		int			STONE_THIRST_CHANGE_CONSUME			=	-2;

	public static final 		int			STONE_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			STONE_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			STONE_THIRST_CHANGE_ATTACK			=	0;

	//GOLD\\
	public static final 		int			GOLD_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			GOLD_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			GOLD_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			GOLD_HUNGER_CHANGE_CONSUME			=	-2;
	public static final 		int			GOLD_ENERGY_CHANGE_CONSUME			=	-1;
	public static final 		int			GOLD_THIRST_CHANGE_CONSUME			=	-2;

	public static final 		int			GOLD_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			GOLD_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			GOLD_THIRST_CHANGE_ATTACK			=	0;

	//FISH\\
	public static final 		int			FISH_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			FISH_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			FISH_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			FISH_HUNGER_CHANGE_CONSUME			=	33;
	public static final 		int			FISH_ENERGY_CHANGE_CONSUME			=	1;
	public static final 		int			FISH_THIRST_CHANGE_CONSUME			=	-1;

	public static final 		int			FISH_HUNGER_CHANGE_ATTACK			=	30;
	public static final 		int			FISH_ENERGY_CHANGE_ATTACK			=	-5;
	public static final 		int			FISH_THIRST_CHANGE_ATTACK			=	-3;

	//WOOD\\
	public static final 		int			WOOD_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			WOOD_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			WOOD_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			WOOD_HUNGER_CHANGE_CONSUME			=	-2;
	public static final 		int			WOOD_ENERGY_CHANGE_CONSUME			=	-1;
	public static final 		int			WOOD_THIRST_CHANGE_CONSUME			=	-2;

	public static final 		int			WOOD_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			WOOD_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			WOOD_THIRST_CHANGE_ATTACK			=	0;

	//WATER\\
	public static final 		int			WATER_HUNGER_CHANGE_INTERACT		=	0;
	public static final 		int			WATER_ENERGY_CHANGE_INTERACT		=	0;
	public static final 		int			WATER_THIRST_CHANGE_INTERACT		=	0;

	public static final 		int			WATER_HUNGER_CHANGE_CONSUME			=	0;
	public static final 		int			WATER_ENERGY_CHANGE_CONSUME			=	1;
	public static final 		int			WATER_THIRST_CHANGE_CONSUME			=	50;

	public static final 		int			WATER_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			WATER_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			WATER_THIRST_CHANGE_ATTACK			=	0;

	//MEAT\\
	public static final 		int			MEAT_HUNGER_CHANGE_INTERACT			=	0;
	public static final 		int			MEAT_ENERGY_CHANGE_INTERACT			=	0;
	public static final 		int			MEAT_THIRST_CHANGE_INTERACT			=	0;

	public static final 		int			MEAT_HUNGER_CHANGE_CONSUME			=	33;
	public static final 		int			MEAT_ENERGY_CHANGE_CONSUME			=	1;
	public static final 		int			MEAT_THIRST_CHANGE_CONSUME			=	-1;

	public static final 		int			MEAT_HUNGER_CHANGE_ATTACK			=	0;
	public static final 		int			MEAT_ENERGY_CHANGE_ATTACK			=	0;
	public static final 		int			MEAT_THIRST_CHANGE_ATTACK			=	0;

	// ----------- AI Constants ----------- \\

	public static final			int			GATHER_CROPS_STATE_TIME				=	5*CONTROLLER_UPDATE_INTERVAL_NORMAL;			// Number of updates ("frames") that the character will wait when gathering crops
	public static final			int			GATHER_BERRIES_STATE_TIME			=	90;			// Number of updates ("frames") that the character will wait when gathering wild berries
	public static final			int			GATHER_WATER_STATE_TIME				=	3*CONTROLLER_UPDATE_INTERVAL_NORMAL;			// Number of updates ("frames") that the character will wait when gathering water
	public static final			int			GATHER_MEAT_STATE_TIME				= 	60;			// Number of updates ("frames") that the character will wait when gathering meat
	public static final			int			GATHER_FISH_STATE_TIME				= 	10*CONTROLLER_UPDATE_INTERVAL_NORMAL;			// Number of updates ("frames") that the character will wait when gathering fish
	public static final			int			GATHER_WOOD_STATE_TIME				= 	5*CONTROLLER_UPDATE_INTERVAL_NORMAL;			// Number of updates ("frames") that the character will wait when gathering wood
	public static final			int			GATHER_GOLD_STATE_TIME				=	8*CONTROLLER_UPDATE_INTERVAL_NORMAL;			// Number of updates ("frames") that the character will wait when gathering gold
	public static final			int			GATHER_STONE_STATE_TIME				=	8*CONTROLLER_UPDATE_INTERVAL_NORMAL;			// Number of updates ("frames") that the character will wait when gathering stone
	public static final			int			EAT_STATE_TIME						=	15;			// Number of updates ("frames") that the character will wait when eating something
	public static final			int			DRINK_STATE_TIME					=	15;			// Number of updates ("frames") that the character will wait when drinking something

	// ----------- PATHFINDER Constants ----------- \\

	public static final		int				PATHFINDER_GRID_SIZE				=	8;
	public static final		double			PATHFINDER_PERPENDICULAR_COST		=	1.;
	public static final		double			PATHFINDER_DIAGONAL_COST			=	1.4;
	public static final 	Pathfinder		PATHFINDER_OBJECT					=	new Pathfinder(PATHFINDER_GRID_SIZE, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, PATHFINDER_PERPENDICULAR_COST, PATHFINDER_DIAGONAL_COST);

	// ----------- Display Needs ----------- \\
	public static final		int				BOX_WIDTH							=	600;
	public static final		int				BOX_HEIGHT							=	175;
	public static final		int				MARGIN_TOP							=	BOX_HEIGHT/5;
	public static final		int				MARGIN_LEFT							=	20;
	public static final 	int				HALF_TEXT_HEIGHT					=	9;
	public static final		double			NEEDS_CRITICAL_LEVEL				=	0.3;
	public static final		double			NEEDS_CONFORTABLE_LEVEL				=	0.7;

	// ----------- Character Traits ----------- \\
	public static final		int				MAX_TRAIT_VALUE						=	100;
	public static final		int				MIN_TRAIT_VALUE						=	0;
	public static final 	double			GLUTTONY_HUNGER_CHANGE_MODIFIER		=	0.01*0.5;
	public static final 	double			SLOTH_ENERGY_CHANGE_MODIFIER		=	0.01*0.5;
	public static final 	double			ENVY_SURROUNDING_RADIUS_MODIFYER	=	MAX_TRAIT_VALUE*10;
	public static final		double			WRATH_ENERGY_ATTACK_MODIFIER		=	0.05;
	public static final 	int				PRIDE_INTERACT_SOCIAL_MODIFIER		=	MAX_TRAIT_VALUE*10;

	// ----------- Display Item highlight options ----------- \\
	public static final		int				OPTION_BOX_WIDTH					=	500;
	public static final		int				OPTION_BOX_HEIGHT					=	SLOT_DISPLAY_SIZE*2/3;
	public static final		int				OPTION_MARGIN_TOP					=	OPTION_BOX_HEIGHT/6;
	public static final		int				OPTION_MARGIN_LEFT					=	60;
}
