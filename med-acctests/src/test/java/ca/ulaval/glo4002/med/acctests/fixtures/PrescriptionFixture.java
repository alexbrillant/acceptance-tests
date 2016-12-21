package ca.ulaval.glo4002.med.acctests.fixtures;

import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;

public interface PrescriptionFixture {
    void createValidPrescription(PatientIdentifier patientIdentifier);
    void createInvalidPrescription(PatientIdentifier patientIdentifier);
}
