package fr.zoltowski.core.files;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.nio.file.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;

import static fr.zoltowski.Program.LOGGER;


public class CSVParser {
    private final static com.opencsv.CSVParser CSV_PARSER = new CSVParserBuilder()
            .withSeparator(',')
            .withIgnoreQuotations(true)
            .build();

    public <T> List<T> parse(Path filePath, ConsumeCsv<T> consumeCsv) {
        List<T> items = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(CSV_PARSER)
                    .build()) {
                csvReader.readAll().forEach(data -> {
                    List<String> list = new ArrayList<>(Arrays.stream(data).map(String::trim).toList());
                    list.add(filePath.toFile().getName());
                    String[] lines = list.toArray(String[]::new);
                    Optional<T> itemOptional = consumeCsv.fromCsv(lines);
                    itemOptional.ifPresent(items::add);
                });
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "parse", e);
        }
        return items;
    }

}
