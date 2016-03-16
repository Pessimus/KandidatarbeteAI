package Model;

import java.awt.*;

/**
 * Created by Tobias on 2016-03-09.
 */
public final class Constants {
	public static final		String		GAME_TITLE							=	"AI for NPCs";

	public static final 	double 		SCREEN_WIDTH 						= 	Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final 	double 		SCREEN_HEIGHT 						= 	Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public static final 	double 		SCREEN_EDGE_TRIGGER_MAX_X			=	SCREEN_WIDTH * 0.8;
	public static final 	double 		SCREEN_EDGE_TRIGGER_MAX_Y			=	SCREEN_HEIGHT * 0.8;
	public static final 	double 		SCREEN_EDGE_TRIGGER_MIN_X			=	SCREEN_WIDTH * 0.08;
	public static final 	double 		SCREEN_EDGE_TRIGGER_MIN_Y			=	SCREEN_HEIGHT * 0.08;

	public static final		float		SCREEN_SCROLL_SPEED_X				=	100f; // Pixels per second!
	public static final		float		SCREEN_SCROLL_SPEED_Y				=	100f; // Pixels per second!

	public static final 	float 		DEFAULT_WORLD_VIEW_X				= 	100f;
	public static final 	float 		DEFAULT_WORLD_VIEW_Y				= 	100f;

	public static final		float		WORLD_WIDTH							=	9600f;
	public static final		float		WORLD_HEIGHT						=	9600f;
	public static final 	int 		TARGET_FRAMERATE 					= 	60;
	public static final 	boolean		GAME_GRAB_MOUSE						= 	false;
	public static final 	boolean 	RUN_IN_FULLSCREEN 					= 	false;
	public static final 	long		CONTROLLER_UPDATE_INTERVAL			= 	17; // Interval in milliseconds

	public static final 	int 		WORLD_TILE_SIZE						= 	32;
	public static final 	int 		VERTICAL_TILES						=	(int) (SCREEN_HEIGHT /WORLD_TILE_SIZE);
	public static final 	int 		HORIZONTAL_TILES 					=	(int) (SCREEN_WIDTH /WORLD_TILE_SIZE);
}
