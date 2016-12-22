package ca.ulaval.glo4002.med.acctests.fixtures.executePrescription;

import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import java.time.LocalDate;

public interface ExecutePrescriptionFixture {
    void executePrescription(PatientIdentifier patientIdentifier,
                             PrescriptionIdentifier prescriptionIdentifier,
                             LocalDate localDate);

    void thenPrescriptionExecutionIsRefused();
    void thenPrescriptionIsExecuted();
}
