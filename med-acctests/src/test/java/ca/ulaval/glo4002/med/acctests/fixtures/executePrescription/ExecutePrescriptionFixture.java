package ca.ulaval.glo4002.med.acctests.fixtures.executePrescription;

import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import java.time.LocalDate;

public interface ExecutePrescriptionFixture {
    void executePrescriptionAtLocalDate(PatientIdentifier patientIdentifier,
                                        PrescriptionIdentifier prescriptionIdentifier,
                                        LocalDate localDate);
    void executePrescriptionNow(PatientIdentifier patientIdentifier, PrescriptionIdentifier prescriptionIdentifier);
    void thenPrescriptionExecutionIsRefused();
    void thenPrescriptionIsExecuted();
}
