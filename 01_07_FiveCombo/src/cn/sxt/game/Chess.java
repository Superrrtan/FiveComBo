package cn.sxt.game;

import java.util.ArrayList;

/*
 * AI�㷨��
 * 
*/
public class Chess {
	//����λ�ñ�ʶ�� 0~���ӣ�1~AI���� �� 2~HUMAN���ӣ�
	private int chess[][];
	//AI��ʶ
	public static final int AI = 1;
	//��ұ�ʶ
	public static final int HUMAN = 2;
	
	public Chess() {//��������
		chess = new int[15][15];
		restart();
	}
	public void restart() {
		for (int i = 0; i < chess.length; i++)
			for (int j = 0; j < chess.length; j++)
				chess[i][j] = 0;
	}
	public Location LocationAI() {
		//Ѱ�ҿ���λ�õ㼯��
		ArrayList<Location> ableLocationList = new ArrayList<Location>();
		Location maxLocation = null ;
		int max = 0;
		ableLocationListExplore(ableLocationList);
		//ģ����֣�Ѱ����ѵ�
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
	public void ableLocationListExplore (ArrayList<Location> list){//Ѱ�ҿ��е�
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
	public int getScore(int x , int y) {//��ȡ����
		int score = getXScore(x,y,AI)+getYScore(x,y,AI)+getSlant_1Score(x,y,AI)+getSlant_2Score(x,y,AI)+getXScore(x,y,HUMAN)+getYScore(x,y,HUMAN)+getSlant_1Score(x,y,HUMAN)+getSlant_2Score(x,y,HUMAN);
		System.out.println("(x,y) "+x+" "+y+ " "  +score);
		return score;
	}
	public int getXScore(int x, int y, int chessman) {
		int other;// �Է�����

		if (chessman == AI)
			other = HUMAN;
		else
			other = AI;

		// ģ������
		chess[x][y] = chessman;

		// ��ࡢ�Ҳ��Ƿ񱻶�
		int leftStatus = 0;
		int rightStatus = 0;
		int count1 = 0;// count1���������Ӹ���

		// ɨ���¼����
		for (int j = y; j < 15; j++) {
			if (chess[x][j] == chessman) {
				count1++;
				if (j == 14)
					rightStatus = 2;// �Ҳ౻ǽ��ס
			}

			else {
				if (chess[x][j] == 0)
					rightStatus = 1;// �Ҳ�Ϊ��
				if (chess[x][j] == other)
					rightStatus = 2;// �Ҳ౻�Է���ס
				break;
			}
		}
		for (int j = y - 1; j >= 0; j--) {
			if (chess[x][j] == chessman) {
				count1++;
				if (j == 0)
					leftStatus = 2; // ��౻ǽ��ס
			}

			else {
				if (chess[x][j] == 0)
					leftStatus = 1;// ���Ϊ��
				if (chess[x][j] == other)
					leftStatus = 2;// ��౻�Է���ס
				break;
			}
		}

		// �ָ�
		chess[x][y] = 0;

		// �������ͼ����λ����
		return getScoreRule(count1, leftStatus, rightStatus);
	}
	public int getYScore(int x, int y, int chessman) {
		int other;// �Է�����

		if (chessman == AI)
			other = HUMAN;
		else
			other = AI;

		// ģ������
		chess[x][y] = chessman;

		// ��ࡢ�Ҳ��״̬��������¼����
		int topStatus = 0;
		int bottomStatus = 0;
		int i;
		int count1=0;

		// ɨ���¼����
		
		for (i = x; i < chess.length; i++) {
			if (chess[i][y] == chessman) {
				count1++;
				if (i==chess.length-1) bottomStatus= 2;// �� �౻ǽ��ס
			}
				
			else {
				if (chess[i][y] == 0)
					bottomStatus = 1;// �²�Ϊ��
				if (chess[i][y] == other)
					bottomStatus = 2;// �²౻�Է���ס
				break;
			}
		}
		for (i = x - 1; i >= 0; i--) {
			if (chess[i][y] == chessman) {
				count1++;
				if (i==0) topStatus = 2;//�ϲ౻ǽ��ס
			}
				
			else {
				if (chess[i][y] == 0)
					topStatus = 1;// �ϲ�Ϊ��
				if (chess[i][y] == other)
					topStatus = 2;// �ϲ౻�Է���ס
				break;
			}
		}
		// �ָ�
		chess[x][y] = 0;

		return getScoreRule(count1, topStatus, bottomStatus);
	}
	public int getSlant_1Score(int x, int y, int chessman) {
		int other;// �Է�����

		if (chessman == 1)
			other = 2;
		else
			other = 1;

		// ģ������
		chess[x][y] = chessman;

		// ����
		//int score = 0;

		int topStatus = 0;
		int bottomStatus = 0;
		int i, j, count1 = 0;

		for (i = x, j = y; i < chess.length && j < chess.length; i++, j++) {
			if (chess[i][j] == chessman) {
				count1++;                     
				if (i==14||j==14) bottomStatus = 2;//�²౻ǽ��ס
			}
				
			else {
				if (chess[i][j] == 0)
					bottomStatus = 1;// �²�Ϊ��
				if (chess[i][j] == other)
					bottomStatus = 2;// �²౻�Է���ס
				break;
			}
		}

		for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if (chess[i][j] == chessman) {
				count1++;
			if(i==0||j==0) topStatus = 2;// �ϲ౻ǽ��ס
			}
				
			else {
				if (chess[i][j] == 0)
					topStatus = 1;// �ϲ�Ϊ��
				if (chess[i][j] == other)
					topStatus = 2;// �ϲ౻�Է���ס
				break;
			}
		}
		// �ָ�
		chess[x][y] = 0;

		return getScoreRule(count1, topStatus, bottomStatus);
	}
	
	public int getSlant_2Score(int x, int y, int chessman) {
		int other;// �Է�����
		if (chessman == AI)
			other = HUMAN;
		else
			other = AI;

		// ģ������
		chess[x][y] = chessman;

		// ����

		int topStatus = 0;
		int bottomStatus = 0;
		int i, j;
		// �����ϵ�����
		int count1 = 0;
		for (i = x, j = y; i < chess.length && j >= 0; i++, j--) {
			if (chess[i][j] == chessman) {
				count1++;
			if(i==14||j==0) bottomStatus = 2;// �²౻ǽ��ס
			}
				
			else {
				if (chess[i][j] == 0)
					bottomStatus = 1;// �²�Ϊ��
				if (chess[i][j] == other)
					bottomStatus = 2;// �²౻�Է���ס
				break;
			}
		}

		for (i = x - 1, j = y + 1; i >= 0 && j < chess.length; i--, j++) {
			if (chess[i][j] == chessman) {
				count1++;
			if (i==0||j==14) topStatus = 2;// �ϲ౻ǽ��ס
			}
				
			else {
				if (chess[i][j] == 0)
					topStatus = 1;// �ϲ�Ϊ��
				if (chess[i][j] == other)
					topStatus = 2;// �ϲ౻�Է���ס
				break;
			}
		}

		// �ָ�
		chess[x][y] = 0;

		return getScoreRule(count1, topStatus, bottomStatus);
	}
	
	private int getScoreRule(int count1, int leftStatus, int rightStatus) {// ���ֱ�׼
		int score = 0;

		// �������
		if (count1 >= 5)
			score += 200000;// Ӯ��

		// �������
		if (count1 == 4) {
			if (leftStatus == 1 && rightStatus == 1)
				score += 80000;//���ģ��൱��Ӯ��
			if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
				score += 3000;//�������ģ�Ҳ�൱��Ӯ 
			if (leftStatus == 2 && rightStatus == 2)
				score += 1000;
		}

		// �������
		if (count1 == 3) {
			if (leftStatus == 1 && rightStatus == 1)
				score += 3000;
			if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
				score += 1000;
			if (leftStatus == 2 && rightStatus == 2)
				score += 500;
		}

		// �������
		if (count1 == 2) {
			if (leftStatus == 1 && rightStatus == 1)
				score += 500;
			if ((leftStatus == 2 && rightStatus == 1) || (leftStatus == 1 && rightStatus == 2))
				score += 200;
			if (leftStatus == 2 && rightStatus == 2)
				score += 100;
		}

		// һ�����
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
	public boolean isWin(int x, int y, int cur) {// �ж���Ӯ

		// �ĸ������ϵ�������
		int count1 = 0, count2 = 0, count3 = 0, count4 = 0;
		int i, j;

		// ����ɨ��
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

		// ����ɨ��
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

		// ��б��ɨ��
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

		// ��б��ɨ��
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
	
	public boolean play(int x, int y, int chessman) {// У�����Ӵ��Ƿ�Ϸ�
		// �߽��ж�
		if (x < 0 || x >= chess.length)
			return false;
		if (y < 0 || y >= chess.length)
			return false;
		// �ǿ��жϣ�0Ϊ��λ
		if (chess[x][y] != 0)
			return false;
		// У��ͨ��������
		chess[x][y] = chessman;//���̹�����ֵ
		return true;
	}


}
