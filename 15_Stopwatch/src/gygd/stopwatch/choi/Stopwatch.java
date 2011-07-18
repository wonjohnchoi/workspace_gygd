package gygd.stopwatch.choi;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * 
 * Stopwatch
 * Copyright (C) 2010 Wonjohn Choi
 * Version 2
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class Stopwatch implements ActionListener{
	private SimpleTimer simpleTimer = new SimpleTimer();
	private JFrame window = new JFrame("Stopwatch offered by Wonjohn Choi");
	
	private JButton btnCurrentTime;
	private JButton btnCountDown;
	private JButton btnCountUp;
	
	private JButton btnOnOff;
	private JTextField tfTime;
	
	
	public Stopwatch(){
		iniGUI();
		onBtnCurrentTime();
	}
	
	/*
	 * establish the Graphic Interface 
	 */
	public void iniGUI(){
		window.setLayout(new BorderLayout());
		
		JPanel plButtons = new JPanel();
		plButtons.setLayout(new GridLayout(1,3));
		btnCurrentTime = new JButton("Current Time");
		btnCountDown = new JButton("Count Down");
		btnCountUp = new JButton("Count Up");
		btnCurrentTime.addActionListener(this);
		btnCountDown.addActionListener(this);
		btnCountUp.addActionListener(this);
		//btnCountDown.setBorder(null);
		//btnCountUp.setBorder(null);
		
		plButtons.add(btnCurrentTime);
		plButtons.add(btnCountDown);
		plButtons.add(btnCountUp);
		window.add(plButtons, BorderLayout.NORTH);
		
		JPanel plTime = new JPanel();
		plTime.setLayout(new FlowLayout());
		btnOnOff = new JButton("On");
		btnOnOff.addActionListener(this);
		tfTime = new JTextField(20);
		tfTime.setEditable(false);
		plTime.add(tfTime);
		plTime.add(btnOnOff);
		
		window.add(plTime, BorderLayout.CENTER);
	
		window.setResizable(false);
		window.setSize(400,100);
		window.setAlwaysOnTop(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	
	/*
	 * Method to react to the button clicks
	 * @Override
	 */
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn == btnCurrentTime){
			onBtnCurrentTime();
		}
		else if(btn == btnCountDown){
			onBtnCountDown();
		}else if(btn == btnCountUp){
			onBtnCountUp();
			
		}else if(btn == btnOnOff){
			onBtnOnOff();
		}
	
	}

	private void onBtnCurrentTime() {
		if(!btnOnOff.getText().equals("On")){
			simpleTimer.stop();
			btnOnOff.setText("On");
		}
		
		btnCurrentTime.setEnabled(false);
		btnCountUp.setEnabled(true);
		btnCountDown.setEnabled(true);
		
		tfTime.setEditable(false);
		btnOnOff.setVisible(false);
		btnOnOff.setText("Pause");

		tfTime.setEditable(false);
		
		Calendar calendar = new GregorianCalendar();
	    
		simpleTimer.countUp(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	}

	private void onBtnCountDown() {
		if(!btnOnOff.getText().equals("On")){
			simpleTimer.stop();
			btnOnOff.setText("On");
		}
		
		btnOnOff.setVisible(true);
		
		btnCurrentTime.setEnabled(true);
		btnCountUp.setEnabled(true);
		btnCountDown.setEnabled(false);
		tfTime.setEditable(true);
		tfTime.setText("Put Time in Format \"HR:MIN:SEC\"");
	}

	private void onBtnCountUp() {
		if(!btnOnOff.getText().equals("On")){
			simpleTimer.stop();
			btnOnOff.setText("On");
		}
	
		btnOnOff.setVisible(true);
		
		btnCurrentTime.setEnabled(true);
		btnCountDown.setEnabled(true);
		btnCountUp.setEnabled(false);
		tfTime.setEditable(false);
		tfTime.setText("00:00:00");
	}

	private void onBtnOnOff() {
		if((btnCurrentTime.isEnabled() && btnCountDown.isEnabled() && btnCountUp.isEnabled())){
			tfTime.setText("Choose options");
		}else{
			if(btnOnOff.getText().equals("On")){
				
				if(!btnCurrentTime.isEnabled()){
					//SHOULD NEVER HAPPEN
					System.err.println("Current Time's button shouldn't be clicked!");
					btnOnOff.setText("Pause");
					tfTime.setEditable(false);
					simpleTimer.countUp(Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND);
				}
				else if(!btnCountDown.isEnabled()){
					int HR, MIN, SEC;
					HR=MIN=SEC=-1;
					
					String[] in = tfTime.getText().split(":");
					if(in.length!=3){
						tfTime.setText("WRONG FORMAT ERR.");
					}else{
						try{
							HR = Integer.parseInt(in[0]);
							MIN = Integer.parseInt(in[1]);
							SEC = Integer.parseInt(in[2]);
						}catch(Exception except){}
						
						if(HR<0 || MIN<0 || SEC<0 ||MIN>=60 || SEC>=60){
							tfTime.setText("NUMBER RANGE ERR.");
						}else{
							btnOnOff.setText("Pause");
							tfTime.setEditable(false);
							simpleTimer.countDown(HR, MIN, SEC);
						}
					}
					
				}else{
					simpleTimer.countUp(0,0,0);
					btnOnOff.setText("Pause");
				}
			}else if(btnOnOff.getText().equals("Pause")){
				btnOnOff.setText("Resume");
				simpleTimer.pause();
			}else if(btnOnOff.getText().equals("Resume")){
				btnOnOff.setText("Pause");
				simpleTimer.resume();
				
				//countDown.start();
			}
			
			
		}
	}

	/**
	 * Class to manipulate time (algorithm)
	 * @author Wonjohn Choi
	 *
	 */
	class SimpleTimer{
		private Toolkit toolkit = Toolkit.getDefaultToolkit(); 
		private int HR, MIN, SEC;
		private Timer timer;
		private boolean isPaused;
		
		public void pause(){
			isPaused = true;	
		}
		
		public void resume(){
			isPaused = false;
		}
		
		public void stop(){
			timer.cancel();
		}
		
		public void countDown(int timeH, int timeM, int timeS){
			HR = timeH;
			MIN = timeM;
			SEC = timeS;
			isPaused = false;
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask(){
				public void run() {	
					if(!isPaused){
						tfTime.setText(getTimeString(HR, MIN, SEC));
					
						SEC--;
						if(SEC==-1){
							SEC = 59;
							MIN--;
						}
						
						if(MIN==-1){
							MIN = 59;
							HR--;
						}
						
						if(HR==-1){
							timer.cancel();	
							toolkit.beep();
						}
					}
				}
			}, 0, 1000);
		}
		
		public void countUp(int timeH, int timeM, int timeS) {
			HR=timeH;
			MIN=timeM;
			SEC=timeS;
			isPaused = false;
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask(){
				public void run() {	
					if(!isPaused){
						tfTime.setText(getTimeString(HR, MIN, SEC));
					
						SEC++;
						if(SEC==60){
							SEC = 0;
							MIN++;
						}
						
						if(MIN==60){
							MIN = 0;
							HR++;
						}
					}
				}
			}, 0, 1000);
		
		}

	}
	
	public String getTimeString(int HR, int MIN, int SEC){
		String h = HR+"";
		String m= MIN+"";
		String s=SEC+"";
		
		while(h.length()<=1) h="0"+h;
		while(m.length()<=1) m="0"+m;
		while(s.length()<=1) s="0"+s;
		
		return h+":"+m+":"+s;
		
		
	}
	
	
	/*
	 * main method
	 */
	public static void main(String args[]){
		new Stopwatch();
	}
}

