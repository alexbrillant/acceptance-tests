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

    private static final LocalDate EXPIRATION_DATE = LocalDate.parse("2016-12-12");
    private Response currentRequest;
    private PrescriptionFormIso8601 currentPrescription;

    private static final String PHYSICIAN = "Dr. Bob";
    private static final int RENEWALS = 1;
    private static final String DIN = "02017431";
    private static final String NAME = "ACETAMINOPHEN 160 MG";

    @Override
    public void givenValidPrescription(PatientIdentifier patientIdentifier) {
        currentPrescription = new PrescriptionFormIso8601(PHYSICIAN, EXPIRATION_DATE, RENEWALS, DIN, NAME);
    }

    @Override
    public void givenInvalidPrescription(PatientIdentifier patientIdentifier) {
        currentPrescription = new PrescriptionFormIso8601(EXPIRATION_DATE);
    }

    @Override
    public void addPrescriptionToPatient(PatientIdentifier patientIdentifier) {
        currentRequest = givenBaseRequest().body(currentPrescription)
                .when().post(createPatientPrescriptionRessourceUrl(patientIdentifier));
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
