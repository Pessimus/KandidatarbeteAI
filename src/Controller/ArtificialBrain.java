package Controller;

import Model.Character;
import Model.Constants;

import java.util.LinkedList;

/**
 * Created by Gustav on 2016-03-23.
 */
public class ArtificialBrain extends AbstractBrain {
    private Character body;
    private LinkedList<PathStep> path;

    public ArtificialBrain() {
        body = new Character((float) (9600*Math.random()),(float) (9600*Math.random()), 2);
    }

    public ArtificialBrain(Character c) {
        body = c;
    }

    public void step() {
        if (Math.random() > 0.0008) {
            double x = Math.random() * 9600;
            double y = Math.random() * 9600;

            path = Controller.pathCalculator.getPath(body.getX(), body.getY(), x, y);
        }
        if (path != null) {
            if (path.isEmpty()) {path = null; return;}
            if (path.getFirst().stepTowards(body)) {path.removeFirst();}
        }
    }
}
