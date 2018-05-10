package game.map;

import game.model.TexturedModel;

import java.util.ArrayList;
import java.util.List;


public class Map {

	private ArrayList<TexturedModel> texturedModels;


	public Map() {
		texturedModels = new ArrayList<TexturedModel>();
	}

	public void cleanUp() {

	}
	public void removeModel(TexturedModel t){
		texturedModels.remove(t);
	}
	public ArrayList<TexturedModel> getTexturedModelsArray()
	{
		return texturedModels;
	}

}