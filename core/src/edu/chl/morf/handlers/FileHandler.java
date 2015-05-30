package edu.chl.morf.handlers;

import java.io.*;

/**
 * An abstract class for reading from and writing to text files.
 * The class uses the user home directory as the directory for the text files.
 * This makes it possible to store the text files in a known place on different computers and operating systems.
 *
 * Created by Lage on 2015-05-17.
 */
public abstract class FileHandler {

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public void save(){
        try{
            printWriter = new PrintWriter(System.getProperty("user.home") + getFilePath());
            this.write(printWriter);

        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        printWriter.close();
    }

    public void load(){
        try{
            //Trys to read file
            bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.home") + getFilePath()));
        }catch (FileNotFoundException e){
            //Creates file if file not found
            File f = new File(System.getProperty("user.home") + getFilePath());
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {

            }
        }

        try{
            bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.home") + getFilePath()));
            read(bufferedReader);
            bufferedReader.close();
        }catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    public abstract void write(PrintWriter printWriter) throws FileNotFoundException;

    public abstract void read(BufferedReader bufferedReader) throws IOException;

    public abstract String getFilePath();

}
