package Controller;

import Model.Character;
import Model.Constants;
import Model.ICharacterHandler;

import java.util.LinkedList;

/**
 * Created by Gustav on 2016-03-23.
 */
public class ArtificialBrain implements AbstractBrain {
    private LinkedList<PathStep> path;

	private ICharacterHandler body; // The character this Brain controls

    public ArtificialBrain() {
        body = new Character((float) (9600*Math.random()),(float) (9600*Math.random()), 2);
    }

    public ArtificialBrain(ICharacterHandler c) {
        body = c;
    }

    @Override
    public void update() {
		int[] needs = body.getNeeds();
		int[] traits = body.getTraits();
		int[] skills = body.getSkills();



        if (Math.random() > 0.0008) {
            double x = Math.random() * 9600;
            double y = Math.random() * 9600;

            path = Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), x, y);
        }
        if (path != null) {
            if (path.isEmpty()) {path = null; return;}
            if (path.getFirst().stepTowards(body)) {path.removeFirst();}
        }
    }

	@Override
	public void setBody(ICharacterHandler character) {
		body = character;
	}

	@Override
	public ICharacterHandler getBody() {
		return body;
	}
}
