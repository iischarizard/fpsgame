package game.entity;

import game.model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import java.util.List;
/**
 * Created by Katkr on 4/23/2018.
 */
public class Bullet {
    private Vector3f position;
    private Vector3f direction;
    private float yRotation;
    private float xRotation;
    private float speed;



    public Bullet(float x, float y, float z,float speed,float rotationY, float rotationX){
        position = new Vector3f(x,y+4,z);
        direction = new Vector3f();
        this.speed = speed;
        this.yRotation = rotationY;
        this.xRotation = rotationX;
        System.out.println(yRotation);
        direction.x = (float)Math.cos(Math.toRadians(yRotation + 90));
        direction.z = (float)Math.sin(Math.toRadians(yRotation + 90));
        direction.y = (float)Math.cos(Math.toRadians(xRotation + 90));

    }
    public void move(){

        position.x += Math.sin(Math.toRadians(yRotation)) * speed *3;
        position.z -= Math.cos(Math.toRadians(yRotation)) * speed *3;
        position.y -= Math.sin(Math.toRadians(xRotation)) * speed *3;
        System.out.println(position);
    }
    public boolean collides(List<TexturedModel> texturedArray){
        for (TexturedModel b : texturedArray) {
            if ((position.x <= b.getMaxX() && position.x >= b.getMinX()) && //
                    (position.y < b.getMaxY() && position.y >= b.getMinY()) && //
                    (position.z <= b.getMaxZ() && position.z >= b.getMinZ())) {
                b.setHit(true);
                //System.out.println(b.getTexture().getMaterial());
                return true;
            }
        }
        return false;
    }
    public Vector3f getPosition(){
        return position;
    }
}
