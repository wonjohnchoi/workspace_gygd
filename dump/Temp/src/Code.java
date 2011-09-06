
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * @author Wonjohn Choi
 */
public class Code{
	/**
	 * find all lines in a file
	 */
    public static int[] numFileLines(File f) {
        if(f.getName().toLowerCase().endsWith(".java") ||f.getName().toLowerCase().endsWith(".py")){
            Scanner sc = null;
            try {
                sc = new Scanner(new FileReader(f));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            int nCode=0;
            int nBlank=0;
            
            while(sc.hasNextLine()){
                if(sc.nextLine().trim().isEmpty()){
                    nBlank++;
                }else{
                	nCode++;
                }
            }
            
            return new int[]{nCode, nBlank};
        }

        return new int[]{0,0};
        
    }
    
    /**
     * recursively find all lines in a directory
     */
    public static int[] numDirectoryLines(File d){
        File files[]=d.listFiles();
        
        int nCode= 0;
        int nBlank=0;
        for(File file:files){
            int nLine[]=numLines(file);
            nCode +=nLine[0];
            nBlank+=nLine[1];
        }
        return new int[]{nCode,nBlank};
    }

    
    /**
     * recursively find all lines in the given address
     */
    public static int[] numLines(File f){
        if(f.isFile()){ //if given file is a file
            return numFileLines(f);
        }else if(f.isDirectory()){//if given file is a directory
            return numDirectoryLines(f);
        }
        return new int[]{0,0};
        
    }
    
    /**
     * main method
     * @param args
     */
    public static void main(String args[]){
    	Scanner sc=new Scanner(System.in);
	    boolean again = true;
	    
    	try{
	    	while(again){
			    System.out.println("Put directory or file name to search for java or python source codes.");
			    File file = new File(sc.nextLine());
			    int[] nLines = numLines(file);
			    System.out.printf("Code Lines: %d\nBlank Lines: %d\n", nLines[0], nLines[1]);
			    System.out.println("Again? (y/n)");
			    again = (sc.nextLine().charAt(0)=='y'?true:false);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	sc.next();
    	
    	
	    
	}
}