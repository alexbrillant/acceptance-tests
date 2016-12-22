package ca.ulaval.glo4002.med.acctests.fixtures.prescription;

import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

public interface PrescriptionFixture {
    void createValidPrescription(PatientIdentifier patientIdentifier);
    void createInvalidPrescription(PatientIdentifier patientIdentifier);
    void thenPrescriptionIsConfirmed();
    void thenPrescriptionHasAnError();
    void addPrescriptionToPatient(PatientIdentifier patientIdentifier);
    PrescriptionIdentifier getCreatedPrescriptionIdentifier();
}
