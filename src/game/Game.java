package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import game.data.Loader;
import game.data.Radio;
import game.entity.Player;
import game.map.Map;
import game.model.TexturedModel;
import game.render.RenderMaster;


public class Game implements GameBase {
	private boolean running = false;
	private RenderMaster renderMaster;
	private Loader loader;
	private Map map;
	private Player player;
	private Radio radio = new Radio();

	public void init() {
		renderMaster = new RenderMaster();
		loader = new Loader();
		map = loader.loadMap("map01");
		player = new Player(map);
		String[] s = {"res/songs/WhatIsLove.wav","res/songs/Ho.wav","res/songs/Stuck.wav","res/songs/Dejavu.wav","res/songs/SweetTalker.wav"};
		radio.loadSongs(s);
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
		while(Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
					//System.out.println(1);
					radio.playFile(-1);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
					radio.playFile(1);
				}
			}
		}
		SoundStore.get().poll(0);
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
