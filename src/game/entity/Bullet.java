package game.entity;

import java.util.ArrayList;
import java.util.List;

import game.data.Loader;
import org.lwjgl.util.vector.Vector3f;

import game.model.TexturedModel;
/**
 * Created by Katkr on 4/23/2018.
 */
public class Bullet {

    private float yRotation;
    private float xRotation;
    private float speed;
    private float aliveTime;
    private Entity model;



    public Bullet(float x, float y, float z,float speed,float rotationY, float rotationX){
        this.model = new Entity(Loader.loadTexturedModel("holyshititworks"),new Vector3f(x,y,z), new Vector3f(0,0,0), .2f);
        this.speed = speed;
        this.yRotation = rotationY;
        this.xRotation = rotationX;
        System.out.println("testes");
        aliveTime = System.nanoTime();

    }
    
    
    public void move(){
        model.setX(Math.sin(Math.toRadians(yRotation)) * speed *10);
        model.setZ(Math.cos(Math.toRadians(yRotation)) * speed *10);
        model.setY(Math.sin(Math.toRadians(xRotation)) * speed *10);
        //System.out.println(model.getPosition());
    }
    public boolean collides(List<TexturedModel> texturedArray, ArrayList<Enemy> enemies){
    	//System.out.println(aliveTime-System.nanoTime());
    	if(System.nanoTime()-aliveTime>1500000000){
    		//System.out.println("hm");
    		return true;
    	}
        for (TexturedModel b : texturedArray) {
            if ((model.getPosition().x <= b.getMaxX() && model.getPosition().x >= b.getMinX()) && //
                    (model.getPosition().y < b.getMaxY() && model.getPosition().y >= b.getMinY()) && //
                    (model.getPosition().z <= b.getMaxZ() && model.getPosition().z >= b.getMinZ())) {
                //System.out.println(b.getTexture().getMaterial());
                return true;
            }
        }
        for (Enemy e : enemies) {
            if ((model.getPosition().x <= e.getPosition().getX()+e.getWidth()*2 && model.getPosition().x >= e.getPosition().getX()-e.getWidth()*2) && //
                    (model.getPosition().y < e.getPosition().getY()+e.getHeight()*2 && model.getPosition().y >= e.getPosition().getY()-e.getHeight()*2) && //
                    (model.getPosition().z <= e.getPosition().getZ()+e.getLength()*2 && model.getPosition().z >= e.getPosition().getZ()-e.getLength()*2)) {
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
        return model.getPosition();
    }
    public Entity getModel(){
        return model;
    }
}