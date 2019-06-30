package cn.sxt.game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;








public class GameFrame extends JFrame {
	private ArrayList<Location> list = new ArrayList<Location>();
	private Chess chess = new Chess();
	private Panel panel ;
	/*
	 * private int margin = 20 ;//据边界距离 private int row = 30 ; //落子间距 private int
	 * Radius = 13;//棋子半径
	 */	
		public void lauchFrame() {
		this.setTitle("FIVE-COMBO——"+"期待你的神之一手");
		//初始化棋盘面板以及添加
		panel = new Panel();
		this.add(panel);
		this.setLocation(600, 200);
		this.setSize(476, 552);
		this.setVisible(true);
		//创建工具栏
		JPanel jp = new JPanel();//创建工具栏
		this.add(jp, BorderLayout.NORTH);//添加
		jp.setBorder(null);//设置工具栏不画边框
		
		//第一个工具：重开一局
		
		JButton restartAction = new JButton("重开一局");//Action
		//restartAction.setToolTipText("重开一局");
		restartAction.setOpaque(true);//透明
		restartAction.setBorderPainted(false);//去掉边框
		restartAction.setFocusPainted(false);//去掉焦点框
		restartAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartBoard();//重开棋局
			}
		});
		jp.add(restartAction);//添加reastartAction
		//为棋盘面板设置鼠标监听事件
		//为棋盘面板设置鼠标监听事件
				panel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {//
						
						showChess(panel, e);
					}
				});
		}
		/**
		 * 棋盘面板的鼠标点击事件
		 * @param chessPanel
		 * @param e
		 */
		public void showChess(Panel panel, MouseEvent e) {
			//点击的位置坐标
			int x = e.getX();
			int y = e.getY();
			
			//转化为棋盘上的行列值
			int col = (x - 5) / 30;//行间隔为30
			int row = (y - 5) / 30;//列间隔为30
			System.out.println("黑  ("+col+","+row+")");
			//玩家落子有效
			boolean isEnable = chess.play(row, col,Chess.HUMAN);
			if (isEnable) {
				// 棋盘面板绘制棋子
				panel.doPlay(row, col, Chess.HUMAN);
				
				//玩家胜利
				if (chess.isWin(row, col, Chess.HUMAN)){
					JOptionPane.showMessageDialog(this, "人获胜", "恭喜玩家胜利了！",JOptionPane.WARNING_MESSAGE);  
					restartBoard();//初始化棋盘
					return;
				}
				
				//棋局控制器分析后获取落子位置
				Location result = chess.LocationAI();
				
				//棋盘控制器控制AI落子
				chess.play(result.getX(), result.getY(),Chess.AI);
				System.out.println("白  ("+result.getX()+","+result.getY()+")");
				// 棋盘面板绘制棋子
				panel.doPlay(result.getX(), result.getY(), Chess.AI);
				
				//AI胜利
				if (chess.isWin(result.getX(), result.getY(),Chess.AI)){
					JOptionPane.showMessageDialog(this, "机器获胜", "你输给了机器了！",JOptionPane.WARNING_MESSAGE); 
					restartBoard();
					return;
				}
				
			} else System.out.println("坐标无效!");
		}
	
		
	

	public void restartBoard() {
		chess.restart();//棋盘控制器初始化棋盘
		panel.clearBoard();//棋盘清除棋子重绘
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameFrame f = new GameFrame();
		f.lauchFrame();
	}	
	
	
	
	
	
	
	

}
