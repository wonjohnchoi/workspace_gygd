import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * a class used to force user to choose an option
 * @author Wonjohn Choi
 *
 */
public class OptionChooser extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = -1188823756788055263L;
	
	JButton buttons[];
	
	int buttonClicked;

	/**
	 * constructor
	 */
	public OptionChooser()
	{
		super("Choose one of the options");
		JPanel myPanel = new JPanel();

		// Layout type (in grid format)
		myPanel.setLayout(new GridLayout(1, 3));

		buttons = new JButton[3];
		
		// naming the buttons and adding the image
		buttons[0] = new JButton("Get Supplies", new ImageIcon("img/Sample Supply2.gif"));
		buttons[1] = new JButton("Hire A Hunter", new ImageIcon("img/hunter-clipart.gif"));
		buttons[2] = new JButton("Roll Dice", new ImageIcon("img/dice.gif"));

		for(int i=0;i<3;i++){
			buttons[i].addActionListener(this);
			myPanel.add(buttons[i]);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 150);
		add(myPanel);
		setResizable(false);
		setAlwaysOnTop(true);		
	}

	/**
	 * when clicked
	 */
	public void actionPerformed(ActionEvent e)
	{
		for(int i=0;i<3;i++){
			if(buttons[i]==e.getSource()){
				buttonClicked = i;
				setVisible(false);
			}
		}
	}

	/**
	 * when clicking a button
	 * @return
	 */
	public int getButtonClicked()
	{
		setVisible(true);
		buttonClicked = -1;
		
		// Wait until they have selected something
		while (buttonClicked == -1)
		{ 
		}
		setVisible(false);
		
		return buttonClicked;
	}

}