package pathing;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import game.model.TexturedModel;

public class NavigationMesh {
	/*
	public ArrayList<MeshPolygon> walkableSurfaces = new ArrayList<MeshPolygon>();
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	public NavigationMesh(ArrayList<TexturedModel> mapObj){
		float minHeight = Float.MAX_VALUE;
		TexturedModel floor = null;
		for(TexturedModel b : mapObj){
			if(b.getMinY()<minHeight){
				floor = b;
				minHeight = b.getMinY();
			}
		}
		mapObj.remove(floor);
		for(int i = 0; i < mapObj.size()-1; i ++){
			TexturedModel a = mapObj.get(i);
			ArrayList<TexturedModel> adjacentModels = new ArrayList<TexturedModel>();
			for(int j = i+1; j < mapObj.size(); j++){
				TexturedModel b = mapObj.get(j);
				boolean adjacent = true;
				for(TexturedModel c : mapObj){
					if(!c.equals(a)&&!c.equals(b)){
						if((c.getMaxX()<=b.getMinX()&&c.getMinX()>=a.getMaxX())||(c.getMaxX()<=a.getMinX()&&c.getMinX()>=b.getMaxX())){
							if((c.getMaxZ()>=a.getMinZ()||c.getMaxZ()>=b.getMinZ()||c.getMinZ()<=a.getMaxZ()||c.getMinZ()<=b.getMaxZ())){
								adjacent = false;
								break;
							}
						}
					}
				}
				if(adjacent){
					adjacentModels.add(b);
				}
			}
			for(TexturedModel b : adjacentModels){
				float x1 = Float.MAX_VALUE, x2 = Float.MAX_VALUE, x3 = Float.MAX_VALUE, x4 = Float.MAX_VALUE,
						y1 = Float.MAX_VALUE, y2 = Float.MAX_VALUE, y3 = Float.MAX_VALUE, y4 = Float.MAX_VALUE;
				int deadPair = 0;
				if(a.getMaxX()< b.getMinX()){
					deadPair = 1;
					x1 = a.getMaxX();
					y1 = a.getMaxZ();
					x2 = b.getMinX();
					y2 = b.getMaxZ();
					x3 = a.getMaxX();
					y3 = a.getMinZ();
					x4 = b.getMinX();
					y4 = b.getMinZ();
				}else if(a.getMinX() < b.getMinX()){
					deadPair = 2;
					if(a.getMinZ()>b.getMaxZ()){
						x1 = a.getMinX();
						y1 = a.getMinZ();	
						x2 = a.getMaxX();
						y2 = a.getMinZ();
						x3 = b.getMinX();
						y3 = b.getMaxZ();
						x4 = b.getMaxX();
						y4 = b.getMaxZ();
					}else if(a.getMaxZ()<b.getMinZ()){
						x1 = b.getMinX();
						y1 = b.getMinZ();
						x2 = b.getMaxX();
						y2 = b.getMinZ();
						x3 = a.getMinX();
						y3 = a.getMaxZ();
						x4 = a.getMaxX();
						y4 = a.getMaxZ();
					}
				}else if(a.getMinX()>b.getMaxX()){
					deadPair = 1;
					x1 = b.getMaxX();
					y1 = b.getMaxZ();
					x2 = a.getMinX();
					y2 = a.getMaxZ();
					x3 = b.getMaxX();
					y3 = b.getMinZ();
					x4 = a.getMinX();
					y4 = a.getMinZ();
				}else if(a.getMinX()>b.getMinX()){
					deadPair = 2;
					if(a.getMinZ()>b.getMaxZ()){
						x1 = a.getMinX();
						y1 = a.getMinZ();
						x2 = a.getMaxX();
						y2 = a.getMinZ();
						x3 = b.getMinX();
						y3 = b.getMaxZ();
						x4 = b.getMaxX();
						y4 = b.getMaxZ();
					}else if(a.getMaxZ()<b.getMinZ()){
						x1 = b.getMinX();
						y1 = b.getMinZ();
						x2 = b.getMaxX();
						y2 = b.getMinZ();
						x3 = a.getMinX();
						y3 = a.getMaxZ();
						x4 = a.getMaxX();
						y4 = a.getMaxZ();
					}
				}
				if(x1 != Float.MAX_VALUE){
					MeshPolygon temp = new MeshPolygon(x1, y1, x2, y2, x3, y3, x4, y4, deadPair);
					boolean newPolygon = true;
					for(MeshPolygon m : walkableSurfaces){
						if(temp.equals(m)){
							newPolygon = false;
							break;
						}
					}
					if(newPolygon)
						walkableSurfaces.add(temp);
				}
			}
		}
		
		for(MeshPolygon a : walkableSurfaces){
			switch(a.getDeadPair()){
			case 1:
				Node n = new Node(new Vector3f((a.getTopLeft2D()[0]+a.getTopRight2D()[0])/2, 0, (a.getTopLeft2D()[1]+a.getTopRight2D()[1])/2));
				boolean newNode = true;
				for(Node b : nodes){
					if(n.equals(b)){
						newNode = false;
						break;
					}
				}
				if(newNode)
					nodes.add(n);
				n = new Node(new Vector3f((a.getBottomLeft2D()[0]+a.getBottomRight2D()[0])/2, 0, (a.getBottomLeft2D()[1]+a.getBottomRight2D()[1])/2));
				newNode = true;
				for(Node b : nodes){
					if(n.equals(b)){
						newNode = false;
						break;
					}
				}
				if(newNode)
					nodes.add(n);
				break;
			case 2:
				Node n1 = new Node(new Vector3f((a.getTopLeft2D()[0]+a.getBottomLeft2D()[0])/2, 0, (a.getTopLeft2D()[1]+a.getBottomLeft2D()[1])/2));
				boolean newNode1 = true;
				for(Node b : nodes){
					if(n1.equals(b)){
						newNode1 = false;
						break;
					}
				}
				if(newNode1)
					nodes.add(n1);
				n1 = new Node(new Vector3f((a.getTopRight2D()[0]+a.getBottomRight2D()[0])/2, 0, (a.getTopRight2D()[1]+a.getBottomRight2D()[1])/2));
				newNode = true;
				for(Node b : nodes){
					if(n1.equals(b)){
						newNode1 = false;
						break;
					}
				}
				if(newNode1)
					nodes.add(n1);
				break;
			}
		}
		for(Node n : nodes){
			System.out.println(n.getPosition());
		}
	}
	
	public ArrayList<Node> getNodes(){
		return nodes;
	}
	
	public ArrayList<Node> getAdjacentNodes(Node a){
		ArrayList<Node> nodesA = new ArrayList<Node>();
		float minDistance = Float.MAX_VALUE;
		Node m = null, n = null, o = null, p = null, q = null, r = null, s = null;
		for(Node b : nodes){
			if(a.getCost(b.getPosition())<minDistance){
				minDistance = a.getCost(b.getPosition());
				m = b;
			}
		}
		minDistance = Float.MAX_VALUE;
		for(Node b : nodes){
			if(!b.equals(m)){
				if(a.getCost(b.getPosition())<minDistance){
					minDistance = a.getCost(b.getPosition());
					n = b;
				}
			}
		}
		minDistance = Float.MAX_VALUE;
		for(Node b : nodes){
			if(!b.equals(m)&&!b.equals(n)){
				if(a.getCost(b.getPosition())<minDistance){
					minDistance = a.getCost(b.getPosition());
					o = b;
				}
			}
		}
		minDistance = Float.MAX_VALUE;
		for(Node b : nodes){
			if(!b.equals(m)&&!b.equals(n)&&!b.equals(o)){
				if(a.getCost(b.getPosition())<minDistance){
					minDistance = a.getCost(b.getPosition());
					p = b;
				}
			}
		}
		minDistance = Float.MAX_VALUE;
		for(Node b : nodes){
			if(!b.equals(m)&&!b.equals(n)&&!b.equals(o)&&!b.equals(p)){
				if(a.getCost(b.getPosition())<minDistance){
					minDistance = a.getCost(b.getPosition());
					q = b;
				}
			}
		}
		minDistance = Float.MAX_VALUE;
		for(Node b : nodes){
			if(!b.equals(m)&&!b.equals(n)&&!b.equals(o)&&!b.equals(p)&&!b.equals(q)){
				if(a.getCost(b.getPosition())<minDistance){
					minDistance = a.getCost(b.getPosition());
					r = b;
				}
			}
		}
		minDistance = Float.MAX_VALUE;
		for(Node b : nodes){
			if(!b.equals(m)&&!b.equals(n)&&!b.equals(o)&&!b.equals(p)&&!b.equals(q)&&!b.equals(r)){
				if(a.getCost(b.getPosition())<minDistance){
					minDistance = a.getCost(b.getPosition());
					s = b;
				}
			}
		}
		nodesA.add(m);
		nodesA.add(n);
		nodesA.add(o);
		nodesA.add(p);
		nodesA.add(q);
		nodesA.add(r);
		nodesA.add(s);
		return nodesA;
		
	}
	*/
}
