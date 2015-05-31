package edu.chl.morf.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete implementation of interface IFileLister.
 * Created by Lage on 2015-05-31.
 */
public class FileLister implements IFileLister{

    /*Lists all files from input path folder and returns
      them in alphabetical order.
     */
    @Override
    public List<String> getFileNames(String directory) {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        List<String> files = new ArrayList<String>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                files.add(file.getName());
            }
        }

        Collections.sort(files); //Sort the list alphabetically
        return files;
    }

}
