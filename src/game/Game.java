package game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import game.data.Loader;
import game.entity.Player;
import game.map.Block;
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
		map = loader.loadMap("map01");
		player = new Player(map);
	}

	public void initGL() {
		GL11.glClearColor(0.529f, 0.808f, 0.980f, 1.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
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
		for (Block block : map.getBlockArray())
			renderMaster.processBlock(block);
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

}
