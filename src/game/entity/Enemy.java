package game.entity;

import java.util.ArrayList;
import java.util.Collections;

import game.data.SoundEffects;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import game.data.Loader;
import game.map.Map;
import game.model.TexturedModel;
import pathfinding.Grid;
import pathfinding.Node;

/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class Enemy extends MovingPerson {
    public static String[] fileNames = {"elfOniiSama","holyshititworks","rae","azunyan","yui"};
    public static SoundEffects sfx = new SoundEffects();
    private Entity model;
    private Vector3f direction;
    private Vector3f playerPos;

    private Vector3f targetLocation;
    private boolean pathfinding;
    private int vision;

    private ArrayList<Vector3f> path;
    private int pathIndex = 0;
    private Grid grid;

    public Enemy(Vector3f position, Loader loader, Map map, Grid grid_){
        super(map);
        grid = grid_;
        path = new ArrayList<Vector3f>();
        model = new Entity(loader.loadTexturedModel(fileNames[(int)(Math.random()*fileNames.length)]),position, new Vector3f(0,0,0), 1f);

        vision = 70;

        targetLocation = new Vector3f(0, 0, 0);
        pathfinding = false;

        this.position = position;
        this.previous = new Vector3f(0, 35, 0);
        this.rotation = new Vector3f(0, 0, 0);

        this.width = 3;
        this.height = 2.2f;

        String[] sfxnoises = {"res/sfx/pistol.wav","res/sfx/rifle.wav"};
        sfx.loadSongs(sfxnoises,"res/sfx/");

        calcGroundHeight();

        runSpeed = 30.0f;
        walkSpeed = 20.0f;
        gravity = 0.625f;
        jumpForce = 20.0f;

        direction = new Vector3f(0, 0, 0);
        targetNode = new Node(new Vector3f(0, 0, 0));
    }
    private Node targetNode;
    public ArrayList<Vector3f> findPath(){
    	ArrayList<Node> open = new ArrayList<Node>();
    	ArrayList<Node> openPrev = new ArrayList<Node>();
    	ArrayList<Node> closed = new ArrayList<Node>();
    	float min = Float.MAX_VALUE;
    	Node m = null;
    	for(Node n : grid.getNodes()){
    		if(n.getCost(position)<min){
    			min = n.getCost(position);
    			m = n;
    		}
    	}
    	m.setCost(m.getCost(targetNode.getPosition()));
    	System.out.println("s: " + m.getPosition());
    	open.add(m);
    	
    	Node n = null;
    	try{
	    	while(open.size()!=0){
	    		for(Node a : open){
	    			System.out.print(a.getPosition()+ " -- ");
	    		}
	    		System.out.println();
	    		n = selectNode(open);
	    		if(n.equals(targetNode) || open.equals(openPrev)){
	    			return buildPath(n);
	    		}
	    		openPrev = open;
	    		open.remove(n);
	    		closed.add(n);
	    		
	    		for(Vector3f a : n.getAdjacentNodePositions()){
	    			Node temp = null;
	    			for(Node z : grid.getNodes()){
	    				if(z.getPosition().equals(a)){
	    					temp = z;
	    					break;
	    				}
	    			}
	    			if(!closed.contains(temp)){
	    				if(!open.contains(temp)){
	    					open.add(temp);
	    				}else{
	    					if(temp.getCost()>temp.getCost(targetNode.getPosition())+temp.getCost(n.getPosition())+n.getCost()){
	        	    			temp.setPrevious(n);
	        					temp.setCost(temp.getCost(temp.getPrevious().getPosition())+temp.getPrevious().getCost()+temp.getCost(targetNode.getPosition()));
	    					}
	    				}
	    			}
	    		}
	    		
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	ArrayList<Vector3f> f = new ArrayList<Vector3f>();
    	f.add(m.getPosition());
    	return f;
    }

    public ArrayList<Vector3f> buildPath(Node n){
        ArrayList<Vector3f> path = new ArrayList<Vector3f>();
        path.add(targetLocation);
        while(n != null){
            path.add(n.getPosition());
            n = n.getPrevious();
        }
        Collections.reverse(path);
        return path;
    }

    public Node selectNode(ArrayList<Node> open){
        Node n = null;
        float min = Float.MAX_VALUE, g, h, f;
        for(int i = 0; i < open.size(); i++){
            Node temp = open.get(i);
            if(temp.getPrevious() == null)
                g = temp.getCost(open.get(0).getPosition());
            else
                g = temp.getCost(temp.getPrevious().getPosition());
            h = temp.getCost();
            f = g + h;
            if(n == null || f < min){
                min = f;
                n = temp;
            }
        }

        return n;
    }

    public void update(float dt, Vector3f playerPosition, ArrayList<TexturedModel> mapObjs,Player player){
        this.playerPos = playerPosition;
        if(distanceBetween(playerPos, position)<=vision){
            sfx.playFile(1);
            if((int)(Math.random()*6)+1 == (int)(Math.random()*6)+1 ){
                vision -= 50;
                player.decreaseHealth();
            }
        }else{
        }
        targetLocation.set(playerPos.getX(), playerPos.getY(), playerPos.getZ());
        float min = Float.MAX_VALUE;
        Node m = null;
        for(Node n : grid.getNodes()){
            if(n.getCost(targetLocation)<min){
                min = n.getCost(targetLocation);
                m = n;
            }
        }
    	targetLocation.set(playerPos.getX(), playerPos.getY(), playerPos.getZ());
    	float min = Float.MAX_VALUE;
    	Node m = null;
    	for(Node n : grid.getNodes()){
    		if(n.getCost(targetLocation)<min){
    			min = n.getCost(targetLocation);
    			m = n;
    		}
    	}
    	if(targetNode == null || !targetNode.equals(m)){  		
    		targetNode = m;
    		path = findPath();
    		pathIndex = 0;
    	}
    	
    	

        //Not sure if the math is correct
        direction.x = (float)Math.cos(Math.toRadians(rotation.x + 90));
        direction.z = (float)Math.sin(Math.toRadians(rotation.y + 90));
        direction.y = (float)Math.cos(Math.toRadians(rotation.x + 90));

        //System.out.println(direction);

        running = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        if(running){
            speed = runSpeed * dt;
        }else{
            speed = walkSpeed * dt;
        }

        verticalMovement(dt);

        //move(dt);
        pathFind(dt);

        if(!facingPlayer()){
            updateDirection();
        }

    }

    public void pathFind(float dt){
        direction.set(path.get(pathIndex).getX()-position.getX(), position.getY(), path.get(pathIndex).getZ()-position.getZ());
        direction.normalise();
        if(distanceBetween(path.get(pathIndex), position)>3){
            previous.setX(position.getX());
            position.x += direction.getX() * speed;
            if (collides()){
                position.x = previous.x;
                pathIndex--;
                if(pathIndex<0)
                    pathIndex = 0;
            }
            previous.setZ(position.getZ());
            position.z += direction.getZ() * speed;
            if (collides()){
                position.z = previous.z;
                pathIndex--;
                if(pathIndex<0)
                    pathIndex = 0;
            }
        }else{
            pathIndex++;
            if(pathIndex == path.size())
                pathIndex--;
        }
    }

    //check if the enemy is facing the player
    public boolean facingPlayer(){

        return true;
    }
    //update the direction of the enemy
    public void updateDirection(){

    }

    private float distanceBetween(Vector3f a, Vector3f b){
        return (float)Math.sqrt(Math.pow(a.getX()-b.getX(), 2)+Math.pow(a.getY()-b.getY(), 2)+Math.pow(a.getZ()-b.getZ(), 2));
    }



    public void move(float dt){

        boolean moved = true;

        direction.set(targetLocation.getX()-position.getX(), direction.getY(), targetLocation.getZ()-position.getZ());
        direction.normalise();
        if(distanceBetween(targetLocation, position)>15){
            previous.setX(position.getX());
            position.x += direction.getX() * speed;
            if (collides())
                position.x = previous.x;
            previous.setZ(position.getZ());
            position.z += direction.getZ() * speed;
            if (collides())
                position.z = previous.z;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {

            previous.setX(position.getX());
            position.x += Math.sin(Math.toRadians(rotation.y)) * speed;
            if (collides())
                position.x = previous.x;
            previous.setZ(position.getZ());
            position.z -= Math.cos(Math.toRadians(rotation.y)) * speed;
            if (collides())
                position.z = previous.z;
            moved = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
            previous.setX(position.getX());
            position.x -= Math.sin(Math.toRadians(rotation.y)) * speed;
            if (collides()) position.x = previous.x;
            previous.setZ(position.getZ());
            position.z += Math.cos(Math.toRadians(rotation.y)) * speed;
            if (collides()) position.z = previous.z;
            moved = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_J)) {
            previous.setX(position.getX());
            position.x += Math.sin(Math.toRadians(rotation.y - 90)) * speed;
            if (collides()) position.x = previous.x;
            previous.setZ(position.getZ());
            position.z -= Math.cos(Math.toRadians(rotation.y - 90)) * speed;
            if (collides()) position.z = previous.z;
            moved = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
            previous.setX(position.getX());
            position.x += Math.sin(Math.toRadians(rotation.y + 90)) * speed;
            if (collides()) position.x = previous.x;
            previous.setZ(position.getZ());
            position.z -= Math.cos(Math.toRadians(rotation.y + 90)) * speed;
            if (collides()) position.z = previous.z;
            moved = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_M) && onGround) {
            dy = jumpForce * dt;
            onGround = false;
            moved = true;
        }
        if (moved) {
            calcGroundHeight();
        }
    }
    public Entity getModel(){
        model.setRotation(getRotation());
        return model;
    }
}