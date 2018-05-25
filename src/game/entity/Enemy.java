package game.entity;

import game.data.Loader;
import game.map.Map;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class Enemy extends MovingPerson {
    private Entity model;
    private Vector3f direction;
    private Vector3f playerPos;

    public Enemy(Vector3f position, Loader loader, Map map){
        super(map);
        model = new Entity(loader.loadTexturedModel("holyshititworks"),position, new Vector3f(0,0,0), 2f);

        this.position = position;
        this.previous = new Vector3f(0, 35, 0);
        this.rotation = new Vector3f(0, 0, 0);

        this.width = 3;
        this.height = 2.2f;

        calcGroundHeight();

        runSpeed = 30.0f;
        walkSpeed = 15.0f;
        gravity = 0.625f;
        jumpForce = 20.0f;

        direction = new Vector3f();
    }

    public void update(float dt, Vector3f playerPosition){
        this.playerPos = playerPosition;
        //System.out.println(position);
        //Not sure if the math is correct
        direction.x = (float)Math.cos(Math.toRadians(rotation.x + 90));
        direction.z = (float)Math.sin(Math.toRadians(rotation.y + 90));
        direction.y = (float)Math.cos(Math.toRadians(rotation.x + 90));

        running = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        if(running){
            speed = runSpeed * dt;
        }else{
            speed = walkSpeed * dt;
        }

        verticalMovement(dt);

        move(dt);

        if(!facingPlayer()){
            updateDirection();
        }


    }
    //check if the enemy is facing the player
    public boolean facingPlayer(){

        return true;
    }
    //update the direction of the enemy
    public void updateDirection(){

    }
    public void move(float dt){
        //System.out.println(position);
        boolean moved = false;

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
    public Entity getEntity(){
        return model;
    }
}
