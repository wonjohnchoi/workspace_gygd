package gygd.choi.minesweeper;

import gygd.choi.gui.ButtonChooser;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends JFrame implements ActionListener {
	Engine m;
	JButton buttons[][];

	public GUI() {
		super("Minesweeper programmed by Wonjohn Choi");
		
		int[] mode = getMode();
		initGame(mode[0],mode[1],mode[2]);
		
	}

	public int[] getMode() {
		//buttons
		JButton[][] buttons = new JButton[][] {
				{ new JButton("Easy")}, {new JButton("Medium")},
				{new JButton("Hard")}};

		ButtonChooser bc = new ButtonChooser(buttons, 1); //create button chooser
		Thread chooser = new Thread(bc); //create its thread
		chooser.start(); //start

		//make sure this thread does not continue till getting information from user
		synchronized (bc.out) {
			try {
				bc.out.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		String chosen = bc.out[0].getText();
		int[] mode = null;
		
		if(chosen.equals("Easy")){
			mode = new int[]{9,9,10};
		}else if(chosen.equals("Medium")){
			mode = new int[]{16,16,40};
		}else if(chosen.equals("Hard")){
			mode = new int[]{16,30,99};
		}else{
			try {
				throw new Exception("No such mode available!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mode;
	}

	public void initGame(int row, int col, int numMines) {
		m = new Engine(row, col, numMines);

		setLayout(new GridLayout(row, col));
		buttons = new JButton[row][col];

		for (int r = 0; r < buttons.length; r++) {
			for (int c = 0; c < buttons[0].length; c++) {
				buttons[r][c] = new JButton();
				buttons[r][c].addActionListener(this);
				add(buttons[r][c]);
			}
		}

		setSize(col*40, row*40);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		button.setEnabled(false);
		
		int row = 0, col = 0;
		boolean found=false;
		for(int r=0;r<buttons.length && !found;r++){
			for(int c=0;c<buttons[0].length && !found;c++){
				if(buttons[r][c] == button){
					row=r;
					col=c;
					found=true;
				}
			}
		}
		
		m.uncoverSquare(row, col);
		
		if(m.grid[row][col]==9){
			buttons[row][col].setBackground(Color.red);
		}else{
			buttons[row][col].setText(""+m.grid[row][col]);
		}
		
		

	}
	
	public static void main(String args[]){
		new GUI();
	}
}
