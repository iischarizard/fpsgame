package game.map;

import game.model.TexturedModel;

import java.util.ArrayList;
import java.util.List;


public class Map {

	private List<TexturedModel> texturedModels;


	public Map() {
		texturedModels = new ArrayList<TexturedModel>();

	}

	public void cleanUp() {

	}

	public List<TexturedModel> getTexturedModelsArray()
	{
		return texturedModels;
	}

}
