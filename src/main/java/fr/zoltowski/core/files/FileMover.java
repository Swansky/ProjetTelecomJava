package fr.zoltowski.core.files;

import java.nio.file.*;
import java.io.IOException;
import java.util.logging.Level;

import static fr.zoltowski.Program.LOGGER;

public class FileMover {
    private final String processedDirectory;

    public FileMover(String processedDirectory) {
        DirectoryUtils.createDirectoryIfNotExist(processedDirectory);
        this.processedDirectory = processedDirectory;
    }

    public void moveToProcessed(Path filePath) {
        try {
            Files.move(filePath, Paths.get(processedDirectory).resolve(filePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "move file method", e);
        }
    }
}

