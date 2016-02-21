package Controller;

import Model.World;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tobias on 2016-02-20.
 */
public class Controller implements ActionListener{

	public static void main(String[] args){
		new World();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		;
	}
}
