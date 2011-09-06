import java.io.*;
import java.util.*;
import hsa.Console;

/**
 * ICS4U Final Exam Practical - Mines Class
 * 
 * @author Mr. Reid
 * @modifier(Student) Wonjohn Choi
 * @date 2010/06/17
 * @version 1.2
 */
public class Mines 
{

	/**
	 * Attributes 
	 */
	Console c = null;
	int numCols = 0;
	int numRows = 0;
	int numMines = 0;
	boolean boom = false; // Did they hit a bomb
	int grid[][] = null; // Bombs are marked as 9 and rest are how many bombs
							// are neighbours
	char covered[][] = null; // Marked as 'C' for covered or 'U' for uncovered

	/**
	 * Constructor
	 * 
	 * @param rows # of rows
	 * @param cols # of columns
	 * @param mines # of mines
	 */
	public Mines(int rows, int cols, int mines) 
	{
		//set existing variables using values from user
		numRows = rows;
		numCols = cols;
		numMines = mines;
		
		//initialize grid (int[][]), c(Console), rand(Random) objects
		grid = new int[numRows][numCols];
		c = new Console();
		Random rand = new Random();
		
		//for the number of mines that are required to be placed,
		for (int i = 0; i < mines; i++) 
		{
			//variable to indicate whether mine is well-placed
			boolean placed = false;
			
			//while mine is not placed (repeat),
			while (!placed) 
			{
				//get random location (r - location of height, c - location of width)
				int r = rand.nextInt(numRows);
				int c = rand.nextInt(numCols);
				
				//if current grid has not been used (0 means unused),
				if (grid[r][c] == 0) 
				{
					//fill current grid with mine (9-mine)
					grid[r][c] = 9;
					
					//to stop while loop, change placed variable to true
					placed = true;
				}
			}
		}
		
		//call method markNumbers to mark # of bombs
		markNumbers();
		
		//initialize covered array to mark whether covered or not
		covered = new char[numRows][numCols];
		
		//for each index from 0 to the height of grid,
		for (int i = 0; i < numRows; i++) 
		{
			//for each index from 0 to the width of grid,
			for (int j = 0; j < numCols; j++) 
			{
				//set each spot as covered (so all spots are indicated to be covered)
				covered[i][j] = 'C';
			}
		}
	}

	/**
	 * Determine if the game is over.
	 * @return boolean value to indicate whether game is over
	 */
	public boolean GameOver() 
	{
		//declare and initialize result variable that indicates whether game finishes
		//it is initially marked as not over
		boolean result = false;

		//if boom already went off, you lost
		//for each index from 0 to the height of grid, (put !result to stop loop when game overs)
		for (int i = 0; i < numRows && !result; i++) 
		{
			//for each index from 0 to the width of grid, (put !result to stop loop when game overs)
			for (int j = 0; j < numCols && !result; j++) 
			{
				//if current spot is uncovered and it's a bomb,
				if(covered[i][j]=='U' && grid[i][j]==9)
				{
					//set game as over
					result = true;
				}
			}
		}
		
		//if currently there is no covered mines, check whether all not mine places are covered,
		if(!result)
		{
			// How many covered squares remain?
			//set a variable to calculate # of covered squares,
			int numCovered = 0;
			
			//for each index from 0 to the height of grid,
			for (int i = 0; i < numRows; i++) 
			{
				//for each index from 0 to the width of grid,
				for (int j = 0; j < numCols; j++) 
				{
					//if current spot is covered,
					if(covered[i][j]=='C')
					{
						//increment # of covered squares counter
						numCovered++;
					}
				}
			}
	
			// if only covered mines remain, you won
			//if # of covered spaces are equal # of mines (all not mine places are uncovered),
			if(numCovered == numMines)
			{
				//set game as over
				result = true;
			}
		}

		return result;
	}

	/**
	 * Method to mark the numbers on the grid for how many
	 * neighbours to that square are bombs (1-8).
	 */
	private void markNumbers() 
	{
		//TODO for index from 0~height of grid,
		for(int i=0;i<numRows;i++)
		{
			//for index from 0~width of grid,
			for(int j=0;j<numCols;j++)
			{
				//if current location indicated by i,j index does not have a bomb,
				if(grid[i][j]!=9)
				{
					//declare a counter variable to count # of bombs in neighbor
					//assign intialize value as 0
					int counter = 0;
					
					//for each index from top to bottom
					for(int x=i-1;x<=i+1;x++)
					{
						//for each index from left to right
						for(int y=j-1;y<=j+1;y++)
						{
							//if current neighbor is not current spot itself,
							//and if x and y of neighbor is part of grid (in range),
							if(!(x==i && y==j) && (0<=x && 0<=y && x<numRows && y<numCols))	
							{
								//if neighbor is bomb (9),
								if(grid[x][y]==9)
								{
									//increase counter
									counter++;
								}
								
							}
						}
					}
					
					//set current grid using counted # of bombs
					grid[i][j] = counter;
				}
			}
		}
	}

	/**
	 * Part 4: This method uncovers the specified square. If it's a bomb, it
	 * explodes and the game is over. If it's next to one or more bombs, it
	 * shows how many bombs are on neighbouring squares (1-8). If it is blank(0) it
	 * means there are no bombs on any squares around it and therefore all can
	 * be uncovered. Sounds a bit like recursion to me!! :) Complete the routine
	 * for the above algorithm. You must do so recursively for full marks but it
	 * can also be done iteratively for part marks. Remember to call this.show()
	 * during your code to animate the process.
	 * 
	 * @param row # of rows
	 * @param col # of columns
	 */
	public void uncoverSquare(int row, int col) 
	{
		//set current location as covered
		covered[row][col] = 'U';
		
		//If it's a bomb, 
		if(grid[row][col] == 9)
		{
			//tell that game is over,
			boom = true;
		}
		
		//If it's next to one or more bombs,
		else if(grid[row][col]>0)
		{
			//do nothing
		}
		
		//if current space does not have boom neighbor,
		else if(grid[row][col] == 0)
		{
			//Process to uncover recursively other neighbors,
			//for each index from 0 to the height of grid, (put !result to stop loop when game overs)
			for (int i = 0; i < numRows; i++) 
			{
				//for each index from 0 to the width of grid, (put !result to stop loop when game overs)
				for (int j = 0; j < numCols; j++) 
				{
					//for each index from top to bottom
					for(int x=i-1;x<=i+1;x++)
					{
						//for each index from left to right
						for(int y=j-1;y<=j+1;y++)
						{
							//if current neighbor is not current spot itself,
							//and if x and y of neighbor is part of grid (in range),
							if(!(x==i && y==j) && (0<=x && 0<=y && x<numRows && y<numCols))	
							{
								//if neibor is covered
								if(covered[x][y]=='C' && grid[x][y] ==0)
								{
									//uncover neighbors
									uncoverSquare(x, y);
								}
								
							}
						}
					}
				}
			}
		}
		else
		{
			System.out.println("UNEXPECTED ERROR");
		}
		this.show();
		
	}

	/***************************************************************************
	 * Nothing beyond this point needs to be modified although you are free to
	 * and certainly are encouraged to read through it to understand how the
	 * game works.
	 */

	/**
	 * Redisplay the game on the console (eventually upgrade to SWING GUI) Set
	 * toString(false) if you want to see without the cover Increase sleep time
	 * to slow animation on uncovering
	 */
	public void show() 
	{
		// Clear the console
		c.clear();
		
		// true-cover
		// false-uncover
		c.println(this.toString(true)); // Switch to false
		try 
		{
			Thread.sleep(100);
		} 
		catch (InterruptedException e) 
		{
		}
	}

	/**
	 * Get users choice of spot
	 */
	public void takeTurn() {
		// Prompt the user to take a turn
		c.println("Pick a square: rowNum colNum");
		this.uncoverSquare(c.readInt(), c.readInt());
	}

	/**
	 * Convert the board to a string for display withCover = true to hide board
	 * yet uncovered withCover = false to see whole board (debugging)
	 */
	public String toString(boolean withCover) {
		String result = "";

		// Header
		result += "   ";
		for (int j = 0; j < numCols; j++) 
		{
			result += j + " ";
		}
		result += "\n--";
		for (int j = 0; j < numCols; j++) 
		{
			result += "--";
		}
		result += "\n";

		// Data + Row header
		for (int i = 0; i < numRows; i++) 
		{
			// Row Header
			result += i + "| ";

			// Data
			for (int j = 0; j < numCols; j++) 
			{
				if (withCover && (covered[i][j] == 'C')
						|| (covered[i][j] == 'F')) {
					result += ". ";
				} else {
					result += convert2Symbol(grid[i][j]) + " ";
				}
			}
			result += "\n";
		}
		
		if(boom)
		{
			result += "BOOM!! YOU LOSE!! NOOBIE!";
		}
		return result;
	}

	/**
	 * Utility function
	 * 
	 * @param i
	 * @return
	 */
	private String convert2Symbol(int i) {
		// Convert to symbol
		String symbol = " ";
		if (i == 9) 
		{
			symbol = "X";
		} 
		else if (i > 0) 
		{
			symbol = "" + i;
		}
		return symbol;
	}

}
