import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Wonjohn Choi
 *
 */
public class FillThirtySix extends JFrame implements ActionListener{

	private static final long serialVersionUID = 4453306257523761398L;
	
	protected JPanel board;
	protected JPanel info;
	protected JButton[][] pieces;
	
	protected final int SIZE = 6;
	protected Vector<JButton> curCheckers;
	protected int counter;
	protected boolean turnFlag;
	
	/**
	 * constructor
	 */
	public FillThirtySix(){		
		super("'Fill Thirty Six' developed by Wonjohn Choi and Heejin Choi in G.Y.G.D.");
		turnFlag = true;
		counter = 0;
		curCheckers = new Vector<JButton>(7);
		
		initGUI();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	
	/**
	 * build up the game board
	 */
	public void initGUI() {
		setVisible(false);
		
		setSize(200, 250);
		setLayout(new BorderLayout());
		
		//set up board
		board = new JPanel();
		board.setLayout(new GridLayout(SIZE, SIZE));
		
		pieces = new JButton[SIZE][SIZE];
		
		for(int row=0;row<SIZE;row++){
			for(int col=0;col<SIZE;col++){
				pieces[row][col] = new JButton();
				pieces[row][col].addActionListener(this);
				board.add(pieces[row][col]);
			}
		}
		
		add(board, BorderLayout.CENTER);
		
		//set up extra panel
		info = new JPanel();
		
		//add End Turn button
		JButton endButton = new JButton("End Turn");
		endButton.addActionListener(this);
		info.add(endButton);
		
		//add panel to the frame
		add(info, BorderLayout.NORTH);
	}

	/**
	 * when clicked by mouse
	 */
	public void actionPerformed(ActionEvent e) {
		//get the clicked button
		JButton clicked = (JButton) e.getSource();

		//when user clicked "End Turn" button
		if(clicked.getText().equals("End Turn")){
			
			//if the user didn't clicked any button for his/her turn
			if(curCheckers.size()==0){
				JOptionPane.showMessageDialog(this, "You cannot end your turn before using at least one checker", "Message Board", JOptionPane.WARNING_MESSAGE);
			}else //change turn
			{
				for(JButton button: curCheckers){
					if(turnFlag){
						button.setBackground(Color.red);
					}else{
						button.setBackground(Color.black);
					}
				}
				
				curCheckers.clear();
				turnFlag=!turnFlag;
			}
			//if user attempts to click more than 7
		}else if(curCheckers.size()>6){
			JOptionPane.showMessageDialog(this, "End your turn. You used all 7 checkers for this turn", "Message Board", JOptionPane.WARNING_MESSAGE);
		}else{
			//turn out a button by putting checker
			clicked.setBackground(Color.GREEN);
			clicked.setEnabled(false);
			curCheckers.add(clicked);
			
			counter++;
		}
		
		//if all of the spaces are filled,
		if(counter==SIZE*SIZE){
			String winner="";
			if(turnFlag){
				winner="BLACK";
			}else{
				winner="RED";
			}
			JOptionPane.showMessageDialog(this, winner+" wins the game!");
			System.exit(0);
		}
	}

	/**
	 * main method to start a game
	 * @param args
	 */
	public static void main(String args[]){
		new FillThirtySix();
	}
	
}
