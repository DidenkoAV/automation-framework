package core.helpers.framework.general;

import java.io.File;

public class FileHelper {

    public static void deleteDirectory(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            System.out.println("Provided directory is null, does not exist, or is not a directory.");
            return;
        }

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.out.println("Failed to delete file: " + file.getAbsolutePath());
                }
            }
        }

        boolean deleted = directory.delete();
        if (!deleted) {
            System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
        }
    }
}
