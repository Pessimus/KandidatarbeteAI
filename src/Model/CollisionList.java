package Model;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;

/**
 * Created by Martin on 24/02/2016.
 */
public class CollisionList {

	private Node currentNodeX;
	private Node startNodeX;
	private Node startNodeY;
	private int size = 0;

	public double getX() {return currentNodeX.getValue().getX();}
	public double getY() {return currentNodeX.getValue().getY();}
	public double getRadius() {return currentNodeX.getValue().getCollisionRadius();}
	public ICollidable getValue() {return currentNodeX.getValue();}
	public boolean previous() {if (currentNodeX.getPrevious() != null) {currentNodeX = currentNodeX.getPrevious(); return true;} else {return false;}}
	public boolean next() {
		if (currentNodeX.getNext() != null) {
			currentNodeX = currentNodeX.getNext();
			return true;
		}
		else {
			return false;
		}
	}




	public CollisionList(){
		this.startNodeX = new Node(new ICollidable() {
			@Override
			public float getX() {
				return -10000;
			}

			@Override
			public float getY() {
				return -10000;
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

			public void checkInteractables(){}

			@Override
			public void addToSurroundingX(ICollidable rhs) {}

			@Override
			public void addToSurroundingY(ICollidable rhs) {}

			public void checkSurroundings(){}

			@Override
			public RenderObject getRenderObject(){return null;}

			@Override
			public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
				return null;
			}
		}, null, null);
		this.startNodeY = new Node(new ICollidable() {
			@Override
			public float getX() {
				return -10000;
			}

			@Override
			public float getY() {
				return -10000;
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

			public void checkInteractables(){}

			@Override
			public void addToSurroundingX(ICollidable rhs) {}

			@Override
			public void addToSurroundingY(ICollidable rhs) {}

			public void checkSurroundings(){}

			@Override
			public RenderObject getRenderObject(){return null;}

			@Override
			public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
				return null;
			}

		}, null, null);
		this.currentNodeX = this.startNodeX;
		//this.currentNodeY = this.startNodeY;
	}

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

	public void add(ICollidable addValue){

		//Add X-------------------
		Node loopNodeX = startNodeX;
		while (loopNodeX.next != null){
			loopNodeX = loopNodeX.next;
		}
		loopNodeX.next = new Node(addValue, loopNodeX,null);

		//Add Y-------------------
		Node loopNodeY = startNodeY;
		while (loopNodeY.next != null){
			loopNodeY = loopNodeY.next;
		}
		loopNodeY.next = new Node(addValue, loopNodeY,null);

		this.size++;
	}

	public void remove(ICollidable collidable) {
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
	}

	public int getSize(){
		return this.size;
	}

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

	private void handleInterractionCollisionLeftX(Node node, Node left){
		if(node.value.getX() - left.value.getX() <= node.value.getInteractionRadius()*2){
			node.value.addToInteractableX(left.value);
			handleInterractionCollisionLeftX(node, left.previous);
		}
	}
	private void handleInterractionCollisionRightX(Node node, Node right){
		if(right != null && right.value.getX() - node.value.getX() <= node.value.getInteractionRadius()*2){
			node.value.addToInteractableX(right.value);
			handleInterractionCollisionRightX(node, right.next);
		}
	}
	private void handleInterractionCollisionLeftY(Node node, Node left){
		if(node.value.getY() - left.value.getY() <= node.value.getInteractionRadius()*2){
			node.value.addToInteractableY(left.value);
			handleInterractionCollisionLeftY(node, left.previous);
		}
	}
	private void handleInterractionCollisionRightY(Node node, Node right){
		if (right != null && right.value.getY() - node.value.getY() <= node.value.getInteractionRadius()*2){
			node.value.addToInteractableY(right.value);
			handleInterractionCollisionRightY(node, right.next);
		}
	}


	private void handleSurroundingsCollisionLeftX(Node node, Node left){
		if(node.value.getX() - left.value.getX() <= node.value.getSurroundingRadius()*2){
			node.value.addToSurroundingX(left.value);
			handleSurroundingsCollisionLeftX(node, left.previous);
		}
	}
	private void handleSurroundingsCollisionRightX(Node node, Node right){
		if(right != null && right.value.getX() - node.value.getX() <= node.value.getSurroundingRadius()*2){
			node.value.addToSurroundingX(right.value);
			handleSurroundingsCollisionRightX(node, right.next);
		}
	}
	private void handleSurroundingsCollisionLeftY(Node node, Node left){
		if(node.value.getY() - left.value.getY() <= node.value.getSurroundingRadius()*2){
			node.value.addToSurroundingY(left.value);
			handleSurroundingsCollisionLeftY(node, left.previous);
		}
	}
	private void handleSurroundingsCollisionRightY(Node node, Node right){
		if (right != null && right.value.getY() - node.value.getY() <= node.value.getSurroundingRadius()*2){
			node.value.addToSurroundingY(right.value);
			handleSurroundingsCollisionRightY(node, right.next);
		}
	}

	//TODO null check
	//TODO should not return void?
	public void handleCollision(){

		//System.out.println("HandleCollision Lenght = "+this.size);

		this.sortX();
		this.sortY();

		//this.print();

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

	public void print(){
		Node tmp = startNodeX.next;

		while(tmp != null){
			System.out.print(tmp.value.getX()+" - ");
			tmp = tmp.next;
		}
		Node tmp2 = startNodeY.next;
		System.out.println();
		while(tmp2 != null){
			System.out.print(tmp2.value.getY()+" - ");
			tmp2 = tmp2.next;
		}
		System.out.println();
		System.out.println();
		System.out.println();


	}

	private class Node{

		private ICollidable value;
		private Node previous;
		private Node next;


		public Node(ICollidable value, Node previous, Node next){
			this.value = value;
			this.previous = previous;
			this.next = next;

		}

		public boolean isLargerThenX(Node rhs){
			if(rhs == null){
				return true;
			}else{
				return (this.value.getX() - rhs.value.getX())>0;
			}
		}

		public Node getNext(){
			return this.next;
		}

		public Node getPrevious(){
			return this.previous;
		}

		public ICollidable getValue(){
			return this.value;
		}

	}
}
