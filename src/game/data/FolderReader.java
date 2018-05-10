package game.data;

import java.io.File;

/**
 * Created by Matthew Hong on 5/10/2018.
 */
public class FolderReader {
    public FolderReader(){

    }
    public String[] getFileNames(String path) {
        File[] files = new File(path).listFiles();
        String[] fileNames = new String[files.length];

//If this pathname does not denote a directory, then listFiles() returns null.
        for (int i = 0; i < fileNames.length; i++) {
            if (files[i].isFile()) {
                fileNames[i] = path + (files[i].getName());
            }
        }
        return fileNames;
    }
}
