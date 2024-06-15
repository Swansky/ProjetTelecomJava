package fr.zoltowski.core.files;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileMoverTest {
    private static final Path FILE_PATH = Path.of("test/csv/input/test.csv");
    private static final Path PROCESSED_PATH = Path.of("test/csv/processed/test.csv");
    private final FileMover fileMover = new FileMover("test/csv/processed");



    @Test
    void moveToProcessed() throws IOException {
        Files.deleteIfExists(PROCESSED_PATH);
        Files.deleteIfExists(FILE_PATH);

        Files.createDirectories(FILE_PATH.getParent());
        Files.createFile(FILE_PATH);


        fileMover.moveToProcessed(FILE_PATH);

        assertTrue(PROCESSED_PATH.toFile().exists());
    }
}
