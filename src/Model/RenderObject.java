package Model;

/**
 * Created by Martin on 03/03/2016.
 */
public class RenderObject {
	public float xPos;
	public float yPos;
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

	public RenderObject(RENDER_OBJECT_ENUM type){
		objectType = type;
	}

	public RenderObject(float x, float y, double r, RENDER_OBJECT_ENUM type){
		xPos = x;
		yPos = y;
		radius = r;
		objectType = type;
	}

	public RenderObject(RenderObject obj){
		xPos = obj.xPos;
		yPos = obj.yPos;
		radius = obj.radius;
		objectType = obj.objectType;
	}
}
