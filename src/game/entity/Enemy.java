package game.entity;

import game.data.Loader;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class Enemy extends Entity {
    private float speed = 0f;
    public Enemy(Vector3f position, Loader loader){
        super(loader.loadTexturedModel("cubeWork"),position, new Vector3f(0,0,0), 1f);
    }

    @Override
    public void update(float dt){
        /*Vector3f temp = this.getPosition();
        temp.setZ(temp.getZ()-.5f);
        this.setPosition(temp);*/

        Vector3f temp = this.getRotation();
        temp.setZ(temp.getZ()-.5f);
        temp.setX(temp.getX()-.5f);
        this.setRotation(temp);
    }
}
