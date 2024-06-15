package fr.zoltowski.core.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FileWatcherTest {
    private static final Path DIRECTORY_PATH_TEST = Path.of("test/csv/input/users_20100417003500.csv");
    private static final Path CSV_TEST_FILE_PATH = Path.of("test/users_20100417003500.csv");

    private FileWatcher fileWatcher;

    @BeforeEach
    public void setUp() throws IOException {
        fileWatcher = new FileWatcher("test/csv/input");
        Files.deleteIfExists(DIRECTORY_PATH_TEST);
    }

    @Test
    public void watchExistFile() {
        Future<?> submit;
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            Files.copy(CSV_TEST_FILE_PATH, DIRECTORY_PATH_TEST);
            submit = executorService.submit(() -> {
                fileWatcher.watch(filePath -> {
                    assertEquals(CSV_TEST_FILE_PATH.toAbsolutePath().toString(), filePath.toAbsolutePath().toString());
                    fileWatcher.stop();
                });
            });

            Thread.sleep(2000);
            if (!submit.isDone()) {
                submit.cancel(true);
                fail("FileWatcher did not detect the file");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    @Test
    public void watchWhenFileIsAdded() throws IOException {
        Future<?> submit;
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            submit = executorService.submit(() -> {
                fileWatcher.watch(filePath -> {
                    assertEquals(CSV_TEST_FILE_PATH.toAbsolutePath().toString(), filePath.toAbsolutePath().toString());
                    fileWatcher.stop();
                });
            });
            Thread.sleep(2000);
            Files.copy(CSV_TEST_FILE_PATH, DIRECTORY_PATH_TEST);

            Thread.sleep(2000);
            if (!submit.isDone()) {
                submit.cancel(true);
                fail("FileWatcher did not detect the file");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
