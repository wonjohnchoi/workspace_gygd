import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

/**
 * Soruce Code Compression algorithm (SCC) to preprocess source codes.
 * It can both compress and decompress the given source code based on its frequently-used words.
 * This is created for my project for Google Global Science Fair 2011. 
 * I am planning on making a handy User Interface and guides later.
 * 
 * @author Wonjohn Choi
 * @date 2011-04-04
 * 
 */
public class SourceCodeCompression {
	/*
	 * main
	 */
	public static void main(String args[]) {
		test();
	}

	/*
	 * a test case to compresser and decompress some Java files"
	 */
	public static void test() {
		SourceCodeCompression.readFrequentWords("java");
		
		SourceCodeCompression.writeCompressedFile("HelloWorld.java", "HelloWorld.javazip");
		SourceCodeCompression.writeDecompressedFile("HelloWorld.javazip", "Helloworld.javazip.java");
		
		SourceCodeCompression.writeCompressedFile("Scanner.java", "Scanner.javazip");
        SourceCodeCompression.writeDecompressedFile("Scanner.javazip", "Scanner.javazip.java");
	}
	
	private static Vector<String> words;

	/**
	 * read a file that contains frequently used words (mostly reserved words)
	 * for the given source code's language
	 */
	public static void readFrequentWords(String lang) {
		Scanner in = null;
		String name = String.format("frequently-used-words-%s.txt", lang);

		try {
			in = new Scanner(new FileReader(name));
		} catch (FileNotFoundException e) {
			System.out.printf("Cannot access %s!\n", name);
			System.exit(0);
		}

		words = new Vector<String>();
		while (in.hasNextLine()) {
			words.add(in.nextLine());
		}

		in.close();
	}

	/**
	 * read the content of the given input file
	 */
	private static String readFile(String from) {
		Scanner in = null;
		String originalContents = "";

		try {
			in = new Scanner(new FileReader(from));
		} catch (IOException e) {
			System.out.printf("Cannot access %s!\n", from);
			System.exit(0);
		}

		while (in.hasNextLine()) {
			originalContents += in.nextLine() + "\n";
		}

		in.close();

		return originalContents;
	}

	/**
	 * Compress the given source code
	 * 
	 * @return compressed data
	 */
	private static String getCompressedContents(String originalContents) {
		for (int i = words.size() - 1; i >= 0; i--) {
			// replace frequently-used words with shorter characters.
			originalContents = originalContents.replace(words.get(i), ""+ (char) (0) + (char) (i+(i>=13?1:0)));
		}

		return originalContents;
	}
	

	/**
	 * Write the compressed source code to file
	 */
	public static void writeCompressedFile(String from, String to) {
		PrintWriter out = null;

		try {
			out = new PrintWriter(new FileWriter(to));
		} catch (IOException e) {
		    System.out.printf("Cannot access %s!\n" + to);
		    e.printStackTrace();
			System.exit(0);
		}
		out.print(getCompressedContents(readFile(from)));
		out.close();
	}

	/**
	 * Decompress the given compressed data
	 * 
	 * @return decompressed data
	 */
	private static String getDecompressedContents(String originalContents) {

		for (int i = 0; i < words.size(); i++) {
			// replace shorter characters with the original frequently-used
			// words.
			originalContents = originalContents.replace(""+ (char) (0) + (char) (i+(i>=13?1:0)), words.get(i));
		}

		return originalContents;
	}

	/**
	 * Write the decompressed data to file
	 */
	public static void writeDecompressedFile(String from, String to) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(to));
		} catch (IOException e) {
			System.out.printf("Cannot access %s!\n" + to);
			e.printStackTrace();
			System.exit(0);
		}
		
		out.print(getDecompressedContents(readFile(from)));
		out.close();
	}
}