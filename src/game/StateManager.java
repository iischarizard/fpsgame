package game;

import game.UI.HudRenderer;
import game.UI.MainMenu;
import game.UI.Sprite;
import game.entity.Player;
import org.lwjgl.input.Mouse;

/**
 * Created by Matthew Hong on 5/8/2018.
 */
public class StateManager {
    public static enum GameState{
        MAINMENU,GAME
    }
    public static GameState gameState = GameState.MAINMENU;
    public static MainMenu mainMenu;
    public static String game = "";

    public static void update(){
        switch (gameState){
            case MAINMENU:
                if(mainMenu == null){
                    mainMenu = new MainMenu();
                }
                mainMenu.update();
                break;
            case GAME:
                if(game == "") {
                    Player.setOutOfMenu();
                    Sprite test = new Sprite("res/crosshair.png");
                    test.setWidth(0.07f);
                    test.setHeight(0.1f);
                    test.setX(0-test.getWidth()/2);
                    test.setY(0+test.getHeight()/2);
                    HudRenderer.addSprite(test);
                    game = "done";
                    Mouse.setGrabbed(true);
                }
                break;
        }
    }
    public static void setGameState(GameState newState){
        gameState = newState;
    }
}
