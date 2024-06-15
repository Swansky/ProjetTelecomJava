package fr.zoltowski.models;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;

import static fr.zoltowski.Program.*;

public record Reimbursement(String id, String name, String firstName, Date birthDate, String phoneNumber, String email,
                            String idReimbursement, String healthCode, double amount,
                            LocalDate dateOfReimbursement) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reimbursement that = (Reimbursement) o;
        return Double.compare(amount, that.amount) == 0 && id.equals(that.id) && name.equals(that.name) && email.equals(that.email) && birthDate.equals(that.birthDate) && firstName.equals(that.firstName) && healthCode.equals(that.healthCode) && phoneNumber.equals(that.phoneNumber) && idReimbursement.equals(that.idReimbursement) && dateOfReimbursement.equals(that.dateOfReimbursement);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + idReimbursement.hashCode();
        result = 31 * result + healthCode.hashCode();
        result = 31 * result + Double.hashCode(amount);
        result = 31 * result + dateOfReimbursement.hashCode();
        return result;
    }

    public static Optional<Reimbursement> fromCsv(String[] lines) {
        try {
            String socialSecurityNumber = lines[0];
            String lastName = lines[1];
            String firstName = lines[2];
            Date birthdate = BIRTH_DATE_FORMAT.parse(lines[3]);
            String phoneNumber = lines[4];
            String email = lines[5];
            String reimbursementId = lines[6];
            String healthCode = lines[7];
            double amount = Double.parseDouble(lines[8]);
            String fileName = lines[9];
            String dateString = fileName.split("_")[1].split("\\.")[0];
            TemporalAccessor dateOfReimbursement = FILE_DATE_FORMAT.parse(dateString);
            LocalDate date = LocalDate.from(dateOfReimbursement);
            Reimbursement reimbursement = new Reimbursement(socialSecurityNumber, lastName, firstName,
                    birthdate, phoneNumber, email, reimbursementId, healthCode, amount, date);
            return Optional.of(reimbursement);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "fromCsv", e);
            return Optional.empty();
        }
    }
}
