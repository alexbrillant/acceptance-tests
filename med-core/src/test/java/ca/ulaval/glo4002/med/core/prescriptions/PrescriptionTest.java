package ca.ulaval.glo4002.med.core.prescriptions;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class PrescriptionTest {

    private static final int RENEWALS = 1;
    private static final String PHYSICIAN = "879986";
    private static final Date DATE = new Date();;
    private static final String DIN = "din12948";
    public static final int NO_MORE_RENEWALS = 0;

    @Test
    public void hasIdentifierIfComparedToTheInitialIdentifiedUUID() {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, DATE, PHYSICIAN, RENEWALS);

        boolean sameIdentifier = prescription.hasIdentifier(identifier);

        assertTrue(sameIdentifier);
    }

    @Test
    public void hasIdentifierIfComparedToADifferentIdentifier() {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, DATE, PHYSICIAN, RENEWALS);

        boolean sameIdentifier = prescription.hasIdentifier(PrescriptionIdentifier.create());

        assertFalse(sameIdentifier);
    }

    @Test(expected = UnauthorizedRenewalException.class)
    public void givenNoMoreRenewals_whenExecute_shouldThrowException() {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, DATE, PHYSICIAN, NO_MORE_RENEWALS);

        prescription.execute();
    }

    @Test
    public void givenRenewals_whenExecute_shouldRemoveOneRenewal() throws Exception {
        PrescriptionIdentifier identifier = PrescriptionIdentifier.create();
        Prescription prescription = new Prescription(identifier, DIN, DATE, PHYSICIAN, RENEWALS);

        prescription.execute();

        assertEquals(RENEWALS - 1, prescription.getRenewals());
    }
}
