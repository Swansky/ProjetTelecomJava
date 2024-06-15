package fr.zoltowski;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {

    private static final String[] VALID_CSV_FILE_NAMES = {"users_20100417003500.csv", "users_20101126102519.csv"};
    private static final String[] INVALID_CSV_FILE_NAMES = {"invalid.csv", "invalid2.csv","users_0100417003500.csv","u_20100417003500.csv"};

    @Test
    public void testIsValidCsvFile() {
        for (String validCsvFileName : VALID_CSV_FILE_NAMES) {
            assertTrue(Program.isValidCsvFile(validCsvFileName));
        }

        for (String invalidCsvFileName : INVALID_CSV_FILE_NAMES) {
            assertFalse(Program.isValidCsvFile(invalidCsvFileName));
        }
    }



}
