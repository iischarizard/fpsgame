package game.model;

import game.texture.ModelTexture;
import org.joml.Vector3f;

public class TexturedModel extends Bounds{

	private RawModel rawModel;
	private ModelTexture texture;
	private boolean isHit = false;
	private Vector3f position;

	public TexturedModel(RawModel model, ModelTexture texture) {
		super();
		this.rawModel = model;
		this.texture = texture;
	}
	public TexturedModel() {
		super();
		position = new Vector3f(getMaxX()-getMinX(),getMaxY()-getMinY(),getMaxZ()-getMinZ());
	}
	public Vector3f getPosition(){
		return position;
	}
	public void setHit(boolean x){
		isHit = x;
	}
	public void setRawModel(RawModel rawModel) {
		this.rawModel = rawModel;
	}

	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}

}
