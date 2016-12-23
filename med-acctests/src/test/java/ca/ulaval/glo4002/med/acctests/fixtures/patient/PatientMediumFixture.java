package ca.ulaval.glo4002.med.acctests.fixtures.patient;

import ca.ulaval.glo4002.med.acctests.fixtures.HibernateBaseFixture;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatientMediumFixture extends HibernateBaseFixture {

    private PatientRepository patientRepository;
    private int expectedExecutionCount;

    public PatientMediumFixture() {
        patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
        expectedExecutionCount = 0;
    }

    public void givenPatient(PatientIdentifier patientIdentifier) {
        withEntityManager((tx) -> {
            Patient patient = new Patient(patientIdentifier);
            patientRepository.persist(patient);
        });
    }

    public void thenPatientHasPrescription(PatientIdentifier patientIdentifier,
                                           PrescriptionIdentifier prescriptionIdentifier) {
        withEntityManager((tx) -> {
            Patient patient = patientRepository.findByIdentifier(patientIdentifier);
            assertTrue(patient.hasPrescription(prescriptionIdentifier));
        });
    }

    public void thenPatientDoesntHaveAnyPrescription(PatientIdentifier existentPatientIdentifier, PrescriptionIdentifier prescriptionIdentifier) {
        withEntityManager((tx) -> {
            Patient patient = patientRepository.findByIdentifier(existentPatientIdentifier);
            assertFalse(patient.hasPrescription(prescriptionIdentifier));
        });
    }

    public void thenPatientHasPrescriptionWithExecutionDate(PatientIdentifier patientIdentifier,
                                                            PrescriptionIdentifier prescriptionIdentifier) {
        withEntityManager((tx) -> {
            Patient patient = patientRepository.findByIdentifier(patientIdentifier);
            Prescription prescription = patient.getPrescription(prescriptionIdentifier);
            int actualExecutionCount = prescription.getExecutionDates().size();
            assertEquals(expectedExecutionCount, actualExecutionCount);
        });
    }
}