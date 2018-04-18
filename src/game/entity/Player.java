package game.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import game.map.Block;
import game.map.Map;

public class Player {

	// Position and movement
	private Vector3f position;
	private Vector3f previous;
	private Vector3f rotation;
	private float dy, groundHeight;

	// Bounds
	private float width;
	private float height;
	private float length;

	private boolean onGround;
	private boolean running;

	// Speed constants
	private float runSpeed;
	private float walkSpeed;
	private float speed;
	private float gravity;
	private float jumpForce;

	private Map map;

	public Player(Map map) {
		this.map = map;

		position = new Vector3f(0, 35, 0);
		previous = new Vector3f(0, 35, 0);
		rotation = new Vector3f(0, 0, 0);

		width = 1;
		height = 4;
		length = 1;

		runSpeed = 20.0f;
		walkSpeed = 12.0f;
		gravity = 0.625f;
		jumpForce = 30.0f;


		calcGroundHeight();
	}

	public void update(float dt) {

		running = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		if(running){
			speed = runSpeed * dt;
		}else{
			speed = walkSpeed * dt;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){

		}
		verticalMovement(dt);


		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			position.set(0.0f, 35.0f, 0.0f);
			previous.set(0.0f, 35.0f, 0.0f);
			calcGroundHeight();
			dy = 0.0f;
		}

		if (Mouse.isButtonDown(0) && !Mouse.isGrabbed()) Mouse.setGrabbed(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Mouse.isGrabbed()) Mouse.setGrabbed(false);

		boolean moved = false;
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
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && onGround) {
			dy = jumpForce * dt;
			onGround = false;
			moved = true;
		}

		Vector3f prevRot = new Vector3f(rotation);
		if (Mouse.isGrabbed()) {
			rotation.y += (float) Mouse.getDX() * 7.25f * dt;
			rotation.x -= (float) Mouse.getDY() * 7.25f * dt;
			if (rotation.x < -90) rotation.x = -90;
			else if (rotation.x > 90) rotation.x = 90;
		}

		if (moved) {
			calcGroundHeight();
		}

	}

	private void verticalMovement(float dt) {
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

	private void calcGroundHeight() {
		groundHeight = Float.NEGATIVE_INFINITY;
		for (Block b : map.getBlockArray()) {
			if ((position.x <= b.getMaxX() && position.x + width >= b.getMinX()) && //
					(position.y >= b.getMaxY() && groundHeight <= b.getMaxY()) && //
					(position.z <= b.getMaxZ() && position.z + length >= b.getMinZ())) {
				groundHeight = b.getMaxY();
			}
			//in the air
			if (position.y != groundHeight) {

			}
		}
	}

	private boolean collides() {
		for (Block b : map.getBlockArray()) {
			if ((position.x <= b.getMaxX() && position.x + width >= b.getMinX()) && //
					(position.y < b.getMaxY() && position.y + height >= b.getMinY()) && //
					(position.z <= b.getMaxZ() && position.z + length >= b.getMinZ())) //
				return true;
		}
		return false;
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
