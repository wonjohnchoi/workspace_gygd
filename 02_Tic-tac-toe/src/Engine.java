import java.util.Arrays;


/**
 * TicTacToeEngine class to enable user to enjoy the traditional game "Tic-tac-toe" with 2 players mode.
 * This manages the general logic
 * @author Wonjohn Choi
 *
 */
public class Engine {
	//to easily recognize variables
	//O -> occupied by 'o'
	//X -> occupied by 'x'
	//N -> not occupied
	protected static final int O = 2;
	protected static final int X = 1;
	protected static final int N = 0;
	
	//1 or 2
	protected int currentPlayer;
	
	//grid
	protected int grid[][];
	
	/**
	 * constructor
	 */
	public Engine(){
		//create 3*3 grid filled with no signs
		grid = new int[3][3];
		
		currentPlayer = 1;	
	}
	
	/**
	 * used to restart a game
	 */
	public void reset(){
		for(int row=0;row<3;row++){
			Arrays.fill(grid[row], 0);
		}
		currentPlayer=1;
	}
	
	/**
	 * fill a specific position with the given sign
	 * @param row
	 * @param col
	 * @param sign
	 */
	public void fill(int row, int col, int sign) {
		grid[row][col] = sign;
	}
	
	/**
	 * check the status of the game
	 * @return 0 if not ended. 1 if player1 wins. 2 if player2 wins. 3 if tied.
	 */
	public int getStatus() {
		//not ended
		int status = 0;
		
		//tie
		boolean isTied=true;
		for(int row=0;row<3;row++){
			for(int col=0;col<3;col++){
				if(grid[row][col]==N){
					isTied=false;
				}
			}
		}
		
		if(isTied){
			status = 3;
		}
		
		//horizontal
		for(int row=0;row<3;row++){
			if(grid[row][0]!=N && grid[row][0]==grid[row][1] && grid[row][0]==grid[row][2]){
				status=currentPlayer;
			}
		}
		
		//vertical
		for(int col=0;col<3;col++){
			if(grid[0][col]!=N && grid[0][col]==grid[1][col] && grid[0][col]==grid[2][col]){
				status = currentPlayer;
			}
		}
		
		//diagonal
		if(grid[0][0]!=N && grid[0][0]==grid[1][1] && grid[0][0]==grid[2][2]){
			status = currentPlayer;
		}
		if(grid[0][2]!=N && grid[0][2]==grid[1][1] && grid[0][2] == grid[2][0]){
			status = currentPlayer;
		}
		
		return status;
	}
	
	
}
