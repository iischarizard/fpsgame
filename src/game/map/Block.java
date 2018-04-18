package game.map;

import org.lwjgl.util.vector.Vector3f;

import game.model.TexturedModel;

public class Block {

	private float minX, minY, minZ;
	private float maxX, maxY, maxZ;
	private TexturedModel model;

	public Block() {
		minX = Float.POSITIVE_INFINITY;
		minY = Float.POSITIVE_INFINITY;
		minZ = Float.POSITIVE_INFINITY;
		maxX = Float.NEGATIVE_INFINITY;
		maxY = Float.NEGATIVE_INFINITY;
		maxZ = Float.NEGATIVE_INFINITY;
	}

	public void calculateBounds(Vector3f v) {
		if (v.getX() < minX) minX = v.getX();
		if (v.getY() < minY) minY = v.getY();
		if (v.getZ() < minZ) minZ = v.getZ();
		if (v.getX() > maxX) maxX = v.getX();
		if (v.getY() > maxY) maxY = v.getY();
		if (v.getZ() > maxZ) maxZ = v.getZ();
	}

	public float getMinX() {
		return minX;
	}

	public float getMinY() {
		return minY;
	}

	public float getMinZ() {
		return minZ;
	}

	public float getMaxX() {
		return maxX;
	}

	public float getMaxY() {
		return maxY;
	}

	public float getMaxZ() {
		return maxZ;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

}
