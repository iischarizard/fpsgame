package game.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import game.model.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

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

	public void render(Matrix4f viewMatrix, Map<Integer, List<TexturedModel>> textModels) {
		shader.start();
		shader.loadViewMatrix(viewMatrix);
		ArrayList<TexturedModel> remove =  new ArrayList <>();
		for (int textureID : textModels.keySet()) {
			bindTexture(textureID);
			List<TexturedModel> batch = textModels.get(textureID);
			for (TexturedModel text : batch) {
				if(!text.getHit()) {
					RawModel model = text.getRawModel();
					GL30.glBindVertexArray(model.getVaoID());
					GL20.glEnableVertexAttribArray(0);
					GL20.glEnableVertexAttribArray(1);
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
					GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
					GL20.glDisableVertexAttribArray(0);
					GL20.glDisableVertexAttribArray(1);
					GL30.glBindVertexArray(0);
				}
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
