package Controller;

import Model.*;
import Model.Character;
import Model.Constants;
import Model.ICharacterHandle;

//import java.awt.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Gustav on 2016-03-23.
 */public class ArtificialBrain implements AbstractBrain {
    private LinkedList<PathStep> path;

	private ICharacterHandle body; // The character this Brain controls

	private boolean exploring = true;

	//private HashMap<Path2D, ResourcePoint> resourceMap = new HashMap<>();
	List<ResourcePoint> resourceMemory = new LinkedList<>();

    public ArtificialBrain() {
		this(new Character((float) (Constants.WORLD_WIDTH*Math.random()),(float) (Constants.WORLD_HEIGHT*Math.random()), 2));
    }

    public ArtificialBrain(ICharacterHandle c) {
        body = c;
    }

    @Override
    public void update() {
		int[] needs = body.getNeeds();
		int[] traits = body.getTraits();
		int[] skills = body.getSkills();

		for(ICollidable object : body.getSurroundings()){
			if(object.getClass().equals(ResourcePoint.class)){
				ResourcePoint resource = (ResourcePoint) object;
				if(!resourceMemory.contains(resource)){
					resourceMemory.add(resource);
				}

			}
		}
		if(exploring) {
			/*if (Math.random() > 0.0008) {
				double x = Math.random() * 9600;
				double y = Math.random() * 9600;

				path = Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), x, y);
			}*/
			if (path != null) {
				if (path.isEmpty()) {
					path = null;
				}
				else if (path.getFirst().stepTowards(body)) {
					path.removeFirst();
				}
			}
		} else{
			if(needs[0] <= needs[1] && needs[0] <= needs[2]){
				ResourcePoint closestResource = resourceMemory.get(0);
				Point closestPoint = new Point((int)closestResource.getX(), (int)closestResource.getY());
				double closestDistance = closestPoint.distance(body.getX(), body.getY());
				for(ResourcePoint resource : resourceMemory){
					if(resource.getResourceName().equals("Meat") || resource.getResourceName().equals("Fish")){
						if(closestPoint.distance(resource.getX(), resource.getY()) < closestDistance){
							closestResource = resource;
							closestPoint = new Point((int)closestResource.getX(), (int)closestResource.getY());
							closestDistance = closestPoint.distance(resource.getX(), resource.getY());
						}
					}
				}

				if(closestResource == null){
					exploring = true;
				}
				else{
					path = Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), closestPoint.getX(), closestPoint.getY());
				}
			}
		}
    }

	@Override
	public void setBody(ICharacterHandle character) {
		body = character;
	}

	@Override
	public ICharacterHandle getBody() {
		return body;
	}
}
