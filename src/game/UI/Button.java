package game.UI;

import game.render.StaticDraw;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;


/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class Button{

    private Texture texture;
    private String name;
    private float height,width,x,y;

    public Button(String wa, String textName, float x, float y, float h, float w){
        this.name = wa;
        height = h;
        width = w;
        this.x = x;
        this.y = y;
        try{
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(textName));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void draw(){
        StaticDraw.draw(x,y,texture,height,width);
    }
    public String getName(){
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
