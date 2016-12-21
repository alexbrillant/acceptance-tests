package ca.ulaval.glo4002.med.acctests.fixtures;

import ca.ulaval.glo4002.med.applicationServices.prescriptions.PrescriptionForm;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;

import java.util.Date;

public class PrescriptionRestFixture extends BaseRestFixture implements PrescriptionFixture {

    public static final Date EXPIRATION_DATE = new Date();
    public static final int RENEWALS = 1;
    public static final String DIN = "din";
    public static final String NAME = "Alice";

    @Override
    public void createValidPrescription(PatientIdentifier patientIdentifier) {
        PrescriptionForm prescription = new PrescriptionForm();
        prescription.expirationDate = EXPIRATION_DATE;
        prescription.din = DIN;
        prescription.renewals = RENEWALS;
        prescription.din = DIN;
        prescription.name = NAME;

        callPatientPrescriptionRequest(patientIdentifier, prescription);
    }

    @Override
    public void createInvalidPrescription(PatientIdentifier patientIdentifier) {
        PrescriptionForm prescription = new PrescriptionForm();
        callPatientPrescriptionRequest(patientIdentifier, prescription);
    }

    private void callPatientPrescriptionRequest(PatientIdentifier patientIdentifier, PrescriptionForm prescriptionForm) {
        givenBaseRequest()
                .body(prescriptionForm)
                .post(createPatientPrescriptionRessourceUrl(patientIdentifier));
    }

    private String createPatientPrescriptionRessourceUrl(PatientIdentifier patientIdentifier) {
        return String.format("/patients/%s/prescriptions", patientIdentifier.describe());
    }
}
