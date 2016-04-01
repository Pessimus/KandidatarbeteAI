package Controller;

import Controller.AIStates.*;
import Model.*;
import Model.Character;
import Model.Constants;
import Model.ICharacterHandle;

//import java.awt.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;
import java.util.List;

/**
 * Created by Gustav on 2016-03-23.
 */public class ArtificialBrain implements AbstractBrain {
	private LinkedList<PathStep> path;

	private IResource.ResourceType nextResourceToGather = null;

	private final Queue<IState> stateQueue = new LinkedList<>();

	private ICharacterHandle body; // The character this Brain controls

	private boolean exploring = true;

	// IState variables for every state possible
	private IState currentState;

	private IState buildHouseState = new BuildHouseState(this);
	private IState buildState = new BuildState(this);
	private IState converseState = new ConverseState(this);
	private IState cookState = new CookState(this);
	private IState drinkState = new DrinkState(this);
	private IState eatState = new EatState(this);
	private IState gatherCropsState = new GatherCropsState(this);
	private IState gatherMaterialState = new GatherMaterialState(this);
	private IState gatherState = new GatherState(this);
	private IState hungryState = new HungryState(this);
	private IState idleState = new IdleState(this);
	private IState movingState = new MovingState(this);
	private IState sleepState = new SleepState(this);
	private IState sleepyState = new SleepyState(this);
	private IState socializeState = new SocializeState(this);
	private IState thirstyState = new ThirstyState(this);
	private IState tradeState = new TradeState(this);

	//private final HashMap<Path2D, ResourcePoint> resourceMap = new HashMap<>();
	List<ResourcePoint> resourceMemory = new LinkedList<>();

	// TODO: Hardcoded universal vision
	public World map;

	public ArtificialBrain(World world, ICharacterHandle c) {
		this(c);
		map = world;
	}
	// TODO: Hardcoded universal vision

	public ArtificialBrain(ICharacterHandle c) {
		body = c;
		currentState = idleState;
	}

	@Override
	public void update() {
		int[] needs = body.getNeeds();
		int[] traits = body.getTraits();
		int[] skills = body.getSkills();

		currentState.run();

		for (ICollidable object : body.getSurroundings()) {
			if (object.getClass().equals(ResourcePoint.class)) {
				ResourcePoint resource = (ResourcePoint) object;
				if (!resourceMemory.contains(resource)) {
					resourceMemory.add(resource);
				}

			}
		}
		if (exploring) {
			if (path != null) {
				if (path.isEmpty()) {
					path = null;
				} else if (path.getFirst().stepTowards(body)) {
					path.removeFirst();
				}
			}
		} else {
			if (needs[0] <= needs[1] && needs[0] <= needs[2]) {
				ResourcePoint closestResource = resourceMemory.get(0);
				Point closestPoint = new Point((int) closestResource.getX(), (int) closestResource.getY());
				double closestDistance = closestPoint.distance(body.getX(), body.getY());
				for (ResourcePoint resource : resourceMemory) {
					if (resource.getResourceName().equals("Meat") || resource.getResourceName().equals("Fish")) {
						if (closestPoint.distance(resource.getX(), resource.getY()) < closestDistance) {
							closestResource = resource;
							closestPoint = new Point((int) closestResource.getX(), (int) closestResource.getY());
							closestDistance = closestPoint.distance(resource.getX(), resource.getY());
						}
					}
				}

				if (closestResource == null) {
					exploring = true;
				} else {
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

	@Override
	public void setState(IState state) {
		currentState = state;
	}

	@Override
	public IState getState() {
		return currentState;
	}

	public IState getBuildHouseState() {
		return buildHouseState;
	}

	public IState getBuildState() {
		return buildState;
	}

	public IState getConverseState() {
		return converseState;
	}

	public IState getCookState() {
		return cookState;
	}

	public IState getDrinkState() {
		return drinkState;
	}

	public IState getEatState() {
		return eatState;
	}

	public IState getGatherCropsState() {
		return gatherCropsState;
	}

	public IState getGatherMaterialState() {
		return gatherMaterialState;
	}

	public IState getGatherState() {
		return gatherState;
	}

	public IState getHungryState() {
		return hungryState;
	}

	public IState getIdleState() {
		return idleState;
	}

	public IState getMovingState() {
		return movingState;
	}

	public IState getSleepState() {
		return sleepState;
	}

	public IState getSleepyState() {
		return sleepyState;
	}

	public IState getSocializeState() {
		return socializeState;
	}

	public IState getThirstyState() {
		return thirstyState;
	}

	public IState getTradeState() {
		return tradeState;
	}

	public IResource.ResourceType getNextResourceToGather() {
		return nextResourceToGather;
	}

	public void setNextResourceToGather(IResource.ResourceType nextResourceToGather) {
		this.nextResourceToGather = nextResourceToGather;
	}

	public LinkedList<PathStep> getPath() {
		return path;
	}

	public void setPath(LinkedList<PathStep> path) {
		this.path = path;
	}

	public Queue<IState> getStateQueue() {
		return stateQueue;
	}

	public void queueState(IState state) {
		stateQueue.offer(state);
	}

	public List<ResourcePoint> getResourceMemory() {
		return resourceMemory;
	}

	//Gives the AI a new path, probably redundant method. Only for testing purposes.
	public void getNewPath(double destX, double destY) {
		path = Constants.PATHFINDER_OBJECT.getPath(body.getX(),body.getY(),destX,destY);
	}

	/*
	enum AIStates{
		IDLE,
		HUNGRY, COOK, EAT, EATMEAT, EATFISH, EATCROPS,
		GATHER, GATHERMATERIAL, GATHERFOOD, GATHERWATER,
		SLEEPY, GOHOME, SLEEP,
		THIRSTY, DRINK,
		SOCIALIZE, TALK,
		BUILD, BUILDHOUSE, BUILDSTOCKPILE, BUILDFARM, BUILDMILL,
	}
	*/
}
