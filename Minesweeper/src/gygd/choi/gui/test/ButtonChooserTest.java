package gygd.choi.gui.test;

import gygd.choi.gui.ButtonChooser;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * test program for ButtonChooser
 * @author Wonjohn Choi
 *
 */
public class ButtonChooserTest {
	public static void main(String args[]){
		JButton[][] buttons = new JButton[][]{
			{new JButton("Hard"),new JButton("Medium")},
			{new JButton("Easy"),new JButton("User Setting")}
		};
				
		ButtonChooser bc = new ButtonChooser(buttons, 2, new Dimension(300,300));
		Thread chooser = new Thread(bc);
		chooser.start();
		
		synchronized(bc.out){
			try {
				bc.out.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		for(JButton chosen: bc.out){
			System.out.println(chosen.getText());
		}
				
	}
}
