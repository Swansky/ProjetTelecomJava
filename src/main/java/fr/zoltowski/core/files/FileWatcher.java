package fr.zoltowski.core.files;

import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;

import static fr.zoltowski.Program.LOGGER;

public class FileWatcher {
    private final String directoryPath;
    private final WatchService watchService;

    public FileWatcher(String directoryPath) throws IOException {
        DirectoryUtils.createDirectoryIfNotExist(directoryPath);
        this.directoryPath = directoryPath;
        this.watchService = FileSystems.getDefault().newWatchService();
    }

    public void watch(Consumer<Path> onFileDetected) {
        try {
            includeInitFiles(onFileDetected);

            Path path = Paths.get(directoryPath);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path filePath = path.resolve((Path) event.context());
                        if (Files.isDirectory(filePath)) {
                            continue;
                        }
                        onFileDetected.accept(filePath);
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, "watch", e);
        }
    }

    private void includeInitFiles(Consumer<Path> onFileDetected) {
        File directory = new File(directoryPath);
        File[] initialFiles = directory.listFiles();
        if (initialFiles != null) {
            for (File file : initialFiles) {
                onFileDetected.accept(file.toPath());
            }
        }
    }

    public void stop() {
        try {
            watchService.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "stop", e);
        }

    }
}
