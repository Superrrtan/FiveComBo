package cn.sxt.game;

import java.util.ArrayList;

/*
 * AI算法类
 * 
*/
public class Chess {
	//棋盘位置标识： 0~无子；1~AI落子 ； 2~HUMAN落子；
	private int chess[][];
	//AI标识
	public static final int AI = 1;
	//玩家标识
	public static final int HUMAN = 2;
	
	public Chess() {//棋盘清零
		chess = new int[15][15];
		restart();
	}
	public void restart() {
		for (int i = 0; i < chess.length; i++)
			for (int j = 0; j < chess.length; j++)
				chess[i][j] = 0;
	}
	public Location LocationAI() {
		//寻找可行位置点集合
		ArrayList<Location> ableLocationList = new ArrayList<Location>();
		Location maxLocation = null ;
		int max = 0;
		ableLocationListExplore(ableLocationList);
		//模拟积分，寻找最佳点
		for (Location location : ableLocationList) {
			int score = getScore(location.getX(),location.getY());
			location.setScore(score);
			if (score>=max) {
				max = score;
				maxLocation = location;
			}
		}
		maxLocation.setChessman(AI);
		System.out.println("maxLocation:  "+maxLocation.getX()+"  "+maxLocation.getY());
		return maxLocation;
	}
	public void ableLocationListExplore (ArrayList<Location> list){//寻找可行点
		for (int i=0;i<chess.length;i++) {
			for (int j = 0; j<chess[0].length;j++) {
				if (chess[i][j]!=0) {
					if (j!=14&&chess[i][j+1]==0) list.add(new Location(i,j+1));
					if (j!=0&&chess[i][j-1]==0) list.add(new Location(i,j-1));
					if (i!=14&&chess[i+1][j]==0) list.add(new Location(i+1,j));
					if (i!=0&&chess[i-1][j]==0) list.add(new Location(i-1,j));
					if (i!=0 && j!=0 && chess[i-1][j-1]==0) list.add(new Location(i-1,j-1));
					if (i!=14 && j!=14 && chess[i+1][j+1]==0) list.add(new Location(i+1,j+1));
					if (i!=0&&j!=14 && chess[i-1][j+1]==0) list.add(new Location(i-1,j+1));
					if (i!=14 && j!=0 && chess[i+1][j-1]==0) list.add(new Location(i+1,j-1));
				}
			}
		}
		
	}
	public int getScore(int x , int y) {//获取分数
		int score = getXScore(x,y,AI)+getYScore(x,y,AI)+getSlant_1Score(x,y,AI)+getSlant_2Score(x,y,AI)+getXScore(x,y,HUMAN)+getYScore(x,y,HUMAN)+getSlant_1Score(x,y,HUMAN)+getSlant_2Score(x,y,HUMAN);
		System.out.println("(x,y) "+x+" "+y+ " "  +score);
		return score;
	}
	public int getXScore(int x, int y, int chessman) {
		int other;// 对方棋子

		if (chessman == AI)
			other = HUMAN;
		else
			other = AI;

		// 模拟落子
		chess[x][y] = chessman;

		// 左侧、右侧是否被堵
		int leftStatus = 0;
		int rightStatus = 0;
		int count1 = 0;// count1是相连棋子个数

		// 扫描记录棋型
		for (int j = y; j < 15; j++) {
			if (chess[x][j] == chessman) {
				count1++;
				if (j == 14)
					rightStatus = 2;// 右侧被墙堵住
			}

			else {
				if (chess[x][j] == 0)
					rightStatus = 1;// 右侧为空
				if (chess[x][j] == other)
					rightStatus = 2;// 右侧被对方堵住
				break;
			}
		}
		for (int j = y - 1; j >= 0; j--) {
			if (chess[x][j] == chessman) {
				count1++;
				if (j == 0)
					leftStatus = 2; // 左侧被墙堵住
			}

			else {
				if (chess[x][j] == 0)
					leftStatus = 1;// 左侧为空
				if (chess[x][j] == other)
					leftStatus = 2;// 左侧被对方堵住
				break;
			}
		}

		// 恢复
		chess[x][y] = 0;

		// 根据棋型计算空位分数
		return getScoreRule(count1, leftStatus, rightStatus);
	}
	public int getYScore(int x, int y, int chessman) {
		int other;// 对方棋子

		if (chessman == AI)
			other = HUMAN;
		else
			other = AI;

		// 模拟落子
		chess[x][y] = chessman;

		// 左侧、右侧的状态，用来记录棋型
		int topStatus = 0;
		int bottomStatus = 0;
		int i;
		int count1=0;

		// 扫描记录棋型
		
		for (i = x; i < chess.length; i++) {
			if (chess[i][y] == chessman) {
				count1++;
				if (i==chess.length-1) bottomStatus= 2;// 下 侧被墙堵住
			}
				
			else {
				if (chess[i][y] == 0)
					bottomStatus = 1;// 下侧为空
				if (chess[i][y] == other)
					bottomStatus = 2;// 下侧被对方堵住
				break;
			}
		}
		for (i = x - 1; i >= 0; i--) {
			if (chess[i][y] == chessman) {
				count1++;
				if (i==0) topStatus = 2;//上侧被墙堵住
			}
				
			else {
				if (chess[i][y] == 0)
					topStatus = 1;// 上侧为空
				if (chess[i][y] == other)
					topStatus = 2;// 上侧被对方堵住
				break;
			}
		}
		// 恢复
		chess[x][y] = 0;

		return getScoreRule(count1, topStatus, bottomStatus);
	}
	public int getSlant_1Score(int x, int y, int chessman) {
		int other;// 对方棋子

		if (chessman == 1)
			other = 2;
		else
			other = 1;

		// 模拟落子
		chess[x][y] = chessman;

		// 分数
		//int score = 0;

		int topStatus = 0;
		int bottomStatus = 0;
		int i, j, count1 = 0;

		for (i = x, j = y; i < chess.length && j < chess.length; i++, j++) {
			if (chess[i][j] == chessman) {
				count1++;                     
				if (i==14||j==14) bottomStatus = 2;//下侧被墙堵住
			}
				
			else {
				if (chess[i][j] == 0)
					bottomStatus = 1;// 下侧为空
				if (chess[i][j] == other)
					bottomStatus = 2;// 下侧被对方堵住
				break;
			}
		}

		for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if (chess[i][j] == chessman) {
				count1++;
			if(i==0||j==0) topStatus = 2;// 上侧被墙堵住
			}
				
			else {
				if (chess[i][j] == 0)
					topStatus = 1;// 上侧为空
				if (chess[i][j] == other)
					topStatus = 2;// 上侧被对方堵住
				break;
			}
		}
		// 恢复
		chess[x][y] = 0;

		return getScoreRule(count1, topStatus, bottomStatus);
	}
	
	public int getSlant_2Score(int x, int y, int chessman) {
		int other;// 对方棋子
		if (chessman == AI)
			other = HUMAN;
		else
			other = AI;

		// 模拟落子
		chess[x][y] = chessman;

		// 分数

		int topStatus = 0;
		int bottomStatus = 0;
		int i, j;
		// 从右上到左下
		int count1 = 0;
		for (i = x, j = y; i < chess.length && j >= 0; i++, j--) {
			if (chess[i][j] == chessman) {
				count1++;
			if(i==14||j==0) bottomStatus = 2;// 下侧被墙堵住
			}
				
			else {
				if (chess[i][j] == 0)
					bottomStatus = 1;// 下侧为空
				if (chess[i][j] == other)
					bottomStatus = 2;// 下侧被对方堵住
				break;
			}
		}

		for (i = x - 1, j = y + 1; i >= 0 && j < chess.length; i--, j++) {
			if (chess[i][j] == chessman) {
				count1++;
			if (i==0||j==14) topStatus = 2;// 上侧被墙堵住
			}
				
			else {
				if (chess[i][j] == 0)
					topStatus = 1;// 上侧为空
				if (chess[i][j] == other)
					topStatus = 2;// 上侧被对方堵住
				break;
			}
		}

		// 恢复
		chess[x][y] = 0;

		return getScoreRule(count1, topStatus, bottomStatus);
	}
	
	private int getScoreRule(int count1, int leftStatus, int rightStatus) {// 评分标准
		int score = 0;

		// 五子情况
		if (count1 >= 5)
			score += 200000;// 赢了

		// 四子情况
		if (count1 == 4) {
			if (leftStatus == 1 && rightStatus == 1)
				score += 80000;//活四，相当于赢了
			if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
				score += 3000;//两个冲四，也相当于赢 
			if (leftStatus == 2 && rightStatus == 2)
				score += 1000;
		}

		// 三子情况
		if (count1 == 3) {
			if (leftStatus == 1 && rightStatus == 1)
				score += 3000;
			if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
				score += 1000;
			if (leftStatus == 2 && rightStatus == 2)
				score += 500;
		}

		// 二子情况
		if (count1 == 2) {
			if (leftStatus == 1 && rightStatus == 1)
				score += 500;
			if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
				score += 200;
			if (leftStatus == 2 && rightStatus == 2)
				score += 100;
		}

		// 一子情况
		if (count1 == 1) {
			if (leftStatus == 1 && rightStatus == 1)
				score += 100;
			if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
				score += 50;
			if (leftStatus == 2 && rightStatus == 2)
				score += 30;
		}

		return score;
	}
	public boolean isWin(int x, int y, int cur) {// 判断输赢

		// 四个方向上的连子数
		int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
		int i, j;

		// 横向扫描
		for (j = y; j < chess.length; j++) {
			if (chess[x][j] == cur)
				count1++;
			else
				break;
		}
		for (j = y - 1; j >= 0; j--) {
			if (chess[x][j] == cur)
				count1++;
			else
				break;
		}
		if (count1 >= 5)
			return true;

		// 纵向扫描
		for (i = x; i < chess.length; i++) {
			if (chess[i][y] == cur)
				count2++;
			else
				break;
		}
		for (i = x - 1; i >= 0; i--) {
			if (chess[i][y] == cur)
				count2++;
			else
				break;
		}
		if (count2 >= 5)
			return true;

		// 正斜向扫描
		for (i = x, j = y; i < chess.length && j < chess.length; i++, j++) {
			if (chess[i][j] == cur)
				count3++;
			else
				break;
		}
		for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if (chess[i][j] == cur)
				count3++;
			else
				break;
		}
		if (count3 >= 5)
			return true;

		// 反斜向扫描
		for (i = x, j = y; i < chess.length && j >= 0; i++, j--) {
			if (chess[i][j] == cur)
				count4++;
			else
				break;
		}
		for (i = x - 1, j = y + 1; i >= 0 && j < chess.length; i--, j++) {
			if (chess[i][j] == cur)
				count4++;
			else
				break;
		}
		if (count4 >= 5)
			return true;

		return false;
	}
	
	public boolean play(int x, int y, int chessman) {// 校验落子处是否合法
		// 边界判断
		if (x < 0 || x >= chess.length)
			return false;
		if (y < 0 || y >= chess.length)
			return false;
		// 非空判断，0为空位
		if (chess[x][y] != 0)
			return false;
		// 校验通过，落子
		chess[x][y] = chessman;//棋盘归属赋值
		return true;
	}


}
