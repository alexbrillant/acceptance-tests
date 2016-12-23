package ca.ulaval.glo4002.med.acctests.fixtures.executePrescription;

import ca.ulaval.glo4002.med.acctests.fixtures.HibernateBaseFixture;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ExecutePrescriptionMediumFixture extends HibernateBaseFixture implements ExecutePrescriptionFixture {
    private PatientRepository patientRepository;
    private boolean currentPrescriptionIsExecuted;

    private PatientIdentifier currentPatientIdentifier;
    private PrescriptionIdentifier currentPrescriptionIdentifier;
    private int currentPrescriptionRenewalBeforeExecution;

    public ExecutePrescriptionMediumFixture() {
        patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
        currentPrescriptionIsExecuted = true;
    }

    @Override
    public void executePrescriptionNow(PatientIdentifier patientIdentifier, PrescriptionIdentifier prescriptionIdentifier) {
        executePrescriptionAtLocalDate(patientIdentifier, prescriptionIdentifier, LocalDate.now());
    }
    @Override
    public void executePrescriptionAtLocalDate(PatientIdentifier patientIdentifier, PrescriptionIdentifier prescriptionIdentifier,
                                               LocalDate localDate) {
        currentPrescriptionIdentifier = prescriptionIdentifier;
        currentPatientIdentifier = patientIdentifier;
        java.util.Date executionDate = java.sql.Date.valueOf(localDate);

        withEntityManager((tx) -> {
            try {
                Patient patient = patientRepository.findByIdentifier(patientIdentifier);
                currentPrescriptionRenewalBeforeExecution = patient.getPrescription(prescriptionIdentifier).getRenewals();
                patient.executePrescription(prescriptionIdentifier, executionDate);
                patientRepository.persist(patient);
            } catch (Exception e) {
                currentPrescriptionIsExecuted = false;
            }
        });
    }

    @Override
    public void thenPrescriptionExecutionIsRefused() {
        assertFalse(currentPrescriptionIsExecuted);
    }

    @Override
    public void thenPrescriptionIsExecuted() {
        withEntityManager((tx) -> {
            Patient patient = patientRepository.findByIdentifier(currentPatientIdentifier);
            Prescription prescription = patient.getPrescription(currentPrescriptionIdentifier);
            assertEquals(currentPrescriptionRenewalBeforeExecution - 1, prescription.getRenewals());
        });
    }
}
