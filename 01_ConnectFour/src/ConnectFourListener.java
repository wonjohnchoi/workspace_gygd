import javax.swing.*;
import java.awt.event.*;
/**
 * Listener class to recognize user's mouse clicking
 * @author Wonjohn Choi
 *
 */
public class ConnectFourListener implements MouseListener {
	//flag to see whether game ended
	private boolean isEnd=false;
	
	//variables to have control over gui and engine
	ConnectFourGUI gui;
	ConnectFourEngine engine;

	/**
	 * constructor
	 * @param engine
	 * @param gui
	 */
	public ConnectFourListener(ConnectFourEngine engine, ConnectFourGUI gui) {
		this.engine = engine;
		this.gui = gui;
		
		//add the listener to each of labels in the main frame
		gui.addListener(this);
	}

	/**
	 * when mouse is clicked
	 */
	public void mouseClicked(MouseEvent e) {
		if(!isEnd){
			JLabel label = (JLabel) e.getComponent();
			
			//get the column where the click belongs to using the given label
			int column = gui.getColumn(label);
			
			//get the row that the click belongs to using logic in engine
			int row = engine.drop(column);
		
			//if row is -1, it means the selected location is not available
			if (row != -1) {
				gui.set(row, column);
			}
			
			//get the status of the game
			int status = engine.checkStatus();
			
			//use the status to perform the next action
			if(status==0){
				//change the turn
				engine.currentPlayer = engine.currentPlayer%2+1;
			}else if(status==1){
				//message to tell that it is a draw
				JOptionPane.showMessageDialog(null, "The game was tie!");
				isEnd=true;
			}else{
				//message to tell that currentPlayer won
				if(engine.currentPlayer==1){
					JOptionPane.showMessageDialog(null,"'X' wins the game!");
				}else{
					JOptionPane.showMessageDialog(null,"'O' wins the game!");
				}
				isEnd=true;
			}
		}
	}

	public void mousePressed(MouseEvent event) {
	}

	public void mouseReleased(MouseEvent event) {
	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}
}
