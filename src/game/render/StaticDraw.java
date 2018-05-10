package game.render;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class StaticDraw {
    public static void draw(float x, float y, Texture texture, float h, float w) {
        glPushMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        glTranslatef(x, y, 0);

        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);

            glTexCoord2f(0, texture.getHeight());
            glVertex2f(0, -h);

            glTexCoord2f(texture.getWidth(), texture.getHeight());
            glVertex2f(w, -h);

            glTexCoord2f(texture.getWidth(), 0);
            glVertex2f(w, 0);
        }
        glEnd();
        glPopMatrix();
    }
}
