package Model;

/**
 * Created by Martin on 24/02/2016.
 */
public class CollisionList {

	public double getX() {return currentNode.getValue().getX();}
	public double getY() {return currentNode.getValue().getY();}
	public double getRadius() {return currentNode.getValue().getCollisionRadius();}
	public ICollidable getValue() {return currentNode.getValue();}
	public void next() {currentNode.getNext()}
	public void previous() {currentNode.getPrevious()}

	private Node currentNode;

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
