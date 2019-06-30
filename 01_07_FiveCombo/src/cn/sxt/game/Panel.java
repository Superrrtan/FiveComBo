package cn.sxt.game;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;


public class Panel extends JPanel {
private static final long serialVersionUID = 1L;
	
	//����λ�ü���
	public ArrayList<Location> list = new ArrayList<Location>();
	
	
	//���̵�Ԫ��ĳ���
	public static final int row = 30;
	
	//����ÿ�е�һ���������ľ���
	public static final int margin=20;
	
	//ÿ��15�����ӵ�
	public static final int rowper=15;
	
	//���Ӱ뾶
	public static final int chessRadius=13;
	
	//���̱�����ɫ
	//Color bgColor=new Color(246,214,159);

	
	//�ǵ���ɫ
	
	
	@Override
	public void paint(Graphics g1) {
		super.paint(g1);
		Graphics2D g=(Graphics2D) g1;
		drawBoard(g);//������
		drawChessman(g);//������
	}
	
	/**
	 * ������
	 * @param g
	 */
	public void drawBoard(Graphics2D g){
		//���ñ�����ɫ�Լ�������
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//System.out.println(this.getWidth()+" "+this.getHeight());
		
		//��������
		g.setColor(Color.black);
		for (int i = 0; i < rowper; i++) {
			g.drawLine(margin, margin + row * i, this.getWidth() - margin, margin + row * i);
			g.drawLine(margin + row * i, margin, margin + row * i, this.getHeight() - margin);
		}
		
		//������ɫ�Լ��������ϵĵ�
		//10��Բ�İ뾶
		g.setColor(Color.cyan);
		g.fillOval(margin-5 + 3 * row, margin-5 + 3 * row, 10, 10);
		g.fillOval(margin-5 + 7 * row, margin-5 + 7 * row, 10, 10);
		g.fillOval(margin-5 + 3 * row, margin-5 + 11 * row, 10, 10);
		g.fillOval(margin-5 + 11 * row, margin-5 + 3 * row, 10, 10);
		g.fillOval(margin-5 + 11 * row, margin-5 + 11 * row, 10, 10);
	}
	
	/**
	 * ������
	 * @param g
	 */
	public void drawChessman(Graphics2D g){
		int i=1;//��־��ǰ���е�����
		
		//�õ�FontMetrics����
		//��ҪΪ�������������
		FontMetrics metrics=g.getFontMetrics();
		int ascent = metrics.getAscent();
		int descent = metrics.getDescent();
		
		//������ֻ�������
		for (Location location : list) {
			if (location.getChessman() == Chess.HUMAN)
				g.setColor(Color.black);//��������Ϊ��ɫ
			else
				g.setColor(Color.white);//���ú���Ϊ��ɫ
			
			//������
			g.fillOval(margin-13 + location.getY() * row, margin-chessRadius + location.getX() * row, chessRadius*2, chessRadius*2);
			
			//�������ϵ�����
			if(location.getChessman()==Chess.HUMAN) g.setColor(Color.white);
			else g.setColor(Color.black);
			String string=i+"";
			//�����ַ���Ӧ�ڵ�����
			g.drawString(string,margin + location.getY() * row-metrics.stringWidth(string)/2,margin + location.getX() * row-(ascent+descent)/2+ascent);
			i++;
		}
	}
	//����ӵ�list�У������ơ�
	public void doPlay(int row, int col, int chessman) {//���̶�Ӧx���꣬y���꣬������
		list.add(new Location(row, col, chessman));
		repaint();
	}
	/**
	 * �������
	 */
	public void clearBoard() {
		list.clear();//���
		repaint();
	}
	

}
