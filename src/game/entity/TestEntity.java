package game.entity;

import org.lwjgl.util.vector.Vector3f;

import game.Game;
import game.data.Loader;

public class TestEntity extends Entity{
	
	public TestEntity(Vector3f position, Loader loader){
		super(loader.loadTexturedModel("holyshititworks"), position, new Vector3f(0, 0, 0), 1);
	}

	@Override
	public void update(float dt) {
		Vector3f rot = getRotation();
		rot.setX(rot.getX()+0.5f);
		rot.setZ(rot.getZ()+0.5f);
		
		setRotation(rot);
		
	}

}
