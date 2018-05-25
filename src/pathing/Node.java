package pathing;

import org.lwjgl.util.vector.Vector3f;

public class Node {
	/*
	private Vector3f position;
	private float cost;
	private Node previous;
	
	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node(Vector3f position_){
		position = position_;
		cost = Float.MAX_VALUE;
		previous = null;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getCost(Vector3f targetLoc){
		Vector3f a = getPosition(), b = targetLoc;
    	return (float)Math.sqrt(Math.pow(a.getX()-b.getX(), 2)+Math.pow(a.getY()-b.getY(), 2)+Math.pow(a.getZ()-b.getZ(), 2));
	}
	@Override
	public boolean equals(Object b){
		Node a = (Node)b;
		if(a == null)
			return false;
		if(a.getPosition().equals(getPosition())&&a.getCost() == getCost()){
			if(a.getPrevious() == null && getPrevious() != null)
				return false;
			if(a.getPrevious() != null && getPrevious() == null)
				return false;
			if(a.getPrevious() == null && getPrevious() == null)
				return true;
			if(a.getPrevious().equals(getPrevious()));
				return true;
		}
		return false;
	}
	*/
}
