package pathing;

public class MeshPolygon {

	
	//2d
	/*         2
	 *
	 *    x1y1---x2y2
	 * 1   |      |   1
	 *     |      |
	 *    x3y3---x4y4
	 *    
	 *         2
	 */
	
	//3d
	/*
	 *      x1y1z1-------x2y2z2
	 *        /|          /|
	 *       / |         / |
	 *      /  |        /  |
	 *   x3y3z3------x4y4z4|
	 *     |   |       |   |
	 *     |   5-------|---6
	 *     |  /        |  /
	 *     | /         | /
	 *     |/          |/  
	 *     7-----------8   
	 * 
	 */
	
	/*
	private float x1, x2, x3, x4, y1, y2, y3, y4;
	private int deadPair;
	
	public MeshPolygon(float x1_,  float y1_, float x2_, float y2_, float x3_, float y3_, float x4_, float y4_, int deadPair_){
		x1 = x1_;
		x2 = x2_;
		x3 = x3_;
		x4 = x4_;
		y1 = y1_;
		y2 = y2_;
		y3 = y3_;
		y4 = y4_;
		deadPair = deadPair_;
	}
	
	public float[] getTopLeft2D(){return new float[]{x1, y1};}
	public float[] getTopRight2D(){return new float[]{x2, y2};}
	public float[] getBottomLeft2D(){return new float[]{x3, y3};}
	public float[] getBottomRight2D(){return new float[]{x4, y4};}
	public int getDeadPair(){return deadPair;}
	
	@Override
	public boolean equals(Object b){
		MeshPolygon a = (MeshPolygon)b;
		if(getTopLeft2D().equals(a.getTopLeft2D())&&getTopRight2D().equals(a.getTopRight2D())&&getBottomLeft2D().equals(a.getBottomLeft2D())&&getBottomRight2D().equals(a.getBottomRight2D()))
			return true;
		return false;
	}
	*/
}
