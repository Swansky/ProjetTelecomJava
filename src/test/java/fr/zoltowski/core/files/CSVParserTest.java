package fr.zoltowski.core.files;

import fr.zoltowski.Program;
import fr.zoltowski.models.Reimbursement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.text.ParseException;

import java.util.List;

import static fr.zoltowski.TestUtils.createReimbursementList;


public class CSVParserTest {
    private static final String CSV_TEST_FILE = "test/users_20100417003500.csv";
    private static final List<Reimbursement> expectedReimbursements;

    static {
        try {
            expectedReimbursements = createReimbursementList();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    private final CSVParser csvParser = new CSVParser();

    @Test
    public void parse() {
        List<Reimbursement> parse = csvParser.parse(Path.of(CSV_TEST_FILE), Reimbursement::fromCsv);
        Assertions.assertFalse(parse.isEmpty());
        Assertions.assertEquals(expectedReimbursements, parse);
    }


}
