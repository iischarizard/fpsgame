package game;

public interface GameBase {

	void init();

	void initGL();

	void update(float dt);

	void render();

	void cleanUp();

	boolean isRunning();

	void start();

	void stop();


}
