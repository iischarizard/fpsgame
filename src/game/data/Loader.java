package game.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import game.Main;
import game.map.Block;
import game.map.Map;
import game.model.RawModel;
import game.model.TexturedModel;
import game.texture.ModelTexture;
import game.tool.TextureMaterialTools;

public class Loader {

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private HashMap<String, Integer> textures = new HashMap<String, Integer>();

	public RawModel loadRawModel(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		GL30.glBindVertexArray(vaoID);
		bindIndicesBuffer(indices);
		storeDataInVBO(0, 3, positions);
		storeDataInVBO(1, 2, textureCoords);
		storeDataInVBO(2, 3, normals);
		GL30.glBindVertexArray(0);
		return new RawModel(vaoID, indices.length);
	}

	public int loadTexture(String filename) {
		if (textures.containsKey(filename)) return textures.get(filename);
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/" + filename + ".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (texture == null) {
			System.err.println("Error loading texture '" + filename + "'.");
			Main.forceStop();
			return 0;
		}
		int textureID = texture.getTextureID();
		textures.put(filename, textureID);
		return textureID;
	}

	public void cleanUp() {
		for (int vao : vaos)
			GL30.glDeleteVertexArrays(vao);
		for (int vbo : vbos)
			GL15.glDeleteBuffers(vbo);
		for (int texture : textures.values())
			GL11.glDeleteTextures(texture);
	}

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		return vaoID;
	}

	private void storeDataInVBO(int index, int size, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(index);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		GL20.glDisableVertexAttribArray(index);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL15.GL_STATIC_DRAW);
	}

	public Map loadMap(String filename) {
		System.out.println("Loading map '" + filename + "'.");
		Map map = new Map();
		try {
			BufferedReader br = new BufferedReader(new FileReader("res/maps/" + filename + ".obj"));
			String line = "";

			int vertexOffset = 1;
			int textureOffset = 1;
			int normalOffset = 1;

			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) continue;
				if (line.startsWith("o")) {
					Block b = new Block();

					String textureName = "";
					int faceCount = 0;
					int vCount = 0;
					int vtCount = 0;
					int vnCount = 0;
					boolean firstFace = true;

					List<Vector3f> vertices = new ArrayList<Vector3f>();
					List<Vector2f> textures = new ArrayList<Vector2f>();
					List<Vector3f> normals = new ArrayList<Vector3f>();
					List<Integer> indices = new ArrayList<Integer>();

					float[] vertexArray = null;
					float[] textureArray = null;
					float[] normalArray = null;
					int[] indiceArray = null;

					while (true) {
						line = br.readLine();
						String[] t = line.split(" ");
						if (t[0].equals("v")) {
							Vector3f vertex = new Vector3f();
							vertex.setX(Float.parseFloat(t[1]));
							vertex.setY(Float.parseFloat(t[2]));
							vertex.setZ(Float.parseFloat(t[3]));
							vertices.add(vertex);
							vCount++;
						}
						if (t[0].equals("vt")) {
							Vector2f texture = new Vector2f();
							texture.setX(Float.parseFloat(t[1]));
							texture.setY(Float.parseFloat(t[2]));
							textures.add(texture);
							vtCount++;
						}
						if (t[0].equals("vn")) {
							Vector3f normal = new Vector3f();
							normal.setX(Float.parseFloat(t[1]));
							normal.setY(Float.parseFloat(t[2]));
							normal.setZ(Float.parseFloat(t[3]));
							normals.add(normal);
							vnCount++;
						}
						if (t[0].equals("usemtl")) {
							textureName = t[1];
						}
						if (t[0].equals("f")) {
							if (firstFace) {
								textureArray = new float[vertices.size() * 2];
								normalArray = new float[vertices.size() * 3];
								firstFace = false;
							}

							for (int i = 1; i < 4; i++) {
								String[] tx = t[i].split("/");

								int indice = Integer.parseInt(tx[0]) - vertexOffset;
								b.calculateBounds(vertices.get(indice));
								indices.add(indice);

								Vector2f texture = textures.get(Integer.parseInt(tx[1]) - textureOffset);
								textureArray[indice * 2    ] = texture.getX();
								textureArray[indice * 2 + 1] = texture.getY();

								Vector3f normal = normals.get(Integer.parseInt(tx[2]) - normalOffset);
								normalArray[indice * 3    ] = normal.getX();
								normalArray[indice * 3 + 1] = normal.getY();
								normalArray[indice * 3 + 2] = normal.getZ();
							}

							if (++faceCount >= 12) break;
						}
					}
					vertexArray = new float[vertices.size() * 3];
					indiceArray = new int[indices.size()];

					int vertexPointer = 0;
					for (Vector3f vertex : vertices) {
						vertexArray[vertexPointer++] = vertex.getX();
						vertexArray[vertexPointer++] = vertex.getY();
						vertexArray[vertexPointer++] = vertex.getZ();
					}

					for (int i = 0; i < indices.size(); i++) {
						indiceArray[i] = indices.get(i);
					}

					RawModel rawModel = loadRawModel(vertexArray, textureArray, normalArray, indiceArray);
					ModelTexture modelTexture = new ModelTexture(TextureMaterialTools.fetchMaterial(textureName), loadTexture(textureName));
					b.setModel(new TexturedModel(rawModel, modelTexture));
					map.getBlockArray().add(b);

					vertexOffset += vCount;
					textureOffset += vtCount;
					normalOffset += vnCount;
				}
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	private IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
