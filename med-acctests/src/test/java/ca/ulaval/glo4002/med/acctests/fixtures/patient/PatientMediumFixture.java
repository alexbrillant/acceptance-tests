package ca.ulaval.glo4002.med.acctests.fixtures.patient;

import ca.ulaval.glo4002.med.acctests.fixtures.HibernateBaseFixture;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import java.time.Clock;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatientMediumFixture extends HibernateBaseFixture {

    private PatientRepository patientRepository;
    private Clock clock;

    public PatientMediumFixture() {
        patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
        clock = ServiceLocator.getInstance().resolve(Clock.class);
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
            Date date = Date.from(clock.instant());
            assertTrue(prescription.getExecutionDates().contains(date));
        });
    }
}