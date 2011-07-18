import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;


public class Sort {
	public static void main(String args[]) throws IOException{
		Scanner in  = new Scanner(new FileReader("frequently-used-words-java.txt"));
		Vector<String> data = new Vector<String>();
		
		while(in.hasNext()){
			data.add(in.next());
		}
		
		in.close();
		
		Collections.sort(data);
		
		PrintWriter out = new PrintWriter(new FileWriter("frequently-used-words-java.txt"));
		for(String d: data){
			out.println(d);
		}
		out.close();
		
		
	}
}
