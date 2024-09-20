package core.helpers.framework.general;

import java.io.File;

public class FileHelper {

    public static void deleteDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }
        directory.delete();
    }
}
