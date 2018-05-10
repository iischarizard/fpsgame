package game.UI;

import game.Game;
import game.tool.Font;

import java.util.ArrayList;

public class HudRenderer {

	private static ArrayList<Sprite> sprites = new ArrayList<>();
	private Font font;
	public static String currentSong = "Now Playing: ";
	public HudRenderer(){
		try {
			font = new Font("res/fonts/BebasNeue.ttf",24);
		}catch (Exception e){

		}
	}
	public void render(){
		for(Sprite s : sprites){
			s.draw();
		}
		Game.make2D();
		font.drawText(currentSong,0,720);
		Game.make3D();
	}
	//I WANT TO DRAW THE CROSSHAIR LATER
	public static void addSprite(Sprite s){
		sprites.add(s);
	}
	public static void updateCurrentSong(String s){
		currentSong = "Now Playing: "+s;
	}
}
