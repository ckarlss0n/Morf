package edu.chl.morf.file;

import java.util.List;

/**
 * Interface for listing all files in a directory
 * Created by Lage on 2015-05-31.
 */
public interface IFileLister {
    public List<String> getFileNames(String directory);
}
