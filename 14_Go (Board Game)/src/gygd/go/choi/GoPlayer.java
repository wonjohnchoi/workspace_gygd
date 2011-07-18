package gygd.go.choi;

import java.awt.Color;

public class GoPlayer {
	int capturedStones = 0;
	Color stoneColor;
	String name;
	
	public GoPlayer(Color col){
		stoneColor = col;
		if(col==Color.BLACK){
			name="Black Stone";
		}else{
			name="White Stone";
		}
	}
	
	public String toString(){
		return name+"\nCaptured: 0";
	}

}
