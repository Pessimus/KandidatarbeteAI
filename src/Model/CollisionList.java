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
			public void addToCollideX(ICollidable rhs) {}

			@Override
			public void addToCollideY(ICollidable rhs) {}

			@Override
			public RenderObject getRenderObject(){return null;}

			@Override
			public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
				return null;
			}

			@Override
			public void checkCollision(){}
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
				return -10000;
			}

			@Override
			public void addToCollideX(ICollidable rhs) {}

			@Override
			public void addToCollideY(ICollidable rhs) {}

			@Override
			public RenderObject getRenderObject(){return null;}

			@Override
			public RenderObject.RENDER_OBJECT_ENUM getRenderType() {
				return null;
			}

			@Override
			public void checkCollision(){}
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
		previous.next.previous = previous;
	}

	private void handleCollisionLeftX(Node node, Node left){
		if(node.value.getX() - left.value.getX() <= node.value.getCollisionRadius()*2){
			node.value.addToCollideX(left.value);
			handleCollisionLeftX(node, left.previous);
		}
	}

	private void handleCollisionRightX(Node node, Node right){
		if(right.value.getX() - node.value.getX() <= node.value.getCollisionRadius()*2){
			node.value.addToCollideX(right.value);
			handleCollisionRightX(node, right.previous);
		}
	}


	private void handleCollisionLeftY(Node node, Node left){
		if(node.value.getY() - left.value.getY() <= node.value.getCollisionRadius()*2){
			node.value.addToCollideY(left.value);
			handleCollisionLeftY(node, left.previous);
		}
	}

	private void handleCollisionRightY(Node node, Node right){
		if (right.value.getY() - node.value.getY() <= node.value.getCollisionRadius()*2){
			node.value.addToCollideY(right.value);
			handleCollisionRightY(node, right.previous);
		}
	}

	//TODO null check
	//TODO should not return void?
	public void handleCollision(){

		//System.out.println("HandleCollision Lenght = "+this.size);

		this.sortX();
		this.sortY();

		//try {
			Node loopXNode = startNodeX;
			while (loopXNode.next != null) {
				loopXNode = loopXNode.next;
				handleCollisionLeftX(loopXNode, loopXNode.previous);
				handleCollisionRightX(loopXNode, loopXNode.next);
			}

			Node loopYNode = startNodeY;
			while (loopYNode.next != null) {
				loopYNode = loopYNode.next;
				handleCollisionLeftY(loopYNode, loopYNode.previous);
				handleCollisionRightY(loopYNode, loopYNode.next);
			}

			Node loop = startNodeX;
			while (loop.next != null) {
				loop = loop.next;
				loop.value.checkCollision();
			}
		//}catch (Exception e){

		//}

	}

	public void print(){
		Node tmp = startNodeX.next;

		while(tmp != null){
			System.out.println(tmp.value.getY());
			tmp = tmp.next;
		}
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
