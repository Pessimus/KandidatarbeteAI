package Model;

/**
 * Created by Martin on 24/02/2016.
 */
public class River {


	//At^3+Bt^2+Ct+D
	private int xA;
	private int xB;
	private int xC;


	private int yA;
	private int yB;
	private int yC;

	private int tMax;

	public River (int worldSize){
		this.xA = (int)(Math.random()*10);
		this.xB = (int)(Math.random()*10);
		this.xC = (int)(Math.random()*10);

		this.yA = (int)(Math.random()*10);
		this.yB = (int)(Math.random()*10);
		this.yC = (int)(Math.random()*10);


	}


}
