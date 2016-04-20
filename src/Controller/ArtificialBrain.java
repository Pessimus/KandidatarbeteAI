package Controller;

import Controller.AIStates.*;
import Model.*;
import Model.Character;
import Utility.Constants;
import Model.ICharacterHandle;

//import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import static Utility.UniversalStaticMethods.distanceBetweenPoints;

/**
 * Created by Gustav on 2016-03-23.
 */
public class ArtificialBrain implements AbstractBrain, PropertyChangeListener {
	// ---------------DEBUG VARIABLE-------------- \\
	private static final boolean USE_MEMORY = true;
	// ---------------------------------------------\\




	private LinkedList<LinkedList<PathStep>> pathStack = new LinkedList<>();

	private LinkedList<IResource.ResourceType> gatherStack = new LinkedList<>();

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
	private IState gatherCropsState = new GatherCropsState(this);
	private IState gatherFishState = new GatherFishState(this);
	private IState gatherMeatState = new GatherMeatState(this);
	private IState gatherWaterState = new GatherWaterState(this);
	private IState gatherWoodState = new GatherWoodState(this);
	private IState gatherGoldState = new GatherGoldState(this);
	private IState gatherStoneState = new GatherStoneState(this);
	private IState gatherState = new GatherState(this);
	private IState hungryState = new HungryState(this);
	private IState idleState = new IdleState(this);
	private IState lowEnergyState = new LowEnergyState(this);
	private IState movingState = new MovingState(this);
	private IState restingState = new RestingState(this);
	private IState sleepingState = new SleepingState(this);
	private IState socializeState = new SocializeState(this);
	private IState thirstyState = new ThirstyState(this);
	private IState tradeState = new TradeState(this);
	private IState exploreState = new ExploreState(this);

	//private final HashMap<Path2D, ResourcePoint> resourceMap = new HashMap<>();
	List<ResourcePoint> resourceMemory = new LinkedList<>();

	private Interaction currentInteraction;
	private Character interactionCharacter;

	private ICollidable objectToFollow = null;
	private Class objectToFind = null;

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
		currentState = idleState;
		c.addPropertyChangeListener(this);

	}

	@Override
	public void update() {
		int[] needs = body.getNeeds();
		int[] traits = body.getTraits();
		int[] skills = body.getSkills();

		if(!body.isWaiting()) {
			//System.out.println("Current state: " + currentState);
			currentState.run();

			//System.out.println();
			//System.out.println("Current state: " + currentState);
			/*getStateQueue().stream()
					.forEach(o -> System.out.println("State:\t" + o));*/
			/*System.out.println("Inventory:");
			body.getInventory().stream()
					.forEach(i -> System.out.print(i.getType() + ":" + i.getAmount() + "  "));*/
			/*System.out.println("\nHunger:\t" + needs[0]);
			System.out.println("Thirst:\t" + needs[1]);
			System.out.println("Energy:\t" + needs[2]);
			System.out.println("Position:\t" + getBody().getX() + ":" + getBody().getY());*/
		}

		/*
		//removing things from memory that might not be there anymore. Anything deleted that is still there will be rediscovered.
		for (int i = 0; i < resourceMemory.size()-1; i++) {
			if (Math.abs(resourceMemory.get(i).getX() - body.getX()) < Constants.CHARACTER_SURROUNDING_RADIUS || Math.abs(resourceMemory.get(i).getY() - body.getY()) < Constants.CHARACTER_SURROUNDING_RADIUS) {
				resourceMemory.remove(i);
				//i--;
			}
		}
		*/
		
		body.getSurroundings().parallelStream()
				.filter(o -> o.getClass().equals(ResourcePoint.class))
				.map(o -> (ResourcePoint)o)
				.filter(o -> !resourceMemory.contains(o))
				.forEach(resourceMemory::add);

		/*
		for (ICollidable object : body.getSurroundings()) {
			if (object.getClass().equals(ResourcePoint.class)) {
				ResourcePoint resource = (ResourcePoint) object;
				if (!resourceMemory.contains(resource)) {
					resourceMemory.add(resource);
				}

			}
		}
		*/
	}

	@Override
	public void setBody(ICharacterHandle character) {
		body = character;
	}

	@Override
	public ICharacterHandle getBody() {
		return body;
	}

	public IState getCurrentState(){
		return currentState;
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

	public IResource.ResourceType getNextResourceToGather() {
		return gatherStack.peek();
	}

	public LinkedList<IResource.ResourceType> getGatherStack(){
		return gatherStack;
	}

	public void stackResourceToGather(IResource.ResourceType stackedResource){
		gatherStack.push(stackedResource);
	}

	public IStructure.StructureType peekStructureStack() {
		return buildStack.peek();
	}

	public void stackStructureToBuild(IStructure.StructureType type){
		buildStack.push(type);
	}

	public LinkedList<IStructure.StructureType> getStructureStack(){
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

	/**
	 * Finds the closest object of the type 'ResourcePoint' to the character
	 * @param type The type of resource to look for
	 * @return the 'ResourcePoint' which is closest, or null if none is found in memory
	 */
	public ResourcePoint getClosestResourcePoint(IResource.ResourceType type){
		if(USE_MEMORY) {

			/*surround.stream()
					.filter(o -> o.getClass().equals(ResourcePoint.class))
					.map(o -> (ResourcePoint)o)
					.filter(o -> o.getResource().getResourceType().equals(type))
					.reduce((rp1, rp2) -> distanceBetweenPoints(getBody().getX(), getBody().getY(), rp1.getX(), rp1.getY()) < distanceBetweenPoints(getBody().getX(), getBody().getY(), rp2.getX(), rp2.getY()) ? rp1 : rp2)
					.ifPresent(rp -> closest = rp);*/
			if(type.equals(IResource.ResourceType.FOOD)){
				return resourceMemory.stream()
						.filter(o -> o.getResource().getResourceType().equals(IResource.ResourceType.WATER) || o.getResource().getResourceType().equals(IResource.ResourceType.MEAT) || o.getResource().getResourceType().equals(IResource.ResourceType.CROPS))
						.reduce((rp1, rp2) -> distanceBetweenPoints(getBody().getX(), getBody().getY(), rp1.getX(), rp1.getY()) < distanceBetweenPoints(getBody().getX(), getBody().getY(), rp2.getX(), rp2.getY()) ? rp1 : rp2)
						.orElseGet(() -> null);
			}


			return resourceMemory.stream()
					.filter(o -> o.getResource().getResourceType().equals(type))
					.reduce((rp1, rp2) -> distanceBetweenPoints(getBody().getX(), getBody().getY(), rp1.getX(), rp1.getY()) < distanceBetweenPoints(getBody().getX(), getBody().getY(), rp2.getX(), rp2.getY()) ? rp1 : rp2)
					.orElseGet(() -> null);
		}

		return null;
	}

	public Interaction getCurrentInteraction() {
		return currentInteraction;
	}

	public Character getInteractionCharacter() {
		return interactionCharacter;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("attacked")){
			// TODO: Implement a proper response to being attacked
		} else if(evt.getPropertyName().equals("startInteraction")){
			Character other = (Character)evt.getNewValue();
			Interaction interaction = (Interaction)evt.getOldValue();
			System.out.println(interaction);

			if(currentInteraction == null && interactionCharacter == null){
				currentInteraction = interaction;
				interactionCharacter = other;
				stackState(currentState);
				setState(getSocializeState());
			} else{
				interaction.declineInteraction();
			}
		}
		else if (interactionCharacter != null){

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
			}
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
}
