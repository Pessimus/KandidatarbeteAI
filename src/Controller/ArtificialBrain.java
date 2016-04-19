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




	private LinkedList<LinkedList<PathStep>> path = new LinkedList<>();

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

	//private final HashMap<Path2D, ResourcePoint> resourceMap = new HashMap<>();
	List<ResourcePoint> resourceMemory = new LinkedList<>();

	private Interaction currentInteraction;
	private Character interactionCharacter;

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

	}

	@Override
	public void update() {
		int[] needs = body.getNeeds();
		int[] traits = body.getTraits();
		int[] skills = body.getSkills();

		if(!body.isWaiting()) {
			currentState.run();

			System.out.println();
			System.out.println("Current state: " + currentState);
			/*getStateQueue().stream()
					.forEach(o -> System.out.println("State:\t" + o));
			System.out.println("Inventory:");
			body.getInventory().stream()
					.forEach(i -> System.out.print(i.getType() + ":" + i.getAmount() + "  "));*/
			/*System.out.println("\nHunger:\t" + needs[0]);
			System.out.println("Thirst:\t" + needs[1]);
			System.out.println("Energy:\t" + needs[2]);
			System.out.println("Position:\t" + getBody().getX() + ":" + getBody().getY());*/
		}

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

	public IStructure.StructureType getNextStructureToBuild() {
		return buildStack.peek();
	}

	public void stackStructureToBuild(IStructure.StructureType type){
		buildStack.push(type);
	}

	public LinkedList<IStructure.StructureType> getStructureStack(){
		return buildStack;
	}

	public LinkedList<PathStep> getNextPath() {
		return path.peek();
	}

	public LinkedList<LinkedList<PathStep>> getPathStack() {
		return path;
	}

	public void findPathTo(double destX, double destY) {
		path.push(Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), destX, destY));
	}

	public void findPathTo(ICollidable dest) {
		path.push(Constants.PATHFINDER_OBJECT.getPath(body.getX(), body.getY(), dest));
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

			if(currentInteraction == null && interactionCharacter == null){
				currentInteraction = interaction;
				interactionCharacter = other;
				stackState(currentState);
				setState(getSocializeState());
				/*switch (interaction.getTypeOfInteraction()){
					case SOCIAL:
						stackState(currentState);
						setState(getConverseState());
						break;
					case TRADE:
						stackState(currentState);
						setState(getTradeState());
						break;
					case HOSTILE:
						break;
				}*/
			} else{
				interaction.declineInteraction();
			}
		}
		else if(evt.getPropertyName().equals("itemAddedToOfferBy" + interactionCharacter.getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("itemAddedToOfferFailedBy" + getBody().getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("itemAddedToRequestBy" + interactionCharacter.getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("itemAddedToRequestFailedBy" + getBody().getKey())) {
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("itemRemovedFromOfferBy" + interactionCharacter.getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("itemRemovedFromOfferFailedBy" + getBody().getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("itemRemovedFromRequestBy" + interactionCharacter.getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("itemRemovedFromRequestFailedBy" + getBody().getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("tradeAcceptedBy" + interactionCharacter.getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("tradeDeclinedBy" + interactionCharacter.getKey())){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("transferredTrade")){
			// TODO: What to do
		}
		else if(evt.getPropertyName().equals("endInteraction")){
			currentInteraction = null;
			interactionCharacter = null;
			body.setInteractionType(null);
		}

	}
}
