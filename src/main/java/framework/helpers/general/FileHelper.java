package framework.helpers.general;

import framework.helpers.log.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

public class FileHelper {

    private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);


    public static void deleteDirectory(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            logger.error("Provided directory is null, does not exist, or is not a directory.");
            return;
        }

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                boolean deleted = file.delete();
                if (!deleted) {
                    logger.error("Failed to delete file: " + file.getAbsolutePath());
                }
            }
        }

        boolean deleted = directory.delete();
        if (!deleted) {
            logger.error("Failed to delete directory: " + directory.getAbsolutePath());
        }
    }
}
