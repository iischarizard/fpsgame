package game.entity;

import game.data.SoundEffects;
import game.map.Map;
import game.model.Bounds;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Matthew Hong on 5/10/2018.
 */
public abstract class MovingPerson {
    // Position and movement
    public Vector3f position;
    public Vector3f previous;
    public Vector3f rotation;
    public float dy, groundHeight;

    // Bounds
    public float width = 2;
    public float height = 4;
    public float length = 2;

    public boolean onGround;
    public boolean running;

    // Speed constants
    public float runSpeed;
    public float walkSpeed;
    public float speed;
    public float gravity;
    public float jumpForce;

    public Map map;
    public SoundEffects sfx = new SoundEffects();

    public MovingPerson(Map map){
        this.map = map;
    }

    public void moving(float dt){};

    public void calcGroundHeight() {
        groundHeight = Float.NEGATIVE_INFINITY;
        for (Bounds b : map.getTexturedModelsArray()) {
            if ((position.x <= b.getMaxX() && position.x + width >= b.getMinX()) && //
                    (position.y >= b.getMaxY() && groundHeight <= b.getMaxY()) && //
                    (position.z <= b.getMaxZ() && position.z + length >= b.getMinZ())) {
                groundHeight = b.getMaxY()+height;
            }

        }
    }
    public boolean collides() {
        for (Bounds b : map.getTexturedModelsArray()) {
            if ((position.x <= b.getMaxX() && position.x + width >= b.getMinX()) && //
                    (position.y < b.getMaxY() && position.y + height >= b.getMinY()) && //
                    (position.z <= b.getMaxZ() && position.z + length >= b.getMinZ())) //
                return true;
        }
        return false;
    }
    public void verticalMovement(float dt) {
        if (dy != 0) {
            previous.setY(position.getY());
            position.y += dy;
            if (collides()) {
                if (dy > 0) {
                    position.setY(previous.getY());
                    dy = 0;
                }
                else {
                    position.y = groundHeight;
                }
            }
        }
        if (position.y <= groundHeight) {
            if (!onGround) {
                onGround = true;
                position.y = groundHeight;
                dy = 0;
            }
        }
        else {
            onGround = false;
        }
        if (!onGround) {
            dy -= gravity * dt;
        }
    }
    public Map getMap(){
        return map;
    }
    public void setPosition(Vector3f pos){
        this.position = pos;
    }
    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getLenght() {
        return length;
    }
}
