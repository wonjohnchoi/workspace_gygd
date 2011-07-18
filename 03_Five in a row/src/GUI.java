import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Wonjohn Choi
 *
 */
public class GUI implements ActionListener{
	protected JFrame frame;  //frame that manages the whole GUI.
	protected JButton[][] buttonGrid;  //array to store each button
	protected int SIZE;  //size of the board
	protected Engine engine;
	protected boolean isFinished = false;

	/**
	 * constructor: create 19 * 19 grid that each and every section is sensible to the mouse click.
	 */
	public GUI(){
		SIZE = promptSIZE();
		frame = new JFrame("Five in a row developed by Wonjohn Choi in G.Y.G.D.");
		frame.setLayout(new GridLayout(SIZE, SIZE));
		
		buttonGrid = new JButton[SIZE][SIZE];
		
		for(int row=0;row<SIZE;row++){
			for(int col=0;col<SIZE;col++){
				buttonGrid[row][col] = new JButton();
				frame.add(buttonGrid[row][col]);
				buttonGrid[row][col].addActionListener(this);
			}
		}
		
		frame.setSize(50*SIZE, 50*SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		engine = new Engine(SIZE);
	}
	
	/**
	 * used to restart a game
	 * basically does similar thing as constructor
	 * reset the size of the frame and make new buttons
	 */
	public void reset(){
		
		frame.setVisible(false);
		
		for(int row=0;row<SIZE;row++){
			for(int col=0;col<SIZE;col++){
				frame.remove(buttonGrid[row][col]);
			}
		}
		
		SIZE = promptSIZE();
		
		buttonGrid = new JButton[SIZE][SIZE];
		
		for(int row=0;row<SIZE;row++){
			for(int col=0;col<SIZE;col++){
				buttonGrid[row][col] = new JButton();
				frame.add(buttonGrid[row][col]);
				buttonGrid[row][col].addActionListener(this);
				
			}
		}

		isFinished = false;
		
		engine.SIZE=SIZE;
		engine.reset();
		
		
		frame.setSize(45*SIZE, 45*SIZE);
		frame.setLayout(new GridLayout(SIZE, SIZE));
		frame.setVisible(true);
	}
	
	/**
	 * ask user the size of the board
	 * wrong input indicates the exit of the program
	 */
	public int promptSIZE(){
		String strlen = JOptionPane.showInputDialog(frame, "Input the integral size of the board (5~19)", "Message Board", JOptionPane.QUESTION_MESSAGE);
		int intLen = 0;
		
		try{
			intLen = Integer.parseInt(strlen);
		}catch(Exception e){
			JOptionPane.showMessageDialog(frame, "Input should be in integral format");
			return promptSIZE();
		}
		
		if(intLen<5 || intLen>19){
			JOptionPane.showMessageDialog(frame, "Input should be 5~19");
			return promptSIZE();
		}
		
		return intLen;
	}

	/**
	 * when a mouse clicked a button,
	 */
	public void actionPerformed(ActionEvent e) {
		if(!isFinished){
			//get the button
			JButton clicked = (JButton) e.getSource();
			
			for(int row=0;row<SIZE;row++){
				for(int col=0;col<SIZE;col++){
					if(clicked == buttonGrid[row][col]){
						
						//fill the virtual array with text (O or X)
						engine.fill(row, col);
						
						//fill the real graphical button with text (O or X)
						if(engine.counter%2==0){
							buttonGrid[row][col].setText("X");
						}else{
							buttonGrid[row][col].setText("O");
						}
						
						//disable the button
						buttonGrid[row][col].setEnabled(false);	
					}
				}
			}
			
			//check if one wins the game
			isFinished = engine.isFinished();
			
			//if finished,
			if(isFinished) {
				
				//send message
				if(engine.counter%2==0){
					JOptionPane.showMessageDialog(frame, "'X' wins the game");
				}else{
					JOptionPane.showMessageDialog(frame, "'O' wins the game");
				}
				
			//check if the game is a tie
			}else if(engine.counter==SIZE*SIZE){
				isFinished = true;
				JOptionPane.showMessageDialog(frame, "The game is a tie");
			}
			 
			//part where program asks user whether he or she wants extra games
			if(isFinished) {
				int option = JOptionPane.showConfirmDialog(frame, "Do you want to player another game?", "Message Board", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if(option == JOptionPane.YES_OPTION){
					reset();
				} else{
					System.exit(0);
				}
			}
		}
	}

}
