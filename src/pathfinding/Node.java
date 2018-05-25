package pathfinding;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

public class Node {

	private Vector3f position;
	private float cost;
	private Node previous;
	private ArrayList<Vector3f> adjacentNodePositions;
	
	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node(Vector3f position_, Vector3f... args){
		adjacentNodePositions = new ArrayList<Vector3f>();
		if(args.length!=0){
			for(Vector3f v : args){
				adjacentNodePositions.add(v);
			}
		}
		position = position_;
		cost = Float.MAX_VALUE;
		previous = null;
	}

	public float getCost(Vector3f targetLoc){
		Vector3f a = getPosition(), b = targetLoc;
    	return (float)Math.sqrt(Math.pow(a.getX()-b.getX(), 2)+Math.pow(a.getY()-b.getY(), 2)+Math.pow(a.getZ()-b.getZ(), 2));
	}
	public ArrayList<Vector3f> getAdjacentNodePositions() {
		return adjacentNodePositions;
	}

	public void setAdjacentNodePositions(ArrayList<Vector3f> adjacentNodePositions) {
		this.adjacentNodePositions = adjacentNodePositions;
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
}

