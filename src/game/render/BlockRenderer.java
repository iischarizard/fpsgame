package game.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import game.map.Block;
import game.model.RawModel;
import game.shader.BlockShader;

public class BlockRenderer {

	private BlockShader shader;

	public BlockRenderer(Matrix4f projectionMatrix) {
		shader = new BlockShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Matrix4f viewMatrix, Map<Integer, List<Block>> blocks) {
		shader.start();
		shader.loadViewMatrix(viewMatrix);
		for (int textureID : blocks.keySet()) {
			bindTexture(textureID);
			List<Block> batch = blocks.get(textureID);
			for (Block block : batch) {
				bindModel(block.getModel().getRawModel());
				GL11.glDrawElements(GL11.GL_TRIANGLES, block.getModel().getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
		}
		unbindModel();
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}

	private void bindTexture(int textureID) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	private void bindModel(RawModel rawModel) {
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}

	private void unbindModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

}
