package Controller;

import Controller.AIStates.*;
import Model.*;
import Model.Character;
import Utility.Constants;
import Utility.RenderObject;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Created by Gustav on 2016-03-23.
 */
public class ArtificialBrain implements AbstractBrain, PropertyChangeListener {
	// ---------------DEBUG VARIABLE-------------- \\
	private static final boolean USE_MEMORY = true;
	// ---------------------------------------------\\


	private PrintWriter debugger;
	private LinkedList<Character> uninteractableCharacters = new LinkedList<>();

	private LinkedList<LinkedList<PathStep>> pathStack = new LinkedList<>();
	private LinkedList<Point> pointStack = new LinkedList<>();
	private LinkedList<ICollidable> resourceStack = new LinkedList<>();

	private LinkedList<ResourceTuple> gatherStack = new LinkedList<>();

	private LinkedList<IResource.ResourceType> resourceToFindStack = new LinkedList<>();

	private LinkedList<IStructure> structureToFindStack = new LinkedList<>();

	private LinkedList<Character> blackList = new LinkedList<>();

	private final Deque<IState> stateQueue = new LinkedList<>();

	private ICharacterHandle body; // The character this Brain controls

	private boolean exploring = true;

	// IState variables for every state possible
	private IState currentState;

	private IState buildHouseState = new BuildingHouseState(this);
	private IState buildState = new BuildState(this);
	private IState buildingState = new BuildingState(this);
	private IState converseState = new ConverseState(this);
	private IState cookState = new CookState(this);
	private IState drinkState = new DrinkState(this);
	private IState eatState = new EatState(this);
	private IState followState = new FollowState(this);
	private IState findCharacterState = new FindCharacterState(this);
	private IState findResourceState = new FindResourceState(this);
	private IState gatherCropsState = new GatherCropsState(this);
	private IState gatherFishState = new GatherFishState(this);
	private IState gatherMeatState = new GatherMeatState(this);
	private IState gatherWaterState = new GatherWaterState(this);
	private IState gatherWoodState = new GatherWoodState(this);
	private IState gatherGoldState = new GatherGoldState(this);
	private IState gatherStoneState = new GatherStoneState(this);
	private IState gatherState = new GatherState(this);
	private IState hungryState = new HungryState(this);
	private IState huntingState = new HuntingState(this);
	private IState idleState = new IdleState(this);
	private IState dumpToStockpileState = new DumpToStockpileState(this);
	private IState lowEnergyState = new LowEnergyState(this);
	private IState movingState = new MovingState(this);
	private IState restingState = new RestingState(this);
	private IState sleepingState = new SleepingState(this);
	private IState socializeState = new SocializeState(this);
	private IState thirstyState = new ThirstyState(this);
	private IState tradeState = new TradeState(this);
	private IState exploreState = new ExploreState(this);
	private IState workFarmState = new WorkFarmState(this);

	//private final HashMap<Path2D, ResourcePoint> resourceMap = new HashMap<>();

	private List<ResourcePoint> resourceMemory = new LinkedList<>();
	private List<IStructure> structureMemory = new LinkedList<>();

	private Interaction currentInteraction;
	private Character interactionCharacter;

	private StockpileInteraction currentStockpileInteraction;

	private ICollidable objectToFollow = null;
	private Class objectToFind = null;

	private int timeSinceAnimalSighting = 1000; //a counter to keep track of the time since an animal was sighted. If it was a short time ago the chance that the AI will choose to hunt increases

	public int getAnimalTime() {
		return timeSinceAnimalSighting;
	}

	public void setAnimalTime(int i) {
		timeSinceAnimalSighting = i;
	}

	//Construction variables - What are we building?
	private LinkedList<IStructure.StructureType> buildStack = new LinkedList<>();
	

	// TODO: Hardcoded universal vision
	public World map;

	public ArtificialBrain(World world, ICharacterHandle c) {
		this(c);
		map = world;
	}
	// TODO: Hardcoded universal vision

	public ArtificialBrain(ICharacterHandle c) {
		body = c;
		currentState = gatherState;
		stackState(this.getGatherState());
		stackResourceToGather(new ResourceTuple(IResource.ResourceType.FOOD, 3));
		stackResourceToGather(new ResourceTuple(IResource.ResourceType.WATER, 3));

		c.addPropertyChangeListener(this);
	}

	@Override
	public void update() {
		int[] needsArray = body.getNeeds();
		int[] traits = body.getTraits();
		int[] skills = body.getSkills();

		if (!body.isWaiting()) {

			currentState.run();

			int minVal = needsArray[0];
			int minindex = 0;

			if (needsArray[0] < 60 || needsArray[1] < 60 || needsArray[2] < 60) {
				if (!stateQueue.contains(this.getHungryState()) && !stateQueue.contains(this.getThirstyState()) && !stateQueue.contains(this.getLowEnergyState())) {
					clearStatePaths();
					getBody().setCurrentActivity(RenderObject.RENDER_OBJECT_ENUM.EMPTY);
					for (int i = 0; i < needsArray.length; i++) {
						if (needsArray[i] < minVal) {
							minVal = needsArray[i];
							minindex = i;
						}
					}

					switch (minindex) {
						case 0:
							this.stackState(currentState);
							this.setState((this.getHungryState()));
							break;
						case 1:
							this.stackState(currentState);
							this.setState((this.getThirstyState()));
							break;
						case 2:
							this.stackState(currentState);
							this.setState((this.getLowEnergyState()));
							break;

						default:
							this.stackState(currentState);
							this.setState((this.getHungryState()));
							break;
					}
				}
			}
		}

		// Clearing out objects in surroundings from memory. Every object forgotten that is still in surroundings will be relearned.
		LinkedList<ResourcePoint> removeList = new LinkedList<>();
		resourceMemory.stream()
				.filter(o -> o != null)
				.filter(o -> o.toBeRemoved() && Math.abs(o.getX() - getBody().getX()) < Constants.CHARACTER_SURROUNDING_RADIUS && Math.abs(o.getY() - getBody().getY()) < Constants.CHARACTER_SURROUNDING_RADIUS)
				.forEach(removeList::add);
		resourceMemory.stream()
				.filter(o -> o == null)
				.forEach(removeList::add);
		removeList.stream()
				.forEach(resourceMemory::remove);


		body.getSurroundings().stream()
				.filter(o -> o.getClass().equals(ResourcePoint.class))
				.map(o -> (ResourcePoint) o)
				.filter(o -> !resourceMemory.contains(o))
				.forEach(resourceMemory::add);

		body.getSurroundings().stream()
				.filter(o -> o instanceof IStructure)
				.map(o -> (IStructure) o)
				.filter(o -> !structureMemory.contains(o))
				.forEach(structureMemory::add);

		if (timeSinceAnimalSighting < 5000) {
			timeSinceAnimalSighting++;
		}
		for (ICollidable c : body.getSurroundings()) {
			if (c.getClass().equals(Animal.class)) {
				timeSinceAnimalSighting = 0;
				break;
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

	public IState getCurrentState() {
		return currentState;
	}

	@Override
	public void setState(IState state) {
		currentState = state;
		//debugger.println(state.getClass().toString());
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

	public IState getBuildingState() {
		return buildingState;
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

	public IState getFollowState() {
		return followState;
	}

	public IState getGatherCropsState() {
		return gatherCropsState;
	}

	public IState getGatherFishState() {
		return gatherFishState;
	}

	public IState getGatherMeatState() {
		return gatherMeatState;
	}

	public IState getGatherState() {
		return gatherState;
	}

	public IState getHungryState() {
		return hungryState;
	}

	public IState getHuntingState() {
		return huntingState;
	}

	public IState getIdleState() {
		return idleState;
	}

	public IState getLowEnergyState() {
		return lowEnergyState;
	}

	public IState getMovingState() {
		return movingState;
	}

	public IState getSleepingState() {
		return sleepingState;
	}

	public IState getRestingState() {
		return restingState;
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

	public IState getGatherWaterState() {
		return gatherWaterState;
	}

	public IState getGatherWoodState() {
		return gatherWoodState;
	}

	public IState getGatherStoneState() {
		return gatherStoneState;
	}

	public IState getExploreState() {
		return exploreState;
	}

	public IState getGatherGoldState() {
		return gatherGoldState;
	}

	public IState getWorkFarmState() {
		return workFarmState;
	}

	public ResourceTuple getNextResourceToGather() {
		return gatherStack.peek();
	}

	public LinkedList<ResourceTuple> getGatherStack() {
		return gatherStack;
	}

	public void stackResourceToGather(ResourceTuple stackedResource) {
		gatherStack.push(stackedResource);
	}

	public IStructure.StructureType peekStructureStack() {
		return buildStack.peek();
	}

	public void stackStructureToBuild(IStructure.StructureType type) {
		buildStack.push(type);
	}

	public LinkedList<IStructure.StructureType> getStructureStack() {
		return buildStack;
	}

	public LinkedList<PathStep> getNextPath() {
		return pathStack.peek();
	}

	public LinkedList<LinkedList<PathStep>> getPathStack() {
		return pathStack;
	}

	public void findPathTo(double destX, double destY) {
		pathStack.push(Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), destX, destY));
	}

	public void findPathTo(ICollidable dest) {
		pathStack.push(Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), dest));
	}

	public Queue<IState> getStateQueue() {
		return stateQueue;
	}

	public void queueState(IState state) {
		stateQueue.offer(state);
	}

	public void stackState(IState state) {
		stateQueue.push(state);
	}

	public List<ResourcePoint> getResourceMemory() {
		return resourceMemory;
	}

	public List<IStructure> getStructureMemory() {
		return structureMemory;
	}

	public LinkedList<Character> getUninteractableCharacters() {
		return uninteractableCharacters;
	}

	/**
	 * Finds the closest object of the type 'ResourcePoint' to the character
	 *
	 * @param type The type of resource to look for
	 * @return the 'ResourcePoint' which is closest, or null if none is found in memory
	 */
	public ResourcePoint getClosestResourcePoint(IResource.ResourceType type) {
		/*
		if(USE_MEMORY) {

			/*surround.stream()
					.filter(o -> o.getClass().equals(ResourcePoint.class))
					.map(o -> (ResourcePoint)o)
					.filter(o -> o.getResource().getResourceType().equals(type))
					.reduce((rp1, rp2) -> distanceBetweenPoints(getBody().getX(), getBody().getY(), rp1.getX(), rp1.getY()) < distanceBetweenPoints(getBody().getX(), getBody().getY(), rp2.getX(), rp2.getY()) ? rp1 : rp2)
					.ifPresent(rp -> closest = rp);*/
			/*
			if(type.equals(IResource.ResourceType.FOOD)){
				return resourceMemory.stream()
						.filter(o -> o.getResource().getResourceType().equals(IResource.ResourceType.WATER) || o.getResource().getResourceType().equals(IResource.ResourceType.MEAT) || o.getResource().getResourceType().equals(IResource.ResourceType.CROPS))
						.reduce((rp1, rp2) -> distanceBetweenPoints(getBody().getX(), getBody().getY(), rp1.getX(), rp1.getY()) < distanceBetweenPoints(getBody().getX(), getBody().getY(), rp2.getX(), rp2.getY()) ? rp1 : rp2)
						.orElseGet(() -> null);
			}

*/
		if (type.equals(IResource.ResourceType.FOOD)) {
			return resourceMemory.stream()
					.filter(o -> o.getResource().getResourceType().equals(IResource.ResourceType.WATER) || o.getResource().getResourceType().equals(IResource.ResourceType.MEAT) || o.getResource().getResourceType().equals(IResource.ResourceType.CROPS))
					.reduce((rp1, rp2) -> (Math.abs(getBody().getX() - rp1.getX()) < Math.abs(getBody().getX() - rp2.getX())
							&& Math.abs(getBody().getY() - rp1.getY()) < Math.abs(getBody().getX() - rp2.getX())) ? rp1 : rp2)
					.orElseGet(() -> null);
		}


		return resourceMemory.stream()
				.filter(o -> o.getResource().getResourceType().equals(type))
				.reduce((rp1, rp2) -> (Math.abs(getBody().getX() - rp1.getX()) < Math.abs(getBody().getX() - rp2.getX())
						&& Math.abs(getBody().getY() - rp1.getY()) < Math.abs(getBody().getX() - rp2.getX())) ? rp1 : rp2)
				.orElseGet(() -> null);
	}

	public Interaction getCurrentInteraction() {
		return currentInteraction;
	}

	public Character getInteractionCharacter() {
		return interactionCharacter;
	}

	public LinkedList<Character> getBlackList() {
		return blackList;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("attacked")) {
			// TODO: Implement a proper response to being attacked
		} else if (evt.getPropertyName().equals("startInteraction")) {
			Character other = (Character) evt.getNewValue();
			Interaction interaction = (Interaction) evt.getOldValue();

			if ((currentInteraction == null || interactionCharacter == null) ||
					(currentInteraction.getCharacter1Key() == body.hashCode() && currentInteraction.getCharacter2Key() == interactionCharacter.hashCode()) ||
					(currentInteraction.getCharacter2Key() == body.hashCode() && currentInteraction.getCharacter1Key() == interactionCharacter.hashCode())) {
				currentInteraction = interaction;
				interactionCharacter = other;

				if (getCurrentState() != getSocializeState()) {
					stackState(currentState);
				}
				setState(getSocializeState());

			} else {
				interaction.declineInteraction();
			}
		} else if (interactionCharacter != null) {

			if (evt.getPropertyName().equals("itemAddedToOfferBy" + interactionCharacter.hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("itemAddedToOfferFailedBy" + getBody().hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("itemAddedToRequestBy" + interactionCharacter.hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("itemAddedToRequestFailedBy" + getBody().hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("itemRemovedFromOfferBy" + interactionCharacter.hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("itemRemovedFromOfferFailedBy" + getBody().hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("itemRemovedFromRequestBy" + interactionCharacter.hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("itemRemovedFromRequestFailedBy" + getBody().hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("tradeAcceptedBy" + interactionCharacter.hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("tradeDeclinedBy" + interactionCharacter.hashCode())) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("transferredTrade")) {
				// TODO: What to do
			} else if (evt.getPropertyName().equals("endInteraction")) {
				currentInteraction = null;
				interactionCharacter = null;
				body.setInteractionType(null);
			} else if (evt.getPropertyName().equals("interactionNotActive" + interactionCharacter.hashCode())) {
				if (uninteractableCharacters.size() > 5) {
					uninteractableCharacters.clear();
				}
				uninteractableCharacters.add(interactionCharacter);
			}
		} else if (evt.getPropertyName().equals("endStockpileInteraction")){
			currentStockpileInteraction.removePropertyChangeListener(this);
			setCurrentStockpileInteraction(null);
		} else if (evt.getPropertyName().equals("startStockpileInteraction")){
			setCurrentStockpileInteraction((StockpileInteraction)evt.getOldValue());
			currentStockpileInteraction.addPropertyChangeListener(this);
		}
	}

	public ICollidable getObjectToFollow() {
		return objectToFollow;
	}

	public void setObjectToFollow(ICollidable objectToFollow) {
		this.objectToFollow = objectToFollow;
	}

	public Class getObjectToFind() {
		return objectToFind;
	}

	public void setObjectToFind(Class ObjectToFind) {
		this.objectToFind = ObjectToFind;
	}

	public LinkedList<Point> getPointStack() {
		return pointStack;
	}

	public Point getNextPoint() {
		return pointStack.peek();
	}

	public void stackPoint(Point p) {
		pointStack.offer(p);
	}

	public LinkedList<ICollidable> getResourceStack() {
		return resourceStack;
	}

	public ICollidable getNextResource() {
		return resourceStack.peek();
	}

	public void stackResource(ICollidable r) {
		resourceStack.push(r);
	}

	public void stackResourceToFind(IResource.ResourceType res) {
		resourceToFindStack.push(res);
	}

	public String getThingMovingTowards() {
		if (getNextResource().getClass().equals(ResourcePoint.class)) {
			return ((ResourcePoint) getNextResource()).getResource().getResourceType().toString();
		}
		return "HOUSE";
	}

	public LinkedList<IResource.ResourceType> getResourceToFindStack(){
		return resourceToFindStack;
	}

	public IState getFindResourceState() {
		return findResourceState;
	}

	public IState getFindCharacterState() {
		return findCharacterState;
	}

	private void clearStatePaths() {
		((ExploreState) this.getExploreState()).clearPath();
		((FindCharacterState) this.getFindCharacterState()).clearPath();
		((FindResourceState) this.getFindResourceState()).clearPath();
		((FollowState) this.getFollowState()).clearPath();
		((HuntingState) this.getHuntingState()).clearPath();
		((MovingState) this.getMovingState()).clearPath();
	}

	public IState getDumpToStockpileState() {
		return dumpToStockpileState;
	}

	public StockpileInteraction getCurrentStockpileInteraction() {
		return currentStockpileInteraction;
	}

	public void setCurrentStockpileInteraction(StockpileInteraction currentStockpileInteraction) {
		this.currentStockpileInteraction = currentStockpileInteraction;
	}
	/*public void finalWords() {
		int[] needs = body.getNeeds();
		int age = body.getAge();
		debugger.print("Cause of death ");
		if (needs[0] <= 0) {debugger.println("hunger.");}
		else if (needs[0] <= 0) {debugger.println("thirst.");}
		else if (needs[0] <= 0) {debugger.println("energy.");}
		else {debugger.println("age.");}
		debugger.print("Age upon death " + Integer.toString(age));
		debugger.flush();
		debugger.close();
	}*/
}
