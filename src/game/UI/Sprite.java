package game.UI;

import java.io.IOException;

import game.render.StaticDraw;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Sprite {
 
    private Texture texture;
    private float width;
    private float height;
    private float x,y;
    public Sprite(String filePath) {
    	try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filePath));
            width = texture.getImageWidth();
            height = texture.getImageHeight();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    public void setWidth(float width_){width = width_;}
    public void setHeight(float height_){height = height_;}
    public float getWidth(){return width;}
    public float getHeight(){return height;}

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void draw() {
        StaticDraw.draw(x,y,texture, height,width);
    }
    public Texture getTexture() {
        return texture;
    }
}
