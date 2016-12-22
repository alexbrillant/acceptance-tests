package ca.ulaval.glo4002.med.acctests.fixtures.createPrescription;

import ca.ulaval.glo4002.med.acctests.fixtures.BaseRestFixture;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import ca.ulaval.glo4002.med.interfaces.rest.resources.PrescriptionFormIso8601;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.UUID;

import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;
import static org.eclipse.jetty.http.HttpStatus.Code.CREATED;
import static org.junit.Assert.assertEquals;

public class CreatePrescriptionRestFixture extends BaseRestFixture implements CreatePrescriptionFixture {

    private static final LocalDate EXPIRATION_DATE = LocalDate.now();
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
    public void whenAddingPrescription(PatientIdentifier patientIdentifier) {
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
    public void thenPrescriptionCreationIsConfirmed() {
        assertEquals(CREATED.getCode(), currentRequest.statusCode());
    }

    @Override
    public void thenPrescriptionCreationHasAnError() {
        assertEquals(BAD_REQUEST.getCode(), currentRequest.statusCode());
    }

    @Override
    public PrescriptionIdentifier givenAddedPrescriptionWithRenewals(PatientIdentifier patientIdentifier,
                                                                     int renewals, LocalDate expirationDate) {
        currentPrescription = new PrescriptionFormIso8601(PHYSICIAN, expirationDate, renewals, DIN, NAME);
        whenAddingPrescription(patientIdentifier);
        return getCreatedPrescriptionIdentifier();
    }

    @Override
    public PrescriptionIdentifier givenAddedPrescription(PatientIdentifier patientIdentifier) {
        currentPrescription = new PrescriptionFormIso8601(PHYSICIAN, EXPIRATION_DATE, RENEWALS, DIN, NAME);
        whenAddingPrescription(patientIdentifier);
        return getCreatedPrescriptionIdentifier();
    }

    private String createPatientPrescriptionRessourceUrl(PatientIdentifier patientIdentifier) {
        return String.format("/patients/%s/prescriptions", patientIdentifier.describe());
    }

}
