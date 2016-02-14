package TestView;

import org.lwjgl.util.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import org.lwjgl.util.Dimension;

/**
 * Created by Tobias on 2016-02-14.
 */
public class Toolkit {
	protected static Dimension getScreenDimension(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();

		return new Dimension(width, height);
	}
}
