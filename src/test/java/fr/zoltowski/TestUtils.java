package fr.zoltowski;

import fr.zoltowski.models.Reimbursement;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    public static final LocalDate FILE_TIME = LocalDate.from(Program.FILE_DATE_FORMAT.parse("20100417003500"));

    private TestUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Reimbursement> createReimbursementList() throws ParseException {
        List<Reimbursement> reimbursements = new ArrayList<>();

        reimbursements.add(new Reimbursement("1430847091149", "Arnaud", "Pierre",
                Program.BIRTH_DATE_FORMAT.parse("19/10/1926"), "0541522014",
                "Arnaud.Pierre@gmail.com", "57", "I5", 5.96, FILE_TIME));

        reimbursements.add(new Reimbursement("2091174829405", "Louis", "Jean-François",
                Program.BIRTH_DATE_FORMAT.parse("02/12/1911"), "0581991504",
                "Louis.Jean-François@gmail.com", "59", "C8", 1.0, FILE_TIME));

        reimbursements.add(new Reimbursement("2411126984133", "Sébastien", "Édouard",
                Program.BIRTH_DATE_FORMAT.parse("16/09/1947"), "0372138979",
                "Sébastien.Édouard@gmail.com", "65", "N7", 7.7, FILE_TIME));

        return reimbursements;
    }
}
