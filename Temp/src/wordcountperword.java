import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class wordcountperword {
	public static void main(String args[]) throws IOException{
		Scanner sc=new Scanner(new FileReader("Frahenheit.txt"));
		int count=0;
		int wordcount=0;
		
		while(sc.hasNext()){
			wordcount++;
			count+=sc.next().length();
		}
		double average = (double)count/(double)wordcount;
		System.out.println(6000/average);
	}
}
