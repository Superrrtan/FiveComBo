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
	 * private int margin = 20 ;//�ݱ߽���� private int row = 30 ; //���Ӽ�� private int
	 * Radius = 13;//���Ӱ뾶
	 */	
		public void lauchFrame() {
		this.setTitle("FIVE-COMBO����"+"�ڴ������֮һ��");
		//��ʼ����������Լ����
		panel = new Panel();
		this.add(panel);
		this.setLocation(600, 200);
		this.setSize(476, 552);
		this.setVisible(true);
		//����������
		JPanel jp = new JPanel();//����������
		this.add(jp, BorderLayout.NORTH);//���
		jp.setBorder(null);//���ù����������߿�
		
		//��һ�����ߣ��ؿ�һ��
		
		JButton restartAction = new JButton("�ؿ�һ��");//Action
		//restartAction.setToolTipText("�ؿ�һ��");
		restartAction.setOpaque(true);//͸��
		restartAction.setBorderPainted(false);//ȥ���߿�
		restartAction.setFocusPainted(false);//ȥ�������
		restartAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartBoard();//�ؿ����
			}
		});
		jp.add(restartAction);//���reastartAction
		//Ϊ������������������¼�
		//Ϊ������������������¼�
				panel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {//
						
						showChess(panel, e);
					}
				});
		}
		/**
		 * ��������������¼�
		 * @param chessPanel
		 * @param e
		 */
		public void showChess(Panel panel, MouseEvent e) {
			//�����λ������
			int x = e.getX();
			int y = e.getY();
			
			//ת��Ϊ�����ϵ�����ֵ
			int col = (x - 5) / 30;//�м��Ϊ30
			int row = (y - 5) / 30;//�м��Ϊ30
			System.out.println("��  ("+col+","+row+")");
			//���������Ч
			boolean isEnable = chess.play(row, col,Chess.HUMAN);
			if (isEnable) {
				// ��������������
				panel.doPlay(row, col, Chess.HUMAN);
				
				//���ʤ��
				if (chess.isWin(row, col, Chess.HUMAN)){
					JOptionPane.showMessageDialog(this, "�˻�ʤ", "��ϲ���ʤ���ˣ�",JOptionPane.WARNING_MESSAGE);  
					restartBoard();//��ʼ������
					return;
				}
				
				//��ֿ������������ȡ����λ��
				Location result = chess.LocationAI();
				
				//���̿���������AI����
				chess.play(result.getX(), result.getY(),Chess.AI);
				System.out.println("��  ("+result.getX()+","+result.getY()+")");
				// ��������������
				panel.doPlay(result.getX(), result.getY(), Chess.AI);
				
				//AIʤ��
				if (chess.isWin(result.getX(), result.getY(),Chess.AI)){
					JOptionPane.showMessageDialog(this, "������ʤ", "������˻����ˣ�",JOptionPane.WARNING_MESSAGE); 
					restartBoard();
					return;
				}
				
			} else System.out.println("������Ч!");
		}
	
		
	

	public void restartBoard() {
		chess.restart();//���̿�������ʼ������
		panel.clearBoard();//������������ػ�
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameFrame f = new GameFrame();
		f.lauchFrame();
	}	
	
	
	
	
	
	
	

}
