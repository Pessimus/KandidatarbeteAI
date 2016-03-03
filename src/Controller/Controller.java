package Controller;

//import Model.IModel;
//import Model.World;
//import View.IView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by Tobias on 2016-02-26.
 */
public class Controller implements PropertyChangeListener {
	//IModel gameModel;
	//IView gameView;

	public static void main(String[] args){
		//IModel model = new World();
		//IView view = new View();
		//new Controller();
	}

	/*
	public Controller(IView view, IModel model){
		setView(view);
		setModel(model);
	}
	*/

	/*
	public boolean setView(IView view){
		if(view != null){
			gameView = view;
			return true;
		}

		return false;
	}

	public boolean setModel(IModel model){
		if(model != null){
			gameModel = model;
			return true;
		}

		return false;
	}
	*/

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}
}
