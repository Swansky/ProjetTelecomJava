package fr.zoltowski.core.db;

import fr.zoltowski.Program;
import fr.zoltowski.models.Reimbursement;
import fr.zoltowski.repositories.ReimbursementRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static fr.zoltowski.TestUtils.FILE_TIME;
import static fr.zoltowski.TestUtils.createReimbursementList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseManagerTest {

    private final DatabaseManager databaseManager;
    private final Repository<Reimbursement> repository;

    public DatabaseManagerTest() throws IOException {
        databaseManager = new DatabaseManager();
        repository = new ReimbursementRepository(databaseManager);
    }


    @Test
    public void testReimbursementRepository() throws ParseException, SQLException {
        List<Reimbursement> reimbursementList = createReimbursementList();
        repository.saveOrUpdateInBatch(reimbursementList);

        repository.saveOrUpdateInBatch(reimbursementList);

        Connection connection = databaseManager.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("SELECT count(*)  FROM reimbursement");
        ResultSet resultSet = statement.getResultSet();

        long count = -1;
        while (resultSet.next()) {
            count = resultSet.getLong(1);
        }
        assertEquals(reimbursementList.size(), count);
        connection.close();
    }


    @Test
    public void testCheckUpdate() throws ParseException, SQLException {
        Connection connection = databaseManager.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM reimbursement");
        List<Reimbursement> reimbursements = new ArrayList<>();
        Reimbursement reimbursement = new Reimbursement("1430847091149", "Arnaud", "Pierre",
                Program.BIRTH_DATE_FORMAT.parse("19/10/1926"), "0541522014",
                "Arnaud.Pierre@gmail.com", "57", "I5", 5.96, FILE_TIME);
        reimbursements.add(reimbursement);
        repository.saveOrUpdateInBatch(reimbursements);

        reimbursements = new ArrayList<>();
        reimbursement = new Reimbursement("1430847091149", "Oui", "Pierre",
                Program.BIRTH_DATE_FORMAT.parse("19/10/1926"), "0541522014",
                "Arnaud.Pierre@gmail.com", "57", "I5", 5.96, FILE_TIME);
        reimbursements.add(reimbursement);
        repository.saveOrUpdateInBatch(reimbursements);


        Statement statementSelect = connection.createStatement();
        statementSelect.execute("SELECT lastname FROM reimbursement");
        ResultSet resultSet = statementSelect.getResultSet();

        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString(1);
        }

        assertEquals("Oui", name);
        connection.close();
    }
}
