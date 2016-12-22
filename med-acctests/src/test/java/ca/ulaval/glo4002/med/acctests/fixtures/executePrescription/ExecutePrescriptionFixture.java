package ca.ulaval.glo4002.med.acctests.fixtures.executePrescription;

import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

public interface ExecutePrescriptionFixture {
    void executePrescription(PatientIdentifier patientIdentifier, PrescriptionIdentifier prescriptionIdentifier);
    void thenPrescriptionExecutionIsRefused();
    void thenPrescriptionIsExecuted();
}
