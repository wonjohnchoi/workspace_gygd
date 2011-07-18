import java.io.File;


/**
 * A simple class to apply Source Code Compression algorithm to a directory
 * It uses recursive approach.
 * @author Wonjohn Choi
 * @dependency SourceCodeCompression.java
 *
 */
public class SCCUtility {
    public static void main(String args[]){
        //compress(new File("java\\util"));
        //decompress(new File("java\\util"));
    }
        
    /**
     * Compress with SourceCodeCompression algorithm to a directory (recursively) or a file
     * @param file a directory or file
     */
    public static void compress(File file){
        if(file.isDirectory()){
            //recursively search for sub-directories or files
            for(File f: file.listFiles()){
                compress(f);
            }
        }else{
            SourceCodeCompression.writeCompressedFile(file.getPath(), file.getPath()+"zip");
        }
    }
    
    /**
     * Decompress with SourceCodeCompression algorithm to a directory (recursively) or a file
     * @param file a directory or file
     */
    public static void decompress(File file){
        if(file.isDirectory()){
            //recursively search for sub-directories or files
            for(File f: file.listFiles()){
                decompress(f);
            }
        }else{
            SourceCodeCompression.writeDecompressedFile(file.getPath(), file.getPath()+".java");
        }
    }
}
