/**
 * 
 * @author Wonjohn Choi
 *
 */
public class ConnectFourTest {
	/**
	 * main class to test the game "Connect Four"
	 * @param args
	 */
	public static void main(String[] args) {
		//instantiate to test
		ConnectFourEngine engine = new ConnectFourEngine();
		ConnectFourGUI gui = new ConnectFourGUI();
		new ConnectFourListener(engine, gui);
	}
}
