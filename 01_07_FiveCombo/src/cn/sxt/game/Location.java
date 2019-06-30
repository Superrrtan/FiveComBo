package cn.sxt.game;

/*
 * 位置：1>坐标 x , y
 *     2>chessman 落子归属者
 *     3>score 位置评估分数
*/

public class Location {
	//坐标 x,y
	private int x;
	private int y;
	//落子归属者
	private int chessman;
	//位置评估分数
	private int score;
	//构造方法
	//参数:
	// int x; int y;
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Location(int x, int y, int chessman) {
		//super();
		this.x = x;
		this.y = y;
		this.chessman = chessman;
	}
	//获取x坐标 return (int)
	public int getX() {
		return x;
	}
	//获取y坐标 return (int)
	public int getY() {
		return y;
	}
	//获取评估分数 return (int)
	public int getScore() {
		return score;
	}
	//获取落子者 return (int)
	public int getChessman() {
		return chessman;
	}
	//设定评估分数
	public void setScore(int score) {
		this.score = score;
	}
	//设定落子所有者
	public void setChessman(int chessman) {
		this.chessman = chessman;
	}
	

}
