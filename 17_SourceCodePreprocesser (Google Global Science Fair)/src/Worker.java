import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;


public class Worker {
    public static void main(String args[]) throws Exception{
        SourceCodeCompression.readFrequentWords("java");
        
        File files[] = new File("java").listFiles();
        for(File f: files){
            String name = "java/"+f.getName();
            work(new File(name), "trim");
            work(new File(name), "spacify");
            System.out.println(name);
            double t1 = System.currentTimeMillis();
            work(new File(name), "compress");
            double t2 = System.currentTimeMillis();
            System.out.println(t2-t1);
            work(new File(name), "decompress");
            double t3 = System.currentTimeMillis();
            System.out.println(t3-t2);
        }
        
        /*
       File files[] = new File("java").getAbsoluteFile().listFiles();
       
       String names[] =new File("java").list();
       
       long _zip=0;
       long _7z=0;
       long _zipProcessed=0;
       long _7zProcessed=0;
       
       for(File file: new File("java").listFiles()){
           if(file.getName().contains("_")){
               if(file.getName().endsWith("7z")){
                   _7zProcessed+=getSize(file);
                  // System.out.println(file.getName());
                   
                   
               }else if(file.getName().endsWith("zip")){
                   _zipProcessed+=getSize(file);
               }
           }else{
               if(file.getName().endsWith("7z")){
                   _7z+=getSize(file);
                   System.out.println(getSize(file));
               }else if(file.getName().endsWith("zip")){
                   _zip+=getSize(file);
               }
           }
           
 
       }
       System.out.println("7z preprocess: "+_7zProcessed);
       System.out.println("zip preprocess: "+_zipProcessed);
       System.out.println("7z: "+_7z);
       System.out.println("zip: "+_zip);
       */
    }
    
    
    
    
    public static int getSize(File file){
        int size = 0;
        if(file.isDirectory()){
            for(File f: file.listFiles()){
                size+=getSize(f);
            }
        }else{
            size+=file.length();
        }
        return size;
    }
    
    public static void work(File file, String kind) throws Exception{
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f: files){
                work(f, kind);
            }
        }else{
            if(kind.equals("trim"))trim(file);
            else if(kind.equals("spacify"))spacify(file);
            else if(kind.equals("compress"))compress(file);
            else if(kind.equals("decompress"))decompress(file);
        }
        
        
    }
    
    public static void compress(File file) throws Exception{
        SourceCodeCompression.writeCompressedFile(file.getPath(), file.getPath()+"zip");
        file.delete();
    }
    
    public static void decompress(File file) throws Exception{
        SourceCodeCompression.writeDecompressedFile(file.getPath(), file.getPath()+".java");
        file.delete();
    }
    
    public static void spacify(File file) throws Exception{
        String content  = "";
        Scanner in  = new Scanner(file);
        while(in.hasNextLine()){
            content += in.nextLine()+"\n";
        }
        in.close();
        content=content.replace("\t", "    ");
        PrintWriter out = new PrintWriter(file);
        out.print(content);
        out.close();
    }
    
    
    public static void trim(File file) throws Exception{
      //System.out.println("Size1: "+file.length());
        Scanner in = new Scanner(new FileReader(file.getPath()));
        String contents = "";
        
        boolean start = false;
        
        while(in.hasNextLine()){
            String line = in.nextLine();
            boolean open = line.contains("/*");
            boolean close = line.contains("*/");
            
            if(line.trim().startsWith("//")){
                
            }else if(open && close){
                
            }else if(start){
                if(close)start=false;
            }else{
                if(open){
                    start = true;
                }else{
                    contents += line+"\n";
                }
            }
        }
        in.close();
        PrintWriter out = new PrintWriter(new FileWriter(file.getPath()));
        out.print(contents);
        out.close();
        
        //System.out.println("Size2: "+file.length());
    }
}
