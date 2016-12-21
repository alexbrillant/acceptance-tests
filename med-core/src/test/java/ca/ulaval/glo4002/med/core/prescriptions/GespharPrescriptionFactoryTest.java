package ca.ulaval.glo4002.med.core.prescriptions;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class GespharPrescriptionFactoryTest {

    private static final int RENEWALS = 1;
    private static final String PHYSICIAN = "879986";
    private static final Date DATE = new Date();;
    private static final String DIN = "din12948";

    private GespharPrescriptionFactory factory;

    @Before
    public void createFactory() {
        factory = new GespharPrescriptionFactory();
    }

    @Test
    public void assignesAPrescriptionIdentifierToThePrescription() {
        Prescription prescription = factory.create(DIN, DATE, PHYSICIAN, RENEWALS);

        assertNotEquals("", prescription.getIdentifier().describe());
    }

    @Test
    public void assignesANewPrescriptionIdentifierToThePrescription() {
        Prescription prescription1 = factory.create(DIN, DATE, PHYSICIAN, RENEWALS);
        Prescription prescription2 = factory.create(DIN, DATE, PHYSICIAN, RENEWALS);

        assertNotEquals(prescription1.getIdentifier().describe(), prescription2.getIdentifier().describe());
    }

    @Test
    public void assignesTheDinToThePrescription() {
        Prescription prescription = factory.create(DIN, DATE, PHYSICIAN, RENEWALS);

        assertEquals(DIN, prescription.getDin());
    }

    @Test
    public void assignesTheDateToThePrescription() {
        Prescription prescription = factory.create(DIN, DATE, PHYSICIAN, RENEWALS);

        assertEquals(DATE, prescription.getDate());
    }

    @Test
    public void assignesThePhysicianToThePrescription() {
        Prescription prescription = factory.create(DIN, DATE, PHYSICIAN, RENEWALS);

        assertEquals(PHYSICIAN, prescription.getPhysician());
    }

    @Test
    public void assignesTheRenewalsToThePrescription() {
        Prescription prescription = factory.create(DIN, DATE, PHYSICIAN, RENEWALS);

        assertEquals(RENEWALS, prescription.getRenewals());
    }
}
