package gygd.go.choi;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Board class (part of Go <Board Game>)
 * 
 * Copyright (C) 2010 Wonjohn Choi
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * @author Wonjohn Choi
 *
 */
public class GoBoard extends JPanel {
	/**
     * UID
     */
    private static final long serialVersionUID = 8735251292056356635L;

    //array to control each block
	private JButton[][] blockGrid;

	//width and height of the board
	protected int HEIGHT, WIDTH;
	
	//default color of board
	protected static final Color BOARD_COLOR = new Color(215, 135, 65);
	//protected static final Color BLACK = Color.BLACK;
	//protected static final Color WHITE = Color.WHITE;	
	/**
	 * constructor
	 * @param height height of the board
	 * @param width width of the board
	 */
	public GoBoard(int height, int width){
		HEIGHT = height; WIDTH = width;
		
		blockGrid = new JButton[HEIGHT][WIDTH]; //instantiate button array
		setLayout(new GridLayout(HEIGHT, WIDTH)); //set its layout using layout manager
		
		//for each and every block,
		for(int row=0;row<HEIGHT;row++){
			for(int col=0;col<WIDTH;col++){
				blockGrid[row][col] = new JButton();//set each block as a button
				blockGrid[row][col].setBorder(new LineBorder(Color.getHSBColor(0, 0,0.8F)));//set its border color
				//blockGrid[row][col].setEnabled(false);
				
				blockGrid[row][col].setBackground(BOARD_COLOR);
				//blockGrid[row][col].setIcon(WHITE);
				//blockGrid[row][col].set;//(("Black_Piece.png"));
				add(blockGrid[row][col]);//add to the panel
			}
		}
		
		setPreferredSize(new Dimension(WIDTH*30, HEIGHT*30)); //set the size (each block has a size length of 30)
	}
	
	/**
	 * another version of constructor
	 * @param height height of board
	 * @param width width of board
	 * @param size size of block
	 */
	public GoBoard(int height, int width, int size){
	    this(height, width);
	    setPreferredSize(new Dimension(WIDTH*size, HEIGHT*size));
	}

	/**
	 * check if a grid is empty
	 */
	public boolean isEmpty(int row, int col){
		return blockGrid[row][col].getBackground().equals(BOARD_COLOR);
	}
	
	/**
	 * set a block empty
	 * @param row
	 * @param col
	 */
	public void setEmpty(int row, int col){
	    blockGrid[row][col].setBackground(BOARD_COLOR);
	}
	
	/**
	 * return a button
	 * @param row
	 * @param col
	 * @return
	 */
	public JButton get(int row, int col){
		return blockGrid[row][col];
	}
	
	/**
	 * fill a grid
	 */
	public void fill(int row, int col, Color c){
		blockGrid[row][col].setBackground(c);
	}
	
	
	
	
	/**
	 * reset board
	 */
    public void reset() {
        
       //for every index
        for(int r=0;r<HEIGHT;r++){
            for(int c=0;c<WIDTH;c++){
                blockGrid[r][c].setBackground(BOARD_COLOR); //reset color
                //grid[r][c] = EMPTY; //reset data
            }
        }
        
    }

    
    /**
     * check range in width
     */
    public boolean inWidthRange(int x){
        return x>=0 && x<WIDTH;
    }
    
    /**
     * check range in height
     */
    public boolean inHeightRange(int y){
        return y>=0 && y<HEIGHT;
    }


    /**
     * main test method
     * @param args
     */
    public static void main(String args[]){
    	JFrame test = new JFrame();
    	GoBoard board = new GoBoard(19, 19, 30);
    	test.add(board);
    	test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	test.setVisible(true);
    }
}