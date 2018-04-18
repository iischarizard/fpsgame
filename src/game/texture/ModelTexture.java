package game.texture;

public class ModelTexture {

	private TextureMaterial material;
	private int id;

	public ModelTexture(TextureMaterial material, int id) {
		this.material = material;
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public TextureMaterial getMaterial() {
		return material;
	}

}
