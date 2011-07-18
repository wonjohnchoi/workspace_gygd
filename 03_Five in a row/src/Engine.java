import java.util.Arrays;

public class Engine {
	//counter that counts # of turns passed (used to see whose turn is this)
	protected int counter;
	
	protected int SIZE = 0;  //size of the board
	
	//grid to store info
	protected char grid[][];
	
	/**
	 * constructor
	 */
	public Engine(int SIZE){
		this.SIZE = SIZE;
		counter=0; 
		grid = new char[SIZE][SIZE];
		
		for(int row=0;row<SIZE;row++){
			//'N' represents "NOT USED"
			Arrays.fill(grid[row], 'N');
		}
	}
	
	/**
	 * fill a specific position for the next player
	 */
	public void fill(int row, int col) {
		counter++;
		grid[row][col] = (counter%2==0? 'X':'O');
	}
	
	/**
	 * method to restart a game
	 */
	public void reset(){
		grid = new char[SIZE][SIZE];
		for(int row=0;row<SIZE;row++){
			for(int col=0;col<SIZE;col++){
				grid[row][col] = 'N';
			}
		}
		
		counter = 0;
	}
	

	
	/**
	 * Check if the game is finished
	 */
	public boolean isFinished(){
		//set the status as not finished
		boolean status = false;
		
		//check for a vertical win
		for (int column = 0; column < SIZE && !status; column++) {
			for (int row = 0; row < SIZE-4 && !status; row++) {
				if (grid[row][column] != 'N'
						&& grid[row][column] == grid[row + 1][column]
						&& grid[row][column] == grid[row + 2][column]
						&& grid[row][column] == grid[row + 3][column]
					    && grid[row][column] == grid[row + 4][column]) {
					status = true;
				}
			}
		}
		
		//check for a horizontal win
		for (int row = 0; row < SIZE && !status; row++) {
			for (int column = 0; column < SIZE-4 && !status; column++) {
				if (grid[row][column] != 'N'
						&& grid[row][column] == grid[row][column + 1]
						&& grid[row][column] == grid[row][column + 2]
						&& grid[row][column] == grid[row][column + 3]
						&& grid[row][column] == grid[row][column + 4]) {
					status = true;
				}
			}
		}
		
		//check for a diagonal win (positive slope)
		for (int row = 0; row < SIZE-4 && !status; row++) {
			for (int column = 0; column < SIZE-4 && !status; column++) {
				if (grid[row][column] != 'N'
						&& grid[row][column] == grid[row + 1][column + 1]
						&& grid[row][column] == grid[row + 2][column + 2]
						&& grid[row][column] == grid[row + 3][column + 3]
						&& grid[row][column] == grid[row + 4][column + 4]) {
					status = true;
				}
			}
		}
		
		//check for a diagonal win (negative slope)
		for (int row = 4; row < SIZE && !status; row++) {
			for (int column = 0; column < SIZE-4 && !status; column++) {
				if (grid[row][column] != 'N'
						&& grid[row][column] == grid[row - 1][column + 1]
						&& grid[row][column] == grid[row - 2][column + 2]
						&& grid[row][column] == grid[row - 3][column + 3]
						&& grid[row][column] == grid[row - 4][column + 4]) {
					status = true;
				}
			}
		}
		
		return status;
	}
}
