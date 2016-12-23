package ca.ulaval.glo4002.med.acctests.fixtures.executePrescription;

import ca.ulaval.glo4002.med.acctests.fixtures.HibernateBaseFixture;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import java.time.LocalDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExecutePrescriptionMediumFixture extends HibernateBaseFixture implements ExecutePrescriptionFixture {
    private PatientRepository patientRepository;
    private boolean currentPrescriptionIsExecuted;

    public ExecutePrescriptionMediumFixture() {
        patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
        currentPrescriptionIsExecuted = true;
    }

    @Override
    public void executePrescription(PatientIdentifier patientIdentifier,
                                    PrescriptionIdentifier prescriptionIdentifier,
                                    LocalDate localDate) {
        withEntityManager((tx) -> {
            Patient patient = patientRepository.findByIdentifier(patientIdentifier);

            java.util.Date executionDate = java.sql.Date.valueOf(localDate);
            try {
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
        assertTrue(currentPrescriptionIsExecuted);
    }
}
