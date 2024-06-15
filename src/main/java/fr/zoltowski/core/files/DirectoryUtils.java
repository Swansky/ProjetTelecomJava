package fr.zoltowski.core.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import static fr.zoltowski.Program.LOGGER;

public class DirectoryUtils {
    private DirectoryUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void createDirectoryIfNotExist(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                LOGGER.info("Directory created: " + directoryPath);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "createDirectoryIfNotExist", e);
            }
        }
    }
}

