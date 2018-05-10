package game;

import java.util.ArrayList;

import game.data.FolderReader;
import game.entity.Enemy;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.openal.SoundStore;

import game.data.Loader;
import game.data.Radio;
import game.entity.Player;
import game.map.Map;
import game.model.TexturedModel;
import game.render.RenderMaster;

import static org.lwjgl.opengl.GL11.*;


public class Game implements GameBase {
	private boolean running = false;
	private boolean radioStarted = false;
	private RenderMaster renderMaster;
	private Loader loader;
	private Map map;
	private Player player;
	private Radio radio = new Radio();
	private FolderReader fileReader = new FolderReader();

	private ArrayList<TexturedModel> mapObjs;
	private ArrayList<Enemy> enemies = new ArrayList<>();


	public void init() {
		renderMaster = new RenderMaster();
		loader = new Loader();
		map = loader.loadMap("map01");
		player = new Player(map);
		String[] s = fileReader.getFileNames("res/songs/");
		radio.loadSongs(s,"res/songs/");

		mapObjs = map.getTexturedModelsArray();
		enemies.add(new Enemy(new Vector3f(0, 40, 0),loader,map));

	}

	public void startGameLogic(){


	}


	public void initGL() {
		GL11.glClearColor(0.529f, 0.808f, 0.980f, 1.0f);
		glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		glEnable(GL_BLEND);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public void update(float dt) {
		player.update(dt);


		for(Enemy e: enemies){
			e.update(dt,player.getPosition());
		}

		while(Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
					//System.out.println(1);
					radio.playFile(-1);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
					radio.playFile(1);
					radioStarted = true;
				}
			}
		}
		SoundStore.get().poll(0);

		ArrayList<TexturedModel> temp = new ArrayList<TexturedModel>();
		for (TexturedModel model : mapObjs){
			if(model.getHit()){
				temp.add(model);
			}
		}
		for(TexturedModel model: temp){
			mapObjs.remove(model);
		}
		if(!radio.isSongPlaying() && radioStarted)
			radio.playFile(1);
	}
	public boolean isRunning() {
		return running;
	}
	public void start() {
		if (running) return;
		running = true;
	}

	public void render() {
		for (TexturedModel model : mapObjs){
			renderMaster.processBlock(model);
		}
		for(Enemy e : enemies){
			renderMaster.processEntity(e.getModel());
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
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Main.WIDTH, 0, Main.HEIGHT, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
	}

	public static void make3D() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
