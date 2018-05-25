package game.entity;

import game.Game;
import game.data.SoundEffects;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import game.model.Bounds;
import game.map.Map;

import java.util.ArrayList;

public class Player extends MovingPerson{
	public static boolean outOfMenu = false;


	private ArrayList<Bullet> bullets = new ArrayList <>();
	private SoundEffects sfx = new SoundEffects();
	private float bulletSpeed = 100f;
	
	private boolean flight = false;

	public Player(Map map) {
		super(map);

		position = new Vector3f(0, 12.207812f, 0);
		previous = new Vector3f(0, 35, 0);
		rotation = new Vector3f(0, 0, 0);

		runSpeed = 30.0f;
		walkSpeed = 20.0f;
		gravity = 0.625f;
		jumpForce = 20.0f;

		String[] sfxnoises = {"res/sfx/pistol.wav"};
		sfx.loadSongs(sfxnoises,"res/sfx/");

		calcGroundHeight();
	}

	public void update(float dt, ArrayList<Enemy> enemies) {

		boolean moved = false;
		running = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		if(running){
			speed = runSpeed * dt;
		}else{
			speed = walkSpeed * dt;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){

		}
		if(!flight)
			verticalMovement(dt);
//		/System.out.println(position);
		ArrayList<Bullet> removeBullets = new ArrayList <>();
		for(Bullet b : bullets){
			b.move();
			if(b.collides(map.getTexturedModelsArray(), enemies, this)){
				removeBullets.add(b);
			}
			//System.out.println(b.getPosition());
		}
		for(Bullet collidedBullets : removeBullets){
			bullets.remove(collidedBullets);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			position.set(0.0f, 35.0f, 0.0f);
			previous.set(0.0f, 35.0f, 0.0f);
			calcGroundHeight();
			dy = 0.0f;
		}
		/*while(Keyboard.next()) {
			if (Keyboard.getEventKeyState()) 
				if (Keyboard.isKeyDown(Keyboard.KEY_BACKSLASH)) 
					flight = !flight;
		}*/
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && flight){
			previous.setY(position.y);
			position.y -= speed;
			if(collides()) position.y = previous.y;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_L) && !Mouse.isGrabbed())
			Mouse.setGrabbed(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Mouse.isGrabbed())
			Mouse.setGrabbed(false);

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
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
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			previous.setX(position.getX());
			position.x -= Math.sin(Math.toRadians(rotation.y)) * speed;
			if (collides()) position.x = previous.x;
			previous.setZ(position.getZ());
			position.z += Math.cos(Math.toRadians(rotation.y)) * speed;
			if (collides()) position.z = previous.z;
			moved = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			previous.setX(position.getX());
			position.x += Math.sin(Math.toRadians(rotation.y - 90)) * speed;
			if (collides()) position.x = previous.x;
			previous.setZ(position.getZ());
			position.z -= Math.cos(Math.toRadians(rotation.y - 90)) * speed;
			if (collides()) position.z = previous.z;
			moved = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			previous.setX(position.getX());
			position.x += Math.sin(Math.toRadians(rotation.y + 90)) * speed;
			if (collides()) position.x = previous.x;
			previous.setZ(position.getZ());
			position.z -= Math.cos(Math.toRadians(rotation.y + 90)) * speed;
			if (collides()) position.z = previous.z;
			moved = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if(!flight  && onGround){
				dy = jumpForce * dt;
				onGround = false;
				moved = true;
			}else if(flight){
				previous.setY(position.getY());
				position.y += speed;
				if(collides())position.y = previous.y;
			}
		}
//		/Mouse.setCursorPosition(1280/2,720/2);
		if (Mouse.isGrabbed()) {
			rotation.y += (float) Mouse.getDX() * 7.25f * dt;
			rotation.x -= (float) Mouse.getDY() * 7.25f * dt;
			if (rotation.x < -90)
				rotation.x = -90;
			else if (rotation.x > 90)
				rotation.x = 90;
		}
		//System.out.println(rotation);

		while(Mouse.next() && outOfMenu) {
			if (Mouse.getEventButtonState()) {
				if (Mouse.isButtonDown(0)){
					//System.out.println("PLAYER POS: "+position);
					bullets.add(new Bullet(position.x,position.y+4,position.z,bulletSpeed*dt,rotation.y,rotation.x));
					sfx.playFile(0);
				}
			}
		}
		if (moved) {
			calcGroundHeight();
		}
	}
	public static void setOutOfMenu(){
		outOfMenu = true;
	}

	public void toggleFlight(){
		flight = !flight;
	}


}