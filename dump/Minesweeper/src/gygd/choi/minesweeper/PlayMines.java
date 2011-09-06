package gygd.choi.minesweeper;

import java.util.Scanner;
/**
 * ICS4U Final Exam Practical - PlayMines game driver
 * @author Mr. Reid
 * @date 2010/06/17
 * @version 1.1
 */
public class PlayMines {
  /**
   * You should not have to modify anything here (but you can if it helps you)
   */
  public static void main(String[] args) 
  {
    // Get num Rows and Cols
    Scanner sc = new Scanner(System.in);
    System.out.println("Rows Cols and Number of Mines: ");
    
    // Instantiate the game
    Engine m = new Engine(sc.nextInt(), sc.nextInt(), sc.nextInt());
    m.show();
    
    // Loop until the game is over
    while (!m.GameOver())
    {
      // Take a turn on the console
      m.takeTurn();
    }  
  }
}
