package game.map;

import java.util.ArrayList;
import java.util.List;


public class Map {

	private List<Block> blocks;


	public Map() {
		blocks = new ArrayList<Block>();

	}

	public void cleanUp() {

	}

	public List<Block> getBlockArray()
	{
		return blocks;
	}

}
