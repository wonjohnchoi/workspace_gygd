/**
 * Engine of Connect Four game
 * @author Wonjohn Choi
 *
 */
public class ConnectFourEngine {
	//variables used to indicate whether a position is used or unused.
	private final int UNUSED=0;
	
	//array to store the info of each grid
	private int grid[][];
	//1 or 2 indicates each player
	protected int currentPlayer;
	
	/**
	 * Constructor
	 */
	public ConnectFourEngine(){
		//array is filled with 0 (UNUSED) in default
		grid=new int[6][7];
		
		currentPlayer=1;
	}
	
	/**
	 * Drop a checker into a given column.
	 * Return -1 if not possible
	 * Return the row that the checker landed on
	 */
	public int drop(int column) {
		int row = grid.length-1;
		while(row>=0 && grid[row][column] != UNUSED) {
			row--;
		}
				
		//No more space available on the column
		if (row == -1) {
			return -1;
		}
		
		// fill the calculated row of the column with a checker.
		grid[row][column] = currentPlayer;
		
		return row;
	}
	
	/**
	 * Check if the game is finished
	 * 0. Not finished
	 * 1. no more space available (draw)
	 * 2. one of the players connected 4 stones
	 */
	public int checkStatus(){
		//set the status as not finished
		int status=0;
		
		//check for extra space
		boolean available = false;
		
		for(int row=0; row<grid.length; row++){
			for(int column=0; column<grid[row].length;column++){
				if(grid[row][column] == UNUSED) {
					available = true;
				}
			}
		}
		
		//if no more space is not available,
		if(available==false){
			status=1;
		}
		
		//check for a vertical win
		for (int column = 0; column < 7; column++) {
			for (int row = 0; row < 3; row++) {
				if (grid[row][column] != UNUSED
						&& grid[row][column] == grid[row + 1][column]
						&& grid[row][column] == grid[row + 2][column]
						&& grid[row][column] == grid[row + 3][column]) {
					status = 2;
				}
			}
		}
		
		//check for a horizontal win
		for (int row = 0; row < 6; row++) {
			for (int column = 0; column < 4; column++) {
				if (grid[row][column] != UNUSED
						&& grid[row][column] == grid[row][column + 1]
						&& grid[row][column] == grid[row][column + 2]
						&& grid[row][column] == grid[row][column + 3]) {
					status = 2;
				}
			}
		}
		
		//check for a diagonal win (positive slope)
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 4; column++) {
				if (grid[row][column] != UNUSED
						&& grid[row][column] == grid[row + 1][column + 1]
						&& grid[row][column] == grid[row + 2][column + 2]
						&& grid[row][column] == grid[row + 3][column + 3]) {
					status = 2;
				}
			}
		}
		
		//check for a diagonal win (negative slope)
		for (int row = 3; row < 6; row++) {
			for (int column = 0; column < 4; column++) {
				if (grid[row][column] != UNUSED
						&& grid[row][column] == grid[row - 1][column + 1]
						&& grid[row][column] == grid[row - 2][column + 2]
						&& grid[row][column] == grid[row - 3][column + 3]) {
					status = 2;
				}
			}
		}
		
		return status;
	}
}
