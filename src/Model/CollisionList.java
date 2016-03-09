package Model;

/**
 * Created by Martin on 24/02/2016.
 */
public class CollisionList {

	private Node currentNode;
	private Node startNode;

	public double getX() {return currentNode.getValue().getX();}
	public double getY() {return currentNode.getValue().getY();}
	public double getRadius() {return currentNode.getValue().getCollisionRadius();}
	public ICollidable getValue() {return currentNode.getValue();}

	public boolean next() {
		if (currentNode.getNext() != null) {
			currentNode = currentNode.getNext();
			return true;
		}
		else {
			return false;
		}
	}
	public boolean previous() {if (currentNode.getPrevious() != null) {currentNode = currentNode.getPrevious(); return true;} else {return false;}}




	public CollisionList(){
		this.startNode = new Node(new ICollidable() {
			@Override
			public double getX() {
				return -10000;
			}

			@Override
			public double getY() {
				return -10000;
			}

			@Override
			public double getCollisionRadius() {
				return -10000;
			}
		}, null, null);
		this.currentNode = this.startNode;

	}

	//public CollissionItterator Iterator(){}

	public void sort(){
		int itter = 0;
		if(startNode.next == null){
			return;
		}
		boolean hasChanged = true;
		Node sortNode;
		while(hasChanged) {
			sortNode = startNode.next.next;
			hasChanged = false;
			while (sortNode != null) {
				if(sortNode.value.getY() < sortNode.previous.value.getY()){
					swap(sortNode.previous, sortNode);
					hasChanged = true;
				}
				//System.out.println("............"+sortNode.value.getY());
				//System.out.println("////////////"+sortNode.next.value.getY());
				sortNode = sortNode.next;
			}
			//System.out.println("----");
			//this.print();
			itter++;
		}
		System.out.println("itter = " + itter);
	}

	public void add(ICollidable addValue){
		this.currentNode.next = new Node(addValue, currentNode,null);
		this.currentNode = this.currentNode.next;
	}

	private void swap(Node previous, Node next) {
		next.previous = previous.previous;
		previous.next = next.next;

		next.next=previous;
		previous.previous=next;

		next.previous.next = next;
		previous.next.previous = previous;
	}

	public void print(){
		Node tmp = startNode.next;

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
