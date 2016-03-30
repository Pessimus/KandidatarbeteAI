package Model;

/**
 * Created by Martin on 03/03/2016.
 */
public final class RenderObject{

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private final float xPos;
	private final float yPos;
	private final double radius;

	private RENDER_OBJECT_ENUM objectType;

	public enum RENDER_OBJECT_ENUM{
		CHARACTER("res/Villager16x16.png"), TREE("res/terrain.png"),

		// TODO: Hardcoded test enum
		MEAT("res/meat_item.png");
		// TODO: Hardcoded test enum

		public String pathToResource;

		RENDER_OBJECT_ENUM(String path){
			pathToResource = path;
		}
	}

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	public RenderObject(float x, float y, double r, RENDER_OBJECT_ENUM type){
		xPos = x;
		yPos = y;
		radius = r;
		objectType = type;

	}

//---------------------------------------Getters & Setters------------------------------------------------------------\\

	public final float getX() {
		return xPos;
	}

	public final float getY() {
		return yPos;
	}

	public final double getRadius() {
		return radius;
	}

	public final RENDER_OBJECT_ENUM getRenderType() {
		return objectType;
	}

/*//TODO check if safe to REMOVE!!
	@Override
	public boolean equals(Object o){
		if ( this == o ) return true;
		if ( !(o instanceof RenderObject) ) return false;

		RenderObject temp = (RenderObject) o;
		return temp.getX() == getX() && temp.getY() == getY() && temp.getRadius() == getRadius() && temp.getRenderType() == temp.getRenderType();
	}

	public boolean compare(ICollidable o){
		return o.getX() == getX() && o.getY() == getY() && o.getCollisionRadius() == getRadius() && o.getRenderType() == getRenderType();
	}*/
}
