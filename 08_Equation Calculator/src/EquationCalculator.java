import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 * GUI stuff
 * @author Wonjohn Choi
 *
 */
public class EquationCalculator extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2018702747993072446L;
	JTextField formula, postfix, result;
	JButton evaluate, clear;
	
	/**
	 * constructor: set up GUI
	 */
	public EquationCalculator(){
		super("Equation Calculator developed by Wonjohn Choi");
		
		//general 
		setLayout(new GridLayout(4, 2)); //4*2 layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kill app. when exit
		
		//make labels
		JLabel formulaLabel = new JLabel("Formula:");
		JLabel postfixLabel = new JLabel("Postfix Equation:");
		JLabel resultLabel = new JLabel("Result (round off to 9th):");
		
		//instantiate text fields
		formula = new JTextField();
		postfix = new JTextField();
		result = new JTextField();
		
		postfix.setEditable(false);
		result.setEditable(false);
		
		//instantiate buttons
		evaluate = new JButton("Evaluate");
		clear = new JButton("Clear");
		
		evaluate.addActionListener(this);
		clear.addActionListener(this);
		
		//set fonts
		formulaLabel.setFont(new Font("Arial", 0, 12));
		postfixLabel.setFont(new Font("Arial", 0, 12));
		resultLabel.setFont(new Font("Arial", 0, 12));
		formula.setFont(new Font("Arial", 0, 12));
		postfix.setFont(new Font("Arial", 0, 12));
		result.setFont(new Font("Arial",0,12));

		
		//add labels, text fields and buttons to the frame
		add(formulaLabel);
		add(formula);
		add(postfixLabel);
		add(postfix);
		add(resultLabel);
		add(result);
		add(evaluate);
		add(clear);
		
		//general
		setSize(600, 150);
		setVisible(true);
		setResizable(false);
	}
	
	protected PostfixCalculator pf=new PostfixCalculator();
	protected InfixEquation ifx=new InfixEquation();
	/**
	 * when buttons are clicked,
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//clear button is clicked,
		if(clear == e.getSource()){
			formula.setText("");
			result.setText("");
			
			//evaluate button is clicked,
		}else if(evaluate == e.getSource()){
			onEval();
		}
		
	}
	
	/**
	 * evaluate the equation and show it
	 * When evaluating, use PostfixCalculator and InfixEquation classes
	 */
	public void onEval(){
		//if nothing is given,
		if(formula.getText().trim().isEmpty()){
			result.setText("");
			postfix.setText("");
		}else{			
			//if the process of getting answer makes error,
			//catch and tells user that it's not supported
			try{
				ifx.addInput(formula.getText());
				pf.addInput(ifx.toPostfix());
				
				//set text for postfix equation
				postfix.setText(pf.toString());
				
				//evaluate the postfix formula and round off to the second digit
				result.setText(String.format("%.9f",pf.evaluate()));
			}catch(Exception ex){
				result.setText("Invalid Equation [Supported Symbols: +, -, *, /, ^, (, )]");
				postfix.setText("");
			}
		}
	}
	
	public static void main(String args[]){
		new EquationCalculator();
	}
	
}
