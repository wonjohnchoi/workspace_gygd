package gygd.go.choi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


public class GoGUI extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -16783961817955017L;

	
	protected JTextArea log; //log screen 
	protected JScrollPane logScrollBar;
	protected JTextArea playerInfo[]; //player information screen
	protected GoPlayer players[]; //objects of players
	
	//TOP BAR
	protected JButton startButton;
	protected JLabel currentPlayerName;
	
		
	public GoGUI() {
		
		//layout
		setLayout(new BorderLayout());
	}
	
	/**
	 * manage the top bar
	 */
	public void initTopBar(){
		setVisible(false);
		
		//NORTH part controlled by a panel
		JPanel panel = new JPanel(new FlowLayout());
		
		currentPlayerName = new JLabel("Current Player: Black Stone");
		
		startButton = new JButton("Start Turn");
		startButton.addActionListener(this);
		
		
		panel.add(currentPlayerName);
		panel.add(startButton);
		
		
		add(panel, BorderLayout.NORTH);
		
		setVisible(true);
	}
	
	protected boolean startTurn;
	
	/**
	 * action to be done for a new turn
	 * @param player
	 */
	public void changeTurn(GoPlayer player){
		//change label and reset button for start
		currentPlayerName.setText("Current Player: Black Stone");
		startButton.setEnabled(true);
		startTurn = false;
	
		
		while(!startTurn){
			
		}
	}
	
	/**
	 * when action happens... (by mouse)
	 */
	public void actionPerformed(ActionEvent e) {
		if(startButton == e.getSource()){
			startTurn = true;
			
			startButton.setEnabled(false);
	
		}
		
	}

	public void initMapLog(GoBoard gBoard){
		setVisible(false);
		
		//CENTER part controlled by a panel
		JPanel panel = new JPanel(new BorderLayout());
		//CENTER: West: map
		panel.add(gBoard, BorderLayout.WEST);
		//CENTER: East: log
		log = new JTextArea("*************************LOG SCREEN*************************");
		log.setBorder(new LineBorder(Color.getHSBColor(0, 0, 0.5f)));
		logScrollBar = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(logScrollBar, BorderLayout.CENTER);
		//add the panel to the frame
		add(panel, BorderLayout.CENTER);
		
		
		setVisible(true);
	}
	
	public void initPlayerInfo(GoPlayer[] newPlayers){
		setVisible(true);
		
		//initializes, instantiates, ...
		players = newPlayers;
		playerInfo = new JTextArea[players.length];
		
		//South Part controlled by a panel
		JPanel infoScreen = new JPanel(new GridLayout(1,3));
		//basic settings of text areas
		for(int i=0;i<playerInfo.length;i++){
			playerInfo[i] = new JTextArea(players[i].toString()+"\n\n");
			
			playerInfo[i].setEditable(false);
			playerInfo[i].setBorder(new LineBorder(Color.getHSBColor(0, 0, 0.5f)));
			infoScreen.add(new JScrollPane(playerInfo[i], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
			//infoScreen.add(playerInfo[i]);
		}
		//add the panel to the frame
		add(infoScreen, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	/**
	 * prompt screen to ask # of players playing
	 * It covers every case
	 * @return
	 */
	public int promptNumPlayer(){
		int n = 0;
		boolean hasProblem = false;
		
		//cover the wrong-type input
		try{
			writeLog("How many players are playing? (*Should be 2 or 3)");
			String option = JOptionPane.showInputDialog(this, "How many players are playing? (*Should be 2 or 3)", "Question", JOptionPane.QUESTION_MESSAGE);			
			//for cancel option
			if(option == null){
				writeLog("Closing Program...");
				System.exit(0);
			}
			n = Integer.parseInt(option.trim());
		}catch(Exception e){
			hasProblem = true;
		}
		
		//cover wrong range input
		if(n !=2 && n!=3) {
			hasProblem = true;
		}
		
		if(!hasProblem){
			return n;
		}
		else{
			writeLog("Wrong input: Input must be 2 or 3.");
			JOptionPane.showMessageDialog(this, "Wrong input: Input must be 2 or 3.");
			
			return promptNumPlayer();
		} 
	}
	

	/**
	 * add new logs
	 * @param message
	 */
	public void writeLog(String message){
		log.append("\n"+message);
	}	
	
	/**
	 * update player's information screen
	 */
	public void updatePlayerInfo(){
		//setVisible(false);
		for(int i=0;i<playerInfo.length;i++){
			playerInfo[i].setText(players[i].toString()+"\n\n");
		}
		//setVisible(true);
	}
	
	/**
	 * update map
	 */
	public void updateMap(){
		
	}

	 /**
     * main test method
     * @param args
     */
    public static void main(String args[]){
    	JFrame test = new JFrame();
    	GoGUI gui = new GoGUI();
    	test.add(gui);
    	test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	test.setVisible(true);
    }
	
}
