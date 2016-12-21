package ca.ulaval.glo4002.med.core.patients;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import org.junit.Before;
import org.junit.Test;

public class PatientTest {

    private static final PatientIdentifier IDENTIFIER = new PatientIdentifier("234");
    private Patient patient;

    @Before
    public void createPatient() {
        patient = new Patient(IDENTIFIER);
    }

    @Test
    public void canAddAPrescriptionToAPatient() {
        PrescriptionIdentifier prescriptionIdentifier = PrescriptionIdentifier.create();
        Prescription prescription = mock(Prescription.class);
        willReturn(true).given(prescription).hasIdentifier(prescriptionIdentifier);

        patient.addPrescription(prescription);

        assertTrue(patient.hasPrescription(prescriptionIdentifier));
    }

}
