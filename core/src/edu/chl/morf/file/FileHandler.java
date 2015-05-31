package edu.chl.morf.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Lage on 2015-05-31.
 */
public interface FileHandler {
    public void save();
    public void load();
    public void write(PrintWriter printWriter) throws FileNotFoundException;
    public void read(BufferedReader bufferedReader) throws IOException;
    public String getFilePath();
}
