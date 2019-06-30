package cn.sxt.game;

/*
 * λ�ã�1>���� x , y
 *     2>chessman ���ӹ�����
 *     3>score λ����������
*/

public class Location {
	//���� x,y
	private int x;
	private int y;
	//���ӹ�����
	private int chessman;
	//λ����������
	private int score;
	//���췽��
	//����:
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
	//��ȡx���� return (int)
	public int getX() {
		return x;
	}
	//��ȡy���� return (int)
	public int getY() {
		return y;
	}
	//��ȡ�������� return (int)
	public int getScore() {
		return score;
	}
	//��ȡ������ return (int)
	public int getChessman() {
		return chessman;
	}
	//�趨��������
	public void setScore(int score) {
		this.score = score;
	}
	//�趨����������
	public void setChessman(int chessman) {
		this.chessman = chessman;
	}
	

}
