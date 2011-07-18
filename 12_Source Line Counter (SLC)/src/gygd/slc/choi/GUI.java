package gygd.slc.choi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * 
 * Class to create GUI for SLC
 * 
 * Copyright (C) 2010 Wonjohn Choi This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
public class GUI {
	protected JFrame window = new JFrame("Source Line Counter (SLC) by Wonjohn Choi");
	protected Engine engine = new Engine();
	protected Extension extensions = new Extension(); //create a small gui class to manage extension
	protected FileChooser fc = new FileChooser();
	
	
	/**
	 * constructor
	 */
	public GUI(){
		
		window.setLayout(new BorderLayout());
		window.add(extensions, BorderLayout.CENTER); //add it to window
		window.add(new FileChooser(), BorderLayout.EAST);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		
		
	}
	
	public static void main(String args[]){
		new GUI();
	}
	
	
	
	/**
	 * a class to find a file
	 * @author Wonjohn Choi
	 *
	 */
	private class FileChooser extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1530967741019300150L;
		protected JButton btnOpen = new JButton("Choose file or folder");
		protected JTextField tf = new JTextField(25);  //text field to store the directory
		protected JTextArea output = new JTextArea(); //text area to show results
		
		/**
		 * constructor
		 */
		public FileChooser(){
			JPanel chooser = new JPanel();
			chooser.setLayout(new FlowLayout());
			chooser.add(tf);
			btnOpen.addActionListener(this);
			chooser.add(btnOpen);
			
			setLayout(new BorderLayout());
			add(chooser, BorderLayout.NORTH);
			add(output, BorderLayout.CENTER);
			
		}

		
		/**
		 * when a button is clicked,
		 * @Override
		 */
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			
			//if it is open button
			if(btnOpen == btn){
				String curDir = tf.getText().trim();
				if(curDir.isEmpty()){
					curDir = ".";
				}
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setCurrentDirectory(new File(curDir));
				
				//filePaths.clear();
				int [] numLines = new int[2];
				if(fc.showOpenDialog(window)==JFileChooser.APPROVE_OPTION){
					for(File f: fc.getSelectedFiles()){
						int [] counter = engine.numLines(f);
						numLines[0] += counter[0];
						numLines[1] += counter[1];
					}
					
					tf.setText(fc.getCurrentDirectory().toString());
					
					output.setText(String.format("# of codes: %d\n# of blank lines: %d", numLines[0], numLines[1]));
					
				}
			}
			
		}
	}
	
	/**
	 * class to manage extensions gui
	 * @author Wonjohn Choi
	 *
	 */
	private class Extension extends JPanel implements ActionListener, KeyListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8683584682066973573L;
		protected JButton btnAdd = new JButton("Add"); //click when adding new extension
		protected JButton btnAbout = new JButton("About");
		protected JTextField tfAdd = new JTextField(); //text area to type extension
		
		/**
		 * constructor
		 */
		public Extension(){
			setLayout(new GridLayout(0,1)); //set layout (infinitely extendible veritcally due to 0)
			btnAdd.addActionListener(this); //add actionlistenr
			btnAbout.addActionListener(this);
			btnAbout.setBorder(new LineBorder(Color.red));
			add(btnAbout);
			add(btnAdd); //add a button
			add(tfAdd); //add a textfield
			window.addKeyListener(this);//add keylistener to window (frame)
			window.pack(); //resize
			
			
			
			//add samples
			tfAdd.setText(".txt");
			onAdd();
			tfAdd.setText(".java");
			onAdd();
			tfAdd.setText(".cpp");
			onAdd();
			tfAdd.setText(".python");
			onAdd();
			tfAdd.setText("");	
		}
		
		/**
		 * when clicked by mouse
		 * @Override
		 */
		public void actionPerformed(ActionEvent e) {
			//if it's add extension button,
			if(btnAdd==e.getSource()){
				onAdd();
			}else if(btnAbout == e.getSource()){
				JOptionPane.showMessageDialog(window, "Source Line Counter (SLC) by Wonjohn Choi is developed to count # of source code lines inside chosen files with given extensions.\n" +
						"Using \'Add\' button, user can add file extensions to be searched. By clicking the chosen extensions, user can remove them\n" +
						"Using \'Open file or folder\' button, user can select files and folders.\n" +
						"When a folder is chosen, each and every file inside the folder is searched recursively.", "About", JOptionPane.INFORMATION_MESSAGE);
			}else{
				//get name of inputed button
				JButton ext = (JButton)e.getSource();
				//remove name from list
				engine.removeExtension(ext.getText());
				//remove the button
				remove(ext);
				//resize
				window.pack();
			}
			
		}
		
		/**
		 * when adding a button based on input text
		 */
		public void onAdd(){
			//get text
			String ext = tfAdd.getText().trim(); 
			//for no input,
			if(ext.isEmpty()){
				
				//for repeated button
			}else if(engine.extensions.indexOf(ext)!=-1){
				
			}else{
				//set new layout
				//setLayout(new GridLayout(2+extensions.size(),1));
				//make new button
				JButton btn = new JButton(ext);
				//add actionlistener
				btn.addActionListener(this);
				//add new button
				add(btn);
				//add the name to the list
				engine.addExtension(ext); 
				
			}
			
			tfAdd.setText("");
			
			//resize
			window.pack();
		}

		/**
		 * @Override
		 * when a key is pressed,
		 */
		public void keyPressed(KeyEvent e) {
			System.out.println("D");
			switch(e.getKeyCode()){
			case KeyEvent.VK_ENTER:
				onAdd();
				break;
			}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
