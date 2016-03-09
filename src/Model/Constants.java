package Model;

/**
 * Created by Tobias on 2016-03-09.
 */
public final class Constants {
	public enum RENDER_OBJECT_ENUM{
		CHARACTER("res/Villager16x16.png"), TREE("res/terrain.png");

		public String pathToResource;

		RENDER_OBJECT_ENUM(String path){
			pathToResource = path;
		}
	}
}
