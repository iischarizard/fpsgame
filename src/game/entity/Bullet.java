package game.entity;

import game.data.Loader;
import game.model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import java.util.List;
/**
 * Created by Katkr on 4/23/2018.
 */
public class Bullet {

    private float yRotation;
    private float xRotation;
    private float speed;
    private float startTime;
    private float elapsedTime;
    private Entity model;


    public Bullet(float x, float y, float z,float speed,float rotationY, float rotationX){
        startTime = System.nanoTime();
        this.speed = speed;
        this.yRotation = rotationY;
        this.xRotation = rotationX;
        this.model = new Entity(Loader.loadTexturedModel("holyshititworks"),new Vector3f(x,y,z), new Vector3f(0,0,0), .2f);

    }
    public void move(){
        elapsedTime = System.nanoTime() - startTime;
        model.setX(Math.sin(Math.toRadians(yRotation)) * speed * 20) ;
        model.setY(Math.sin(Math.toRadians(xRotation)) * speed * 20);
        model.setZ(Math.cos(Math.toRadians(yRotation)) * speed * 20);
    }
    public Enemy collides(List<Enemy> enemies){
        for (Enemy e : enemies) {
            System.out.println((model.getX() <= e.getEntity().getModel().getMaxX() && model.getX() >= e.getEntity().getModel().getMinX())+"        "+(model.getY() <= e.getEntity().getPosition().y+e.getHeight())+"     "+(model.getZ() <= e.getEntity().getModel().getMaxZ() && model.getZ() >= e.getEntity().getModel().getMinZ()));

            if ((model.getX() <= e.getEntity().getModel().getMaxX()+e.getWidth() && model.getX() >= e.getEntity().getModel().getMinX()-e.getWidth()) && //
                    (model.getZ() <= e.getEntity().getModel().getMaxZ()+e.getWidth() && model.getZ() >= e.getEntity().getModel().getMinZ()-e.getWidth()) &&
                    model.getY() <= e.getEntity().getPosition().y+e.getHeight()) {
                e.getEntity().getModel().setHit(true);
                return e;
            }
        }
        return null;
    }
    public boolean elapsedTime(){
        return elapsedTime/ 1000000000.0 > 4;
    }
    public Vector3f getPosition(){
        return model.getPosition();
    }
    public Entity getModel(){
        return model;
    }
}