package ca.ulaval.glo4002.med.acctests.fixtures.prescription;

import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

public interface PrescriptionFixture {
    void givenValidPrescription(PatientIdentifier patientIdentifier);
    void givenInvalidPrescription(PatientIdentifier patientIdentifier);
    void thenPrescriptionIsConfirmed();
    void thenPrescriptionHasAnError();
    void addPrescriptionToPatient(PatientIdentifier patientIdentifier);
    PrescriptionIdentifier getCreatedPrescriptionIdentifier();
}