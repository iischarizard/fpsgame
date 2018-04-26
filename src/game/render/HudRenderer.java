package game.render;

import org.lwjgl.opengl.GL11;


public class HudRenderer {
	
	private Sprite test;
	
	public HudRenderer(){
		test = new Sprite("res/crosshair.png");
		test.setWidth(0.07f);
		test.setHeight(0.1f);
	}
	public void render(){
		test.draw(0-test.getWidth()/2, 0+test.getHeight()/2);
	}

}
