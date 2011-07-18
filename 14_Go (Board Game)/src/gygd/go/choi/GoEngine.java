package gygd.go.choi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GoEngine implements ActionListener {
	private int counter = 0;
	private GoBoard gBoard;
	//private GoMenu gMenu;
	private GoGUI gGUI = new GoGUI();
	private JFrame window = new JFrame("Go, the Asian turn-based board game, offered by Wonjohn Choi");
	private static Color stoneColors[] = {Color.BLACK, Color.WHITE};
	
	/**
	 * constructor
	 */
	public GoEngine(int size){
		int nBlock = promptSIZE();
		window.setSize(nBlock*size, nBlock*size);
		window.add(gGUI);
		gBoard = new GoBoard(nBlock, nBlock, size);
		//window.add(gBoard = new GoBoard(nBlock, nBlock, size));
		gGUI.initMapLog(gBoard);
		gGUI.initPlayerInfo(new GoPlayer[]{new GoPlayer(Color.BLACK), new GoPlayer(Color.WHITE)});
		
		for(int row=0;row<gBoard.HEIGHT;row++){
			for(int col=0;col<gBoard.WIDTH;col++){
				gBoard.get(row, col).addActionListener(this);
			}
		}
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.pack();
	}
	
	/**
	 * ask user the size of the board
	 * wrong input indicates the exit of the program
	 */
	public int promptSIZE(){
		String strlen = JOptionPane.showInputDialog(window, "Input the integral size of the board (3~19)", "Message Board", JOptionPane.QUESTION_MESSAGE);
		int intLen = 0;
		
		try{
			intLen = Integer.parseInt(strlen);
		}catch(Exception e){
			JOptionPane.showMessageDialog(window, "Input should be in integral format");
			return promptSIZE();
		}
		
		if(intLen<3 || intLen>19){
			JOptionPane.showMessageDialog(window, "Input should be 5~19");
			return promptSIZE();
		}
		
		return intLen;
	}

	
	/*
	 * 
	 * 
	 */
	
	public static void main(String args[]){
		new GoEngine(30);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		
		int r = 0, c = 0;
		for(int row=0;row<gBoard.HEIGHT;row++){
			for(int col = 0;col<gBoard.WIDTH;col++){
				if(gBoard.get(row,col)==button){
					r=row;c=col;
				}
			}
		}
		String name = counter%2==0?"Black":"White";
		gGUI.writeLog(name+": ("+c+", "+r+")");
		
		if(button.getBackground()==GoBoard.BOARD_COLOR){
			button.setBackground(stoneColors[counter%2]);
			
			for(int i=1;i>=0;i--){
				for(int row=0;row<gBoard.HEIGHT;row++){
					for(int col=0;col<gBoard.WIDTH;col++){
						LinkedList<JButton> deadStones = findDeadStones(stoneColors[(counter+i)%2], row, col);
						
						if(deadStones!=null && deadStones.size()!=0){
							for(JButton stone: deadStones){
								stone.setBackground(GoBoard.BOARD_COLOR);
							}
							
							System.out.println(deadStones.size()+" was owned");
						}
					}
				}
			}
			
			counter++;
		}
		
		System.out.println(counter);
	}
	
	
	
	
	private static Color TEMP = Color.GRAY;
	private static int movements[][] ={{1,0},{-1,0},{0,1},{0,-1}};
	
	public LinkedList<JButton> findDeadStones(Color deadColor, int row, int col){
		LinkedList<JButton> deadStones = new LinkedList<JButton>();
		
		if(gBoard.inWidthRange(row) && gBoard.inHeightRange(col)){
			if(gBoard.get(row,col).getBackground()==deadColor){
				gBoard.get(row, col).setBackground(TEMP);
				deadStones.add(gBoard.get(row, col));
				
				for(int i=0;i<movements.length;i++){
					LinkedList<JButton> possiblyDeads = findDeadStones(deadColor, row+movements[i][0], col+movements[i][1]);
					if(possiblyDeads==null){
						deadStones = null;
						break;
					}else{
						deadStones.addAll(possiblyDeads);
					}
				}
				
				gBoard.fill(row, col, deadColor);
				
			}else if(gBoard.get(row,col).getBackground()==GoBoard.BOARD_COLOR){
				deadStones = null;
			}else{
				
			}
		}
		//System.out.println(deadStones==null?null:deadStones.size());
		return deadStones;
	}
	
}
