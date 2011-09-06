package gygd.choi.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * This class was created for the functionality of displaying a set of buttons and allowing the user to choose one
 * This class was made very generally for future reuse
 * This class uses syncronized calls
 * @author Wonjohn Choi
 *
 */
public class ButtonChooser extends JFrame implements Runnable, ActionListener{

	private static final long serialVersionUID = 3247137843935049197L;
	
	protected JButton[][] in; //given buttons
	public JButton[] out; //chosen buttons
	protected int idx; //index needed to fill the 'out' array
	
	/**
	 * constructor
	 * @param buttons
	 * @param re
	 */
	public ButtonChooser(JButton[][] buttons, int n){
		//initialize process
		in = buttons;
		out = new JButton[n]; //n indicates # of expected chosen buttons
		idx = 0;
		
		//set layout
		setLayout(new GridLayout(buttons.length, buttons[0].length));
		
		//for each buttons,
		for(int r=0;r<buttons.length;r++){
			for(int c=0;c<buttons[0].length;c++){
				//if a button does not exit,
				if(buttons[r][c]==null){
					//put a dead button
					buttons[r][c]=new JButton();
					buttons[r][c].setEnabled(false);
				}
				
				//reaction to mouse click
				buttons[r][c].addActionListener(this);
				//add to frame
				add(buttons[r][c]);
			}
		}
		
		//set the best optimized size
		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * second constructor
	 * @param buttons
	 * @param n
	 * @param name
	 */
	public ButtonChooser(JButton[][] buttons, int n, String name){
		this(buttons, n);
		setName(name);
	}
	
	/**
	 * constuctor that sets the size of chooser
	 * @param buttons
	 * @param re
	 * @param dimen
	 */
	public ButtonChooser(JButton[][] buttons, int n, Dimension dimen){
		this(buttons, n); //call standard constructor
		
		//set size of the button chooser
		setSize(dimen);
	}
	
	/**
	 * for thread purpose
	 */
	@Override
	public void run() {
		//start by making it visible
		setVisible(true);
	}

	/**
	 * when clicked by mouse
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton chosen = (JButton) event.getSource(); //get button
		chosen.setEnabled(false); //kill its reaction
		out[idx] = chosen; //store it to the chosen button list
		idx++; 
		
		//if required buttons are chosen,
		if(out.length == idx){
			//invisible frame
			setVisible(false);
			
			//call other thread to work
			try{
				synchronized(out){
					out.notify();
				}
			}catch(Exception exp){
				exp.printStackTrace();
			}
			
		}
		
	}

}
