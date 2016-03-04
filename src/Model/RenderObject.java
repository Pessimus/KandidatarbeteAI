package Model;

/**
 * Created by Martin on 03/03/2016.
 */
public class RenderObject {
	public double xPos;
	public double yPos;
	public double radius;

	public RENDER_OBJECT_ENUM objectType;

	//TODO ENUM for type

	// A temporary enum
	public enum RENDER_OBJECT_ENUM{
		CHARACTER("res/Villager16x16.png");

		public String pathToResource;

		RENDER_OBJECT_ENUM(String path){
			pathToResource = path;
		}
	}

	public RenderObject(double x, double y, double r, RENDER_OBJECT_ENUM type){
		xPos = x;
		yPos = y;
		radius = r;
		objectType = type;
	}
}
