import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/**
 * GUI of Connect Four Game
 * @author Wonjohn Choi
 *
 */
public class ConnectFourGUI {
	//manage the whole frame
	private JFrame frame;
	
	//manage each graphicGrid's setting
	private JLabel[][] graphicGrid;
	
	//keeps track of the cuerrent player
	private int currentPlayer;

	/**
	 * constructor
	 */
	public ConnectFourGUI() {
		//create the frame to manipulate the entire screen
		frame = new JFrame("Connect Four by Wonjohn Choi in G.Y.G.D");
		
		//create a container/panel from the frame
		JPanel panel = (JPanel) frame.getContentPane();
		
		//divide the panel into 6*7 pieces
		panel.setLayout(new GridLayout(6, 7));
		
		//create 6*7 label array to control each section
		graphicGrid = new JLabel[6][7];
		
		//for each and every section, instantiate labels and create gray borders.
		//then add the section (label) to the panel
		for (int row=0;row<graphicGrid.length; row++) {
			for (int column = 0; column < graphicGrid[row].length; column++) {
				graphicGrid[row][column] = new JLabel();
				graphicGrid[row][column].setHorizontalAlignment(SwingConstants.CENTER);
				graphicGrid[row][column].setBorder(new LineBorder(Color.getHSBColor(0, 0,0.8F)));
				panel.add(graphicGrid[row][column]);
			}
		}
		
		//set the size
		frame.setSize(350, 300);
		
		//make it visible
		frame.setVisible(true);
		
		//When closing the window, close the application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//variable to keep track of the player
		currentPlayer = 1;
	}

	/**
	 * method to add the ConnectFourListener to each and every label/section
	 * @param listener
	 */
	public void addListener(ConnectFourListener listener) {
		for (int row=0;row<graphicGrid.length; row++) {
			for (int column = 0; column < graphicGrid[row].length; column++) {
				graphicGrid[row][column].addMouseListener(listener);
			}
		}
	}

	/**
	 * get the column in which the given label belongs to
	 * @param label
	 * @return
	 */
	public int getColumn(JLabel label) {
		//for each and every label, compare it to the given label,
		for (int row=0;row<graphicGrid.length; row++) {
			for (int column = 0; column < graphicGrid[row].length; column++) {
				
				//if matched, return the column
				if (graphicGrid[row][column] == label) {
					return column;
				}
			}
		}
		return -1;
	}

	/**
	 * set a label with the given position using a graphical content
	 * @param row
	 * @param column
	 */
	public void set(int row, int column) {
		// for the first player, use black rectangle image
		if (currentPlayer == 1) {
			graphicGrid[row][column].setText("X");
	
		// for the other, use red rectangle image
		} else {
			graphicGrid[row][column].setText("O");
		}
		
		//move the turn to the next player
		currentPlayer = (currentPlayer % 2) + 1;
	}
	
	
	
}
