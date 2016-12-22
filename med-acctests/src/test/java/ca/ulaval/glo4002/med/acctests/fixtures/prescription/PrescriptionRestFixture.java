package ca.ulaval.glo4002.med.acctests.fixtures.prescription;

import ca.ulaval.glo4002.med.acctests.fixtures.BaseRestFixture;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.util.UUID;

import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;
import static org.eclipse.jetty.http.HttpStatus.Code.CREATED;
import static org.junit.Assert.assertEquals;

public class PrescriptionRestFixture extends BaseRestFixture implements PrescriptionFixture {

    public static final LocalDate NOW = LocalDate.now();
    private Response currentRequest;
    private PrescriptionFormIso8601 currentPrescription;

    private static final String PHYSICIAN = "physician";
    private static final LocalDate EXPIRATION_DATE = LocalDate.now();
    private static final int RENEWALS = 1;
    private static final String DIN = "din";
    private static final String NAME = "Alice";

    @Override
    public void createValidPrescription(PatientIdentifier patientIdentifier) {
        currentPrescription = new PrescriptionFormIso8601();
        currentPrescription.physician = PHYSICIAN;
        currentPrescription.din = DIN;
        currentPrescription.expirationDateIso = NOW;
        currentPrescription.renewals = RENEWALS;
        currentPrescription.name = NAME;
    }

    @Override
    public void createInvalidPrescription(PatientIdentifier patientIdentifier) {
        currentPrescription = new PrescriptionFormIso8601();
        currentPrescription.expirationDateIso = NOW;
    }

    @Override
    public void addPrescriptionToPatient(PatientIdentifier patientIdentifier) {
        currentRequest = givenBaseRequest().body(currentPrescription)
                .when().post(createPatientPrescriptionRessourceUrl(patientIdentifier));
        int i = 0;
    }

    @Override
    public PrescriptionIdentifier getCreatedPrescriptionIdentifier() {
        try {
            String[] split = currentRequest.getHeader("Location").split("/");
            String identifierString = split[split.length - 1];
            UUID identifier = UUID.fromString(identifierString);
            return new PrescriptionIdentifier(identifier);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void thenPrescriptionIsConfirmed() {
        assertEquals(CREATED.getCode(), currentRequest.statusCode());
    }

    @Override
    public void thenPrescriptionHasAnError() {
        assertEquals(BAD_REQUEST.getCode(), currentRequest.statusCode());
    }

    private String createPatientPrescriptionRessourceUrl(PatientIdentifier patientIdentifier) {
        return String.format("/patients/%s/prescriptions", patientIdentifier.describe());
    }
}
