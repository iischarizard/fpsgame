package game.model;

import org.lwjgl.util.vector.Vector3f;

import game.texture.ModelTexture;

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

	public void setPosition(Vector3f newPos){this.position = newPos;}

	public void setHit(boolean x){
		isHit = x;
	}

	public void setRawModel(RawModel rawModel) {
		this.rawModel = rawModel;
	}

	public boolean getHit(){
		return isHit;
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

	@Override
	public boolean equals(Object a){
		TexturedModel b = (TexturedModel)a;
		if(getRawModel().equals(b.getRawModel())&&getPosition().equals(b.getPosition())&&getHit() == b.getHit()&&getTexture().equals(b.getTexture()))
			return true;
		return false;
	}
}
