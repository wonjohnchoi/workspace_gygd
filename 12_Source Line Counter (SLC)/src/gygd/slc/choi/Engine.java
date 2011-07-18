package gygd.slc.choi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A source line counter class Given a set of file extensions, it counts all
 * lines with the specified files
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

public class Engine{
	ArrayList<String> extensions;
	
	/**
	 * constructor
	 * @param ext extensions given by user
	 */
	public Engine(ArrayList<String> ext){
		extensions = ext;
	}
	
	/**
	 * constructor
	 */
	public Engine(){
		extensions = new ArrayList<String>();
	}
	
	/**
	 * add extra extension 
	 * @param str extra extension
	 */
	public void addExtension(String str){
		extensions.add(str);
	}
	
	/**
	 * remove an extension
	 * @param str the extension to be removed
	 * @return whether it's successfully removed
	 */
	public boolean removeExtension(String str){
		int idx = extensions.indexOf(str);
		if(idx!=-1){
			extensions.remove(idx);
		}
		
		return idx!=-1;
	}
	
	/**
	 * count all lines and blanks in a file
	 */
    public int[] numFileLines(File f) {
    	boolean isSourceFile = false;
    	String fileName = f.getName().toLowerCase();
    	
    	//check if this file is a source file by checking the extension
    	for(String ext: extensions){
    		if(fileName.endsWith(ext.toLowerCase())){
    			isSourceFile = true;
    		}
    	}
    	
    	//if it's a source file
    	if(isSourceFile){
    		//create a Scanner to read the file
    		Scanner sc = null;
            try {
                sc = new Scanner(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
            //variables to store # of codes and blanks
            int nCode=0;
            int nBlank=0;
            
            //if there is more line to read,
            while(sc.hasNextLine()){
            	//check whether the line is empty
                if(sc.nextLine().trim().isEmpty()){
                    nBlank++;
                }else{
                	nCode++;
                }
            }
            
            //return the # of codes and blanks
            return new int[]{nCode, nBlank};
    	}
    	
    	//return 0 line and 0 blanks if the file is not source file
        return new int[]{0,0};
        
    }
    
    /**
     * recursively find all lines in a directory
     */
    public int[] numDirectoryLines(File d){
        File files[]=d.listFiles(); //get all files in a directory
        
        int nCode= 0;
        int nBlank=0;
        
        //for each file
        for(File file:files){
            int nLine[]=numLines(file); //get lines using numLines method
            nCode +=nLine[0];  
            nBlank+=nLine[1];
        }
        
        //return # of codes and blanks
        return new int[]{nCode,nBlank};
    }

    
    /**
     * recursively find all lines in the given address
     */
    public int[] numLines(File f){
        if(f.isFile()){ //if given file is a file
            return numFileLines(f);
        }else if(f.isDirectory()){//if given file is a directory
            return numDirectoryLines(f);
        }
        
        //if it's neither file nor directory
        return new int[]{0,0};
    }
    
    /**
     * main method
     * @param args
     */
    public static void main(String args[]){
    	Engine slc = new Engine();
    	slc.addExtension(".java");
    	
    	Scanner sc=new Scanner(System.in);
    	
    	//flag to decide whether to get more inputs
	    boolean again = true;
	    
    	try{
    		//while wanted to search again,
	    	while(again){
	    		//prompt
			    System.out.println("Put directory or file name to search for java or python source codes.");
			    
			    //create a file with given directory
			    File file = new File(sc.nextLine());
			    
			    //get code lines
			    int[] nLines = slc.numLines(file);
			    
			    //output
			    System.out.printf("Code Lines: %d\nBlank Lines: %d\n", nLines[0], nLines[1]);
			    
			    //prompt for more try
			    System.out.println("Again? (y/n)");
			    again = (sc.nextLine().charAt(0)=='y'?true:false);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}

	}
}
