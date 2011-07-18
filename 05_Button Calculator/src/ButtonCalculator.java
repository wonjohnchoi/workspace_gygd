import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * Calculator!
 * Simple Calculator class that offers user really simple calculator 
 * 7 8 9  +  - * / 
 * 4 5 6  sin cos asin acos
 * 1 2 3  tan atan sqrt cbrt  
 * 0 CR = pie  e   log10  ^ 
 * @author Wonjohn Choi
 *
 */
public class ButtonCalculator extends JFrame implements ActionListener{
	protected JTextField output;
	protected JButton buttons[][];
	
	/**
	 * constructor
	 */
	public ButtonCalculator(){
		super("Button Calculator developed by Wonjohn Choi in G.Y.G.D.");
		initGUI(); //set up GUI stuff
		initLogic(); //set up Logic stuff
	}
	
	/**
	 * Set up GUI stuff
	 */
	public void initGUI(){
		//add output screen
		output = new JTextField("Rounded to the 9th decimal place");
		output.setEditable(false);
		add(output, "North");
		
		//store button names
		String name[][]={
				"7 8 9 + - * /".split(" "),
				"4 5 6 sin cos asin acos".split(" "),
				"1 2 3 tan atan sqrt cbrt".split(" "),
				"0 . = CR PI E ^".split(" ")
		};
		
		//panel that holds buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(name.length, name[0].length));
		
		//add buttons using the names stored
		buttons = new JButton[name.length][name[0].length];
		for(int row=0;row<buttons.length;row++){
			for(int col=0;col<buttons[0].length;col++){
				buttons[row][col] = new JButton(name[row][col]); //instantiate
				buttons[row][col].addActionListener(this); //add listener to the click of mouse
				buttons[row][col].setBorder(new LineBorder(Color.getHSBColor(0, 0, 0.8f))); //set color of borders
				buttonPanel.add(buttons[row][col]); //add to the panel
			}
		}
		
		//add the panel to the frame
		add(buttonPanel, "Center");
		
		//set options of frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 200);
		setResizable(false);
		setVisible(true);
		
	}
	
	////////////////////////////////////SET UP LOGIC//////////////////////////////////////////////
	//protected final double EMPTY = -0.00001; //unique value that cannot happen in this calculator
	protected double curValue = 0;
	protected final String[] values = "0 1 2 3 4 5 6 7 8 9 PI E".split(" ");
	protected final String[] functions = "sin cos tan asin acos atan sqrt cbrt log10".split(" ");
	
	public void initLogic(){
		Arrays.sort(values);
		//Arrays.sort(operators);
		Arrays.sort(functions);
	}
	
	protected String oper = "=";
	protected boolean isStart = true;
	
	/**
	 * when clicked by a mouse
	 */
	public void actionPerformed(ActionEvent e) {
		String input = (String) e.getActionCommand();
		
		//when the input is a dot (.)
		if(input.equals(".")){
			if(!output.getText().contains(".")){
				if(isStart){
					output.setText("0.");
					isStart=false;
				}else{
					output.setText(output.getText()+".");
				}
			}
			//when the input is a constant "PI"
		} else if(input.equals("PI")){
			output.setText(""+Math.PI);
			isStart = false;
			
			//when the input is a constant "E"
		} else if(input.equals("E")){
			output.setText(""+Math.E);
			isStart = false;
			
			//when the input is clear
		} else if(input.equals("CR")){
			curValue = 0;
			output.setText("");
			oper = "=";
			isStart = true;
			
			//when the input is 0~9
		} else if(Arrays.binarySearch(values, input)>=0){
			if(isStart){
				output.setText(input);
			} else{
				output.setText(output.getText()+ input);
			}
			isStart = false;
		
			//when the input is function
		} else if(Arrays.binarySearch(functions, input)>=0){
			computeFunc(input);
			isStart = true;
		
			//when the input is operator
		}else {
			if (isStart){
				if (input.equals("-") && output.equals("")) {
					output.setText(input);
					isStart = false;
				} else
					oper = input;
			} else {
				computeOper(Double.parseDouble(output.getText()));
				oper = input;
				isStart = true;
			}
		}
		
	
		
	}
	
	/**
	 * compute the operators
	 */
	private void computeOper(double d) {
		if (oper.equals("+"))
			curValue += d;
		else if (oper.equals("-"))
			curValue -= d;
		else if (oper.equals("*"))
			curValue *= d;
		else if (oper.equals("/"))
			curValue /= d;
		else if (oper.equals("="))
			curValue = d;
		else if (oper.equals("^"))
			curValue = Math.pow(curValue, d);
		else
			System.err.println("Non-existence Operator Error");
		output.setText(String.format("%.9f", curValue));
		
	}

	/**
	 * compute the functions
	 * @param input the function
	 */
	private void computeFunc(String input) {
		//sin cos tan asin acos atan sqrt cbrt log10
		curValue = Double.parseDouble(output.getText());
		if(input.equals("sin")){
			curValue = Math.sin(Math.toRadians(curValue));
		}else if(input.equals("cos")){
			curValue = Math.cos(Math.toRadians(curValue));
		}else if(input.equals("tan")){
			curValue = Math.tan(Math.toRadians(curValue));
		}else if(input.equals("asin")){
			curValue = Math.toDegrees(Math.asin(curValue));
		}else if(input.equals("acos")){
			curValue = Math.toDegrees(Math.acos(curValue));
		}else if(input.equals("atan")){
			curValue = Math.toDegrees(Math.atan(curValue));
		}else if(input.equals("sqrt")){
			curValue = Math.sqrt(curValue);
		}else if(input.equals("cbrt")){
			curValue = Math.cbrt(curValue);
		}else if(input.equals("log10")){
			curValue = Math.log10(curValue);
		}else{
			System.err.println("Non-existence Function Error");
		}
		
		output.setText(String.format("%.9f", curValue));
	}

	/**
	 * main method
	 * @param args
	 */
	public static void main(String args[]){
		new ButtonCalculator();
	}
 
}
