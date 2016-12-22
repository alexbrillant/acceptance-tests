package ca.ulaval.glo4002.med.acctests.fixtures.createPrescription;

import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

public interface CreatePrescriptionFixture {
    void givenValidPrescription(PatientIdentifier patientIdentifier);
    void givenInvalidPrescription(PatientIdentifier patientIdentifier);

    void whenAddingPrescription(PatientIdentifier patientIdentifier);

    PrescriptionIdentifier getCreatedPrescriptionIdentifier();
    void thenPrescriptionCreationIsConfirmed();
    void thenPrescriptionCreationHasAnError();

    PrescriptionIdentifier givenAddedPrescriptionWithRenewals(PatientIdentifier patientIdentifier, int renewals);
}