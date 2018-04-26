package game;

import game.model.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import game.data.Loader;
import game.entity.Player;
import game.map.Map;
import game.render.RenderMaster;


public class Game implements GameBase {
	private boolean running = false;
	private RenderMaster renderMaster;
	private Loader loader;
	private Map map;
	private Player player;

	public void init() {
		renderMaster = new RenderMaster();
		loader = new Loader();
		map = loader.loadMap("map02");
		player = new Player(map);
	}

	public void initGL() {
		GL11.glClearColor(0.529f, 0.808f, 0.980f, 1.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);    
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void update(float dt) {
		player.update(dt);
	}
	public boolean isRunning() {
		return running;
	}
	public void start() {
		if (running) return;
		running = true;
	}

	public void render() {
		for (TexturedModel model : map.getTexturedModelsArray()){
			renderMaster.processBlock(model);
		}
		renderMaster.render(player);
	}
	public void stop(){
		if (!running) return;
		running = false;
	}
	public void cleanUp() {
		loader.cleanUp();
		map.cleanUp();
		renderMaster.cleanUp();
		Display.destroy();
	}
	public static void make2D() {
		//Remove the Z axis
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 0, 1);
		GL11.glLoadIdentity();

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
	}

	public static void make3D() {
		//Restore the Z axis
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
