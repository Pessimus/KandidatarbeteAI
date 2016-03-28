package Model;

import Controller.Pathfinder;

import java.awt.*;

/**
 * Created by Tobias on 2016-03-09.
 */
public final class Constants {
	public static final			String		GAME_TITLE							=	"AI for NPCs";

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
	public static final 		long		CONTROLLER_UPDATE_INTERVAL			= 	60; // Updates per second

	public static final			int			PLAYER_CHARACTER_KEY				= 	1;
	public static final			float		CHARACTER_WALK_SPEED				=	6f; // Pixels per update
	public static final			float		CHARACTER_RUN_SPEED					=	12f; // Pixels per update

	public static final 		int 		WORLD_TILE_SIZE						= 	32;
	public static final 		int 		VERTICAL_TILES						=	(int) (SCREEN_HEIGHT /WORLD_TILE_SIZE);
	public static final 		int 		HORIZONTAL_TILES 					=	(int) (SCREEN_WIDTH /WORLD_TILE_SIZE);

	// ----------- PATHFINDER Constants ----------- \\

	public static final		int				PATHFINDER_GRID_SIZE				=	16;
	public static final		double			PATHFINDER_PERPENDICULAR_COST		=	1.;
	public static final		double			PATHFINDER_DIAGONAL_COST			=	1.4;
	public static final 	Pathfinder		PATHFINDER_OBJECT					=	new Pathfinder(PATHFINDER_GRID_SIZE, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, PATHFINDER_PERPENDICULAR_COST, PATHFINDER_DIAGONAL_COST);
}
