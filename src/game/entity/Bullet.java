package game.entity;

import game.model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import java.util.List;
/**
 * Created by Katkr on 4/23/2018.
 */
public class Bullet {
    private Vector3f position;
    private float yRotation;
    private float xRotation;
    private float speed;

    public Bullet(float x, float y, float z,float speed,float rotationY, float rotationX){
        position = new Vector3f(x,y,z);
        this.speed = speed;
        this.yRotation = rotationY;
        this.xRotation = rotationX;

//        System.out.println(rotationX);
//        System.out.println(rotationY);
    }
    public void move(){
        System.out.println(Math.sin(Math.toRadians(xRotation)) * speed*5);
        position.x += Math.sin(Math.toRadians(yRotation)) * speed*5;
        position.z -= Math.cos(Math.toRadians(yRotation)) * speed*5;
        position.y -= Math.sin(Math.toRadians(xRotation)) * speed*5;
    }
    public boolean collides(List<TexturedModel> texturedArray){
        for (TexturedModel b : texturedArray) {
            if ((position.x <= b.getMaxX() && position.x >= b.getMinX()) && //
                    (position.y < b.getMaxY() && position.y >= b.getMinY()) && //
                    (position.z <= b.getMaxZ() && position.z >= b.getMinZ())) {
                b.setHit(true);
                System.out.println(b.getTexture().getMaterial());
                return true;
            }
        }
        return false;
    }
    public Vector3f getPosition(){
        return position;
    }
}
