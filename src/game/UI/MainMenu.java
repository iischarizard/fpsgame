package game.UI;

import game.Game;
import game.StateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;


/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class MainMenu extends Sprite{
    private Ui menuUI;

    public MainMenu(){
        super("res/hudStuff/background.png");
        this.setWidth(2f);
        this.setHeight(2f);
        this.setX(-1f);
        this.setY(1f);
        menuUI = new Ui();
        menuUI.addButton("Play","res/hudStuff/play.png",-.2f,0,.4f,.4f);
    }
    private void updateButtons(){
        if(Mouse.isButtonDown(0)){
            if(menuUI.isButtonClicked()) {
                StateManager.setGameState(StateManager.GameState.GAME);
            }
        }
    }
    public void update(){
        menuUI.draw();
        menuUI.drawString(0,0,"Hello World");
        this.draw();
        updateButtons();
    }
}
