package game.shader;

import org.lwjgl.util.vector.Matrix4f;

public class BlockShader extends ShaderProgram {

	private static final String VERTEX_FILE = "res/shaders/blockVertex.glsl";
	private static final String FRAGMENT_FILE = "res/shaders/blockFragment.glsl";

	private int location_projectionMatrix;
	private int location_viewMatrix;

	public BlockShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}

	public void loadViewMatrix(Matrix4f matrix) {
		super.loadMatrix(location_viewMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

}
