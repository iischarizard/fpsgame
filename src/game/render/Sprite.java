package game.render;


import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Sprite {
 
    private Texture texture;
    private float width;
    private float height;
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
    public void draw(float x, float y) {
        glPushMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        glTranslatef(x, y, 0);
 
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
 
            glTexCoord2f(0, texture.getHeight());
            glVertex2f(0, -height);
 
            glTexCoord2f(texture.getWidth(), texture.getHeight());
            glVertex2f(width, -height);
 
            glTexCoord2f(texture.getWidth(), 0);
            glVertex2f(width, 0);
        }
        glEnd();
        glPopMatrix();
    }
}
