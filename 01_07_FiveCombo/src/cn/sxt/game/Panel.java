package cn.sxt.game;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;


public class Panel extends JPanel {
private static final long serialVersionUID = 1L;
	
	//棋子位置集合
	public ArrayList<Location> list = new ArrayList<Location>();
	
	
	//棋盘单元格的长度
	public static final int row = 30;
	
	//棋盘每行第一个点与左侧的距离
	public static final int margin=20;
	
	//每行15个落子点
	public static final int rowper=15;
	
	//棋子半径
	public static final int chessRadius=13;
	
	//棋盘背景颜色
	//Color bgColor=new Color(246,214,159);

	
	//星的颜色
	
	
	@Override
	public void paint(Graphics g1) {
		super.paint(g1);
		Graphics2D g=(Graphics2D) g1;
		drawBoard(g);//画棋盘
		drawChessman(g);//画棋子
	}
	
	/**
	 * 画棋盘
	 * @param g
	 */
	public void drawBoard(Graphics2D g){
		//设置背景颜色以及画背景
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//System.out.println(this.getWidth()+" "+this.getHeight());
		
		//画棋盘线
		g.setColor(Color.black);
		for (int i = 0; i < rowper; i++) {
			g.drawLine(margin, margin + row * i, this.getWidth() - margin, margin + row * i);
			g.drawLine(margin + row * i, margin, margin + row * i, this.getHeight() - margin);
		}
		
		//设置颜色以及画棋盘上的点
		//10是圆的半径
		g.setColor(Color.cyan);
		g.fillOval(margin-5 + 3 * row, margin-5 + 3 * row, 10, 10);
		g.fillOval(margin-5 + 7 * row, margin-5 + 7 * row, 10, 10);
		g.fillOval(margin-5 + 3 * row, margin-5 + 11 * row, 10, 10);
		g.fillOval(margin-5 + 11 * row, margin-5 + 3 * row, 10, 10);
		g.fillOval(margin-5 + 11 * row, margin-5 + 11 * row, 10, 10);
	}
	
	/**
	 * 画棋子
	 * @param g
	 */
	public void drawChessman(Graphics2D g){
		int i=1;//标志当前进行的手数
		
		//得到FontMetrics对象
		//主要为了设置字体居中
		FontMetrics metrics=g.getFontMetrics();
		int ascent = metrics.getAscent();
		int descent = metrics.getDescent();
		
		//遍历棋局绘制棋子
		for (Location location : list) {
			if (location.getChessman() == Chess.HUMAN)
				g.setColor(Color.black);//设置先手为黑色
			else
				g.setColor(Color.white);//设置后手为白色
			
			//画棋子
			g.fillOval(margin-13 + location.getY() * row, margin-chessRadius + location.getX() * row, chessRadius*2, chessRadius*2);
			
			//画棋子上的数字
			if(location.getChessman()==Chess.HUMAN) g.setColor(Color.white);
			else g.setColor(Color.black);
			String string=i+"";
			//计算字符串应在的坐标
			g.drawString(string,margin + location.getY() * row-metrics.stringWidth(string)/2,margin + location.getX() * row-(ascent+descent)/2+ascent);
			i++;
		}
	}
	//添加子到list中，并绘制。
	public void doPlay(int row, int col, int chessman) {//棋盘对应x坐标，y坐标，落子者
		list.add(new Location(row, col, chessman));
		repaint();
	}
	/**
	 * 清除棋盘
	 */
	public void clearBoard() {
		list.clear();//清除
		repaint();
	}
	

}
