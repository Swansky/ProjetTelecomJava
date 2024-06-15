package fr.zoltowski.core.files;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DirectoryUtilsTest {
    private static final String DIRECTORY_PATH_TEST = "test/csv/input";

    @Test
    public void createDirectoryIfNotExist() {

        DirectoryUtils.createDirectoryIfNotExist(DIRECTORY_PATH_TEST);

        assertTrue(Files.exists(Paths.get(DIRECTORY_PATH_TEST)));
    }



}
