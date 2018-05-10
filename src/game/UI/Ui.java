package game.UI;


import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;



import java.awt.Font;
import java.util.ArrayList;

/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class Ui {
    private ArrayList<Button> buttons;


    public Ui(){
        buttons = new ArrayList<Button>();
    }
    public void drawString(int x, int y, String text){

    }
    public void addButton(String wa, String textureName,float x, float y, float h, float w){
        buttons.add(new Button(wa,textureName,x,y,h,w));
    }
    public boolean isButtonClicked(){
        if(Mouse.getX() > 512 && Mouse.getX() < 766 && Mouse.getY() > 217 && Mouse.getY() < 360 )
            return true;
        return false;
    }
    private Button getButton(String buttonName){
        for(Button b: buttons){
            if(b.getName().equals(buttonName))
                return b;
        }
        return null;
    }
    public void draw(){
        for(Button b: buttons){
            b.draw();
        }
    }
}
