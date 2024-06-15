package fr.zoltowski.repositories;

import fr.zoltowski.core.db.DatabaseManager;
import fr.zoltowski.core.db.Repository;
import fr.zoltowski.models.Reimbursement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static fr.zoltowski.Program.LOGGER;

public class ReimbursementRepository implements Repository<Reimbursement> {
    private final DatabaseManager databaseManager;

    public ReimbursementRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void saveOrUpdateInBatch(List<Reimbursement> reimbursements) {

        final String sql = "INSERT INTO reimbursement " +
                "(social_security_id, lastname, first_name, birthday, email, phone, reimbursement_id, health_code, amount,date_of_reimbursement) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?) " +
                "ON DUPLICATE KEY UPDATE " +
                " lastname = VALUES(lastname), first_name = VALUES(first_name), " +
                "birthday = VALUES(birthday), phone = VALUES(phone), " +
                "email = VALUES(email), health_code = VALUES(health_code), amount = VALUES(amount), date_of_reimbursement = VALUES(date_of_reimbursement)";


        try (Connection connection = databaseManager.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                for (Reimbursement reimbursement : reimbursements) {
                    ps.setString(1, reimbursement.id());
                    ps.setString(2, reimbursement.name());
                    ps.setString(3, reimbursement.firstName());
                    ps.setDate(4, new Date(reimbursement.birthDate().getTime()));
                    ps.setString(5, reimbursement.email());
                    ps.setString(6, reimbursement.phoneNumber());
                    ps.setString(7, reimbursement.idReimbursement());
                    ps.setString(8, reimbursement.healthCode());
                    ps.setDouble(9, reimbursement.amount());
                    ps.setDate(10, Date.valueOf(reimbursement.dateOfReimbursement()));

                    ps.addBatch();
                }
                ps.executeBatch();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "saveOrUpdateInBatch", e);
        }
    }
}
