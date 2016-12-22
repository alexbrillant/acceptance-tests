package ca.ulaval.glo4002.med.core.patients;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    public void givenNoPrescriptionWithIdentifierWanted_whenExecuting_shouldNotExecuteAnyPrescription() {
        Prescription prescription = createPrescriptionMockInPatient(false);

        patient.executePrescription(PrescriptionIdentifier.create());

        verify(prescription, never()).execute();
    }

    @Test
    public void givenWantedPrescription_whenExecuting_shouldExecutePrescription() {
        Prescription prescription = createPrescriptionMockInPatient(true);

        patient.executePrescription(PrescriptionIdentifier.create());

        verify(prescription).execute();
    }

    private Prescription createPrescriptionMockInPatient(boolean hasIdentifier) {
        Prescription prescription = mock(Prescription.class);
        willReturn(hasIdentifier).given(prescription).hasIdentifier(any());
        patient.addPrescription(prescription);
        return prescription;
    }
}
