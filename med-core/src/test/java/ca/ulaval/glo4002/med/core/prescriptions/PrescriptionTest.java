package ca.ulaval.glo4002.med.core.prescriptions;

import static org.junit.Assert.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.Test;

public class PrescriptionTest {

    private static final int RENEWALS = 1;
    private static final String PHYSICIAN = "879986";
    private static final Date EXPIRATION_DATE = new Date();;
    private static final String DIN = "din12948";
    public static final int NO_MORE_RENEWALS = 0;

    @Test
    public void hasIdentifierIfComparedToTheInitialIdentifiedUUID() {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, EXPIRATION_DATE, PHYSICIAN, RENEWALS);

        boolean sameIdentifier = prescription.hasIdentifier(identifier);

        assertTrue(sameIdentifier);
    }

    @Test
    public void hasIdentifierIfComparedToADifferentIdentifier() {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, EXPIRATION_DATE, PHYSICIAN, RENEWALS);

        boolean sameIdentifier = prescription.hasIdentifier(PrescriptionIdentifier.create());

        assertFalse(sameIdentifier);
    }

    @Test(expected = UnauthorizedRenewalException.class)
    public void givenNoMoreRenewals_whenExecuting_shouldThrowException() {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, EXPIRATION_DATE, PHYSICIAN, NO_MORE_RENEWALS);

        prescription.execute(EXPIRATION_DATE);
    }

    @Test
    public void givenRenewalsAndValidExecutionDate_whenExecuting_shouldRemoveOneRenewal() throws Exception {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, EXPIRATION_DATE, PHYSICIAN, RENEWALS);

        prescription.execute(EXPIRATION_DATE);

        assertEquals(RENEWALS - 1, prescription.getRenewals());
    }

    @Test(expected = PrescriptionExpiredException.class)
    public void givenExecutionDateAfterExpirationDate_whenExecuting_shouldThrowException() {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, EXPIRATION_DATE, PHYSICIAN, RENEWALS);
        final Instant executionInstant = EXPIRATION_DATE.toInstant().plus(1, ChronoUnit.SECONDS);
        Date executionDate = Date.from(executionInstant);

        prescription.execute(executionDate);
    }
}
