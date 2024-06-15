package fr.zoltowski;

import fr.zoltowski.core.db.DatabaseManager;
import fr.zoltowski.core.db.Repository;
import fr.zoltowski.core.files.CSVParser;
import fr.zoltowski.core.files.FileMover;
import fr.zoltowski.core.files.FileWatcher;
import fr.zoltowski.models.Reimbursement;
import fr.zoltowski.repositories.ReimbursementRepository;

import java.io.File;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Make by Valérian ZOLTOWSKI and Benjamin ARNAUDIER
 */

public class Program {
    public static final DateFormat BIRTH_DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
    public static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final Logger LOGGER = Logger.getGlobal();

    private static final String PATH_TO_WATCH_DIRECTORY = "csv/input";
    private static final String PATH_TO_PROCESSED_DIRECTORY = "csv/processed";


    public static void main(String[] args) {
        LOGGER.setLevel(Level.ALL);

        try {
            FileWatcher fileWatcher = new FileWatcher(PATH_TO_WATCH_DIRECTORY);
            CSVParser csvParser = new CSVParser();
            DatabaseManager dbManager = new DatabaseManager();
            Repository<Reimbursement> reimbursementRepository = new ReimbursementRepository(dbManager);
            FileMover fileMover = new FileMover(PATH_TO_PROCESSED_DIRECTORY);

            fileWatcher.watch(filePath -> {
                File file = filePath.toFile();
                if (!file.exists()) {
                    return;
                }
                if (!isValidCsvFile(file.getName())) {
                    return;
                }
                List<Reimbursement> users = csvParser.parse(filePath, Reimbursement::fromCsv);
                reimbursementRepository.saveOrUpdateInBatch(users);
                fileMover.moveToProcessed(filePath);
                LOGGER.info("File processed: " + file);
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred", e);
            System.exit(1);
        }
    }

    public static boolean isValidCsvFile(String name) {
        String regex = "^users_\\d{14}\\.csv$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }
}
