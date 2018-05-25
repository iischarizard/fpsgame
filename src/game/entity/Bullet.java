package game.entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import game.model.TexturedModel;
/**
 * Created by Katkr on 4/23/2018.
 */
public class Bullet {
    private Vector3f position;

    private float yRotation;
    private float xRotation;
    private float speed;
    private float aliveTime;



    public Bullet(float x, float y, float z,float speed,float rotationY, float rotationX){
        position = new Vector3f(x,y,z);

        this.speed = speed;
        this.yRotation = rotationY;
        this.xRotation = rotationX;
        System.out.println("testes");
        aliveTime = System.nanoTime();

    }
    
    
    public void move(){
        position.x += Math.sin(Math.toRadians(yRotation)) * speed *10;
        position.z -= Math.cos(Math.toRadians(yRotation)) * speed *10;
        position.y -= Math.sin(Math.toRadians(xRotation)) * speed *10;
       // System.out.println(position);
    }
    public boolean collides(List<TexturedModel> texturedArray, ArrayList<Enemy> enemies, Player p){
    	//System.out.println(aliveTime-System.nanoTime());
    	if(System.nanoTime()-aliveTime>1500000000){
    		//System.out.println("hm");
    		return true;
    	}
        for (TexturedModel b : texturedArray) {
            if ((position.x <= b.getMaxX() && position.x >= b.getMinX()) && //
                    (position.y < b.getMaxY() && position.y >= b.getMinY()) && //
                    (position.z <= b.getMaxZ() && position.z >= b.getMinZ())) {
                b.setHit(true);
                //System.out.println(b.getTexture().getMaterial());
                return true;
            }
        }
        for (Enemy e : enemies) {
            if ((position.x <= e.getPosition().getX()+e.getWidth()*2 && position.x >= e.getPosition().getX()-e.getWidth()*2) && //
                    (position.y < e.getPosition().getY()+e.getHeight()*2 && position.y >= e.getPosition().getY()-e.getHeight()*2) && //
                    (position.z <= e.getPosition().getZ()+e.getLength()*2 && position.z >= e.getPosition().getZ()-e.getLength()*2)) {
                e.setHit(true);
                //System.out.println(b.getTexture().getMaterial());
                return true;
            }
        } 
        /*if ((position.x <= p.getPosition().getX()+e.getWidth()*5 && position.x >= p.getPosition().getX()-e.getWidth()*5) && //
                (position.y < p.getPosition().getY()+e.getHeight()*5 && position.y >= p.getPosition().getY()-e.getHeight()*5) && //
                (position.z <= p.getPosition().getZ()+e.getLength()*5 && position.z >= p.getPosition().getZ()-e.getLength()*5)) {
            p.setHit(true);
            //System.out.println(b.getTexture().getMaterial());
            return true;
        }*/
        return false;
    }
    public Vector3f getPosition(){
        return position;
    }
}