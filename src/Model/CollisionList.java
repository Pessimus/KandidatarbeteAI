package Model;

import Toolkit.RenderObject;

/**
 * Created by Martin on 24/02/2016.
 */
public class CollisionList {

//-----------------------------------------------VARIABLES------------------------------------------------------------\\

	private Node startNodeX;
	private Node startNodeY;
	private int size;
	private Node currentNodeX;
	private double maxRadius;

//----------------------------------------------CONSTRUCTOR-----------------------------------------------------------\\

	/**
	 * A dual list for ICollidables, one list for the x-axis and one for the y-axis.
	 * Used for effectively checking different kinds of collision between objects.
	 */
	public CollisionList(){
		this.startNodeX = new Node(new CollidableDummy(), null, null);
		this.startNodeY = new Node(new CollidableDummy(), null, null);
		this.currentNodeX = this.startNodeX;
		this.size = 0;
		this.maxRadius = 0;
	}

//--------------------------------------------Sorting methods---------------------------------------------------------\\

	/**
	 * Sorting the x-list based on the x-coordinates in ascending order.
	 */
	public void sortX(){
		if(startNodeX.next == null){
			return;
		}
		boolean hasChanged = true;
		Node sortNode;
		while(hasChanged) {
			sortNode = startNodeX.next.next;
			hasChanged = false;
			while (sortNode != null) {
				if(sortNode.value.getX() < sortNode.previous.value.getX()){
					swap(sortNode.previous, sortNode);
					hasChanged = true;
				}
				sortNode = sortNode.next;
			}
		}
	}

	/**
	 * Sorting the y-list based on the y-coordinates in ascending order.
	 */
	public void sortY(){
		if(startNodeY.next == null){
			return;
		}
		boolean hasChanged = true;
		Node sortNode;
		while(hasChanged) {
			sortNode = startNodeY.next.next;
			hasChanged = false;
			while (sortNode != null) {
				if(sortNode.value.getY() < sortNode.previous.value.getY()){
					swap(sortNode.previous, sortNode);
					hasChanged = true;
				}
				sortNode = sortNode.next;
			}
		}
	}

	/**
	 * Swaps positions in the list of two nodes next to each other.
	 * @param previous the first node, has the other as next.
	 * @param next the second node, has the other as previous.
	 */
	private void swap(Node previous, Node next) {
		next.previous = previous.previous;
		previous.next = next.next;

		next.next=previous;
		previous.previous=next;

		next.previous.next = next;
		if (previous.next != null) {
			previous.next.previous = previous;
		}
	}

//------------------------------------------Add & toBeRemoved methods------------------------------------------------------\\

	/**
	 * Adds the ICollidable to the lists in the right place.
	 * @param addValue the ICollidable to be added.
	 */
	public void add(ICollidable addValue){
		//Add to the X list
		Node loopNodeX = startNodeX;
		while (loopNodeX.next != null){
			loopNodeX = loopNodeX.next;
		}
		loopNodeX.next = new Node(addValue, loopNodeX,null);

		//Add to the y list
		Node loopNodeY = startNodeY;
		while (loopNodeY.next != null){
			loopNodeY = loopNodeY.next;
		}
		loopNodeY.next = new Node(addValue, loopNodeY,null);

		//Update maxRadius
		if(addValue.getCollisionRadius()>maxRadius){
			maxRadius = addValue.getCollisionRadius();
		}

		//Update size
		this.size++;
	}

	/**
	 * Removes the ICollidable from the lists if it exists.
	 * @param collidable the ICollidable to be removed.
	 */
	public void remove(ICollidable collidable) {
		//Remove from the x list
		Node tmpX = startNodeX.next;
		while (tmpX != null){
			if (tmpX.value == collidable){//Yes we want ==

				if(tmpX.next != null && tmpX.previous != null) {
					tmpX.previous.next = tmpX.next;
					tmpX.next.previous = tmpX.previous;
				}
				this.size--;
				break;
			}
			tmpX = tmpX.next;
		}

		//Remove from the y list
		Node tmpY = startNodeY.next;
		while (tmpY != null){
			if (tmpY.value == collidable){//Yes we want ==
				if (tmpY.previous != null && tmpY.next != null) {
					tmpY.previous.next = tmpY.next;
					tmpY.next.previous = tmpY.previous;
				}
				break;
			}
			tmpY = tmpY.next;
		}
		//Update maxRadius
		if(collidable.getCollisionRadius() == maxRadius){
			Node tmpNode = startNodeX.next;
			double tmpMax = 0;
			while(tmpNode != null){
				if(tmpNode.value.getCollisionRadius() > tmpMax){
					tmpMax = tmpNode.value.getCollisionRadius();
				}
			}
		}
	}

	/**
	 * Returns the number of items in the lists.
	 * Both lists always have the same number of items.
	 * @return the number of items.
	 */
	public int getSize(){
		return this.size;
	}

//----------------------------------------Sweep and prune methods-----------------------------------------------------\\

	/**
	 * Checks collision for all items in the lists. Collision occurs if two items collide in both lists.
	 */
	public void handleCollision(){

		this.sortX();
		this.sortY();

		//try {
		Node loopXNode = startNodeX;
		while (loopXNode.next != null) {
			loopXNode = loopXNode.next;
			handleSurroundingsCollisionLeftX(loopXNode, loopXNode.previous);
			handleSurroundingsCollisionRightX(loopXNode, loopXNode.next);

			handleInterractionCollisionLeftX(loopXNode, loopXNode.previous);
			handleInterractionCollisionRightX(loopXNode, loopXNode.next);
		}

		Node loopYNode = startNodeY;
		while (loopYNode.next != null) {
			loopYNode = loopYNode.next;
			handleSurroundingsCollisionLeftY(loopYNode, loopYNode.previous);
			handleSurroundingsCollisionRightY(loopYNode, loopYNode.next);

			handleInterractionCollisionLeftY(loopYNode, loopYNode.previous);
			handleInterractionCollisionRightY(loopYNode, loopYNode.next);
		}

		Node loop = startNodeX;
		while (loop.next != null) {
			loop = loop.next;
			loop.value.checkSurroundings();
			loop.value.checkInteractables();
		}

		//}catch (Exception e){

		//}

	}

	//---------------Interaction checking------------------\\

	/**
	 * Checks if two nodes collide in the x-axis.
	 * It uses the interactionRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node left of the left node.
	 * @param node the node to check collision for using the interaction radius.
	 * @param left the node to check against the other using the collision radius.
	 */
	private void handleInterractionCollisionLeftX(Node node, Node left){
		if(node.value.getX() -(left.value.getX() + maxRadius) <= node.value.getInteractionRadius()) {
			if (node.value.getX() - (left.value.getX() + left.value.getCollisionRadius()) <= node.value.getInteractionRadius()) {
				node.value.addToInteractableX(left.value);
			}
			handleInterractionCollisionLeftX(node, left.previous);
		}
	}

	/**
	 * Checks if two nodes collide in the x-axis.
	 * It uses the interactionRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node right of the right node.
	 * @param node the node to check collision for using the interaction radius.
	 * @param right the node to check against the other using the collision radius.
	 */
	private void handleInterractionCollisionRightX(Node node, Node right){
		if(right != null && (right.value.getX() - maxRadius) - node.value.getX() <= node.value.getInteractionRadius()) {
			if ((right.value.getX() - right.value.getCollisionRadius()) - node.value.getX() <= node.value.getInteractionRadius()) {
				node.value.addToInteractableX(right.value);
				handleInterractionCollisionRightX(node, right.next);
			}
			handleInterractionCollisionRightX(node, right.next);
		}
	}

	/**
	 * Checks if two nodes collide in the y-axis.
	 * It uses the interactionRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node left of the left node.
	 * @param node the node to check collision for using the interaction radius.
	 * @param left the node to check against the other using the collision radius.
	 */
	private void handleInterractionCollisionLeftY(Node node, Node left){
		if(node.value.getY() - (left.value.getY() + maxRadius) <= node.value.getInteractionRadius()) {
			if (node.value.getY() - (left.value.getY() + left.value.getCollisionRadius()) <= node.value.getInteractionRadius()) {
				node.value.addToInteractableY(left.value);
			}
			handleInterractionCollisionLeftY(node, left.previous);
		}
	}

	/**
	 * Checks if two nodes collide in the y-axis.
	 * It uses the interactionRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node right of the right node.
	 * @param node the node to check collision for using the interaction radius.
	 * @param right the node to check against the other using the collision radius.
	 */
	private void handleInterractionCollisionRightY(Node node, Node right){
		if(right != null && (right.value.getY() - maxRadius) - node.value.getY() <= node.value.getInteractionRadius()) {
			if ((right.value.getY() - right.value.getCollisionRadius()) - node.value.getY() <= node.value.getInteractionRadius()) {
				node.value.addToInteractableY(right.value);
			}
			handleInterractionCollisionRightY(node, right.next);
		}
	}

	//---------------Surrounding checking------------------\\

	/**
	 * Checks if two nodes collide in the x-axis.
	 * It uses the surroundingRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node left of the left node.
	 * @param node the node to check collision for using the interaction radius.
	 * @param left the node to check against the other using the collision radius.
	 */
	private void handleSurroundingsCollisionLeftX(Node node, Node left){
		if(node.value.getX() - (left.value.getX() + maxRadius) <= node.value.getSurroundingRadius()) {
			if (node.value.getX() - (left.value.getX() + left.value.getCollisionRadius()) <= node.value.getSurroundingRadius()) {
				node.value.addToSurroundingX(left.value);
			}
			handleSurroundingsCollisionLeftX(node, left.previous);
		}
	}

	/**
	 * Checks if two nodes collide in the x-axis.
	 * It uses the surroundingRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node right of the right node.
	 * @param node the node to check collision for using the interaction radius.
	 * @param right the node to check against the other using the collision radius.
	 */
	private void handleSurroundingsCollisionRightX(Node node, Node right){
		if(right != null && (right.value.getX() - maxRadius) - node.value.getX() <= node.value.getSurroundingRadius()) {
			if ((right.value.getX() - right.value.getCollisionRadius()) - node.value.getX() <= node.value.getSurroundingRadius()) {
				node.value.addToSurroundingX(right.value);
			}
			handleSurroundingsCollisionRightX(node, right.next);
		}
	}

	/**
	 * Checks if two nodes collide in the y-axis.
	 * It uses the surroundingRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node left of the left node.
	 * @param node the node to check collision for using the interaction radius.
	 * @param left the node to check against the other using the collision radius.
	 */
	private void handleSurroundingsCollisionLeftY(Node node, Node left){
		if(node.value.getY() - (left.value.getY() + maxRadius) <= node.value.getSurroundingRadius()) {
			if (node.value.getY() - (left.value.getY() + left.value.getCollisionRadius()) <= node.value.getSurroundingRadius()) {
				node.value.addToSurroundingY(left.value);
			}
			handleSurroundingsCollisionLeftY(node, left.previous);
		}
	}

	/**
	 * Checks if two nodes collide in the y-axis.
	 * It uses the surroundingRadius of the first and the collisionRadius of the second.
	 * If collision occurs (when using maxRadius) it also checks the first against the node right of the right left.
	 * @param node the node to check collision for using the interaction radius.
	 * @param right the node to check against the other using the collision radius.
	 */
	private void handleSurroundingsCollisionRightY(Node node, Node right){
		if(right != null && (right.value.getY() - maxRadius) - node.value.getY() <= node.value.getSurroundingRadius()) {
			if ((right.value.getY() - right.value.getCollisionRadius()) - node.value.getY() <= node.value.getSurroundingRadius()) {
				node.value.addToSurroundingY(right.value);
			}
			handleSurroundingsCollisionRightY(node, right.next);
		}
	}

//---------------------------------------------Pathfinding methods----------------------------------------------------\\

	public double getX() {return currentNodeX.value.getX();}
	public double getY() {return currentNodeX.value.getY();}
	public double getRadius() {return currentNodeX.value.getCollisionRadius();}
	public ICollidable getValue() {return currentNodeX.value;}
	public boolean previous() {
		if (currentNodeX.previous != null) {
			currentNodeX = currentNodeX.previous; return true;
		} else {
			return false;
		}
	}
	public boolean next() {
		if (currentNodeX.next != null) {
			currentNodeX = currentNodeX.next;
			return true;
		}
		else {
			return false;
		}
	}

//---------------------------------Inner class for value of start nodes-----------------------------------------------\\

	/**
	 * A dummy class used for the start nodes in the lists.
	 */
	private class CollidableDummy implements ICollidable{
		@Override
		public float getX() {
			return -1000000;
		}

		@Override
		public float getY() {
			return -1000000;
		}

		@Override
		public double getCollisionRadius() {
			return 0;
		}

		@Override
		public double getInteractionRadius() {
			return 0;
		}

		@Override
		public double getSurroundingRadius() {
			return 0;
		}

		@Override
		public void addToInteractableX(ICollidable rhs) {}

		@Override
		public void addToInteractableY(ICollidable rhs) {}

		@Override
		public void checkInteractables(){}

		@Override
		public void addToSurroundingX(ICollidable rhs) {}

		@Override
		public void addToSurroundingY(ICollidable rhs) {}

		@Override
		public void checkSurroundings(){}

		@Override
		public void interacted(Character rhs) {

		}

		@Override
		public void consumed(Character rhs) {

		}

		@Override
		public void attacked(Character rhs) {

		}

		@Override
		public boolean toBeRemoved() {
			return false;
		}

		@Override
		public RenderObject getRenderObject(){return null;}

		@Override
		public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
			return null;
		}
	}

//----------------------------Inner class used to create custom linked list-------------------------------------------\\

	/**
	 * A datastructure designed to hold ICollidables in a ordered list.
	 */
	private class Node{

		private ICollidable value;
		private Node previous;
		private Node next;

		/**
		 * @param value the ICollidable that is to be stored in the list.
		 * @param previous previous node in the list.
		 * @param next the next node in the list.
		 */
		public Node(ICollidable value, Node previous, Node next){
			this.value = value;
			this.previous = previous;
			this.next = next;

		}
	}
}
