package ca.ulaval.glo4002.med.acctests.fixtures.executePrescription;

import ca.ulaval.glo4002.med.acctests.fixtures.BaseRestFixture;
import ca.ulaval.glo4002.med.interfaces.rest.resources.PrescriptionFormIso8601;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import io.restassured.response.Response;

import java.time.*;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.eclipse.jetty.http.HttpStatus.Code.BAD_REQUEST;
import static org.eclipse.jetty.http.HttpStatus.Code.OK;


public class ExecutePrescriptionRestFixture extends BaseRestFixture implements ExecutePrescriptionFixture {

    private Response currentRequest;

    @Override
    public void executePrescription(PatientIdentifier patientIdentifier, PrescriptionIdentifier prescriptionIdentifier,
                                    LocalDate localDate) {
        PrescriptionFormIso8601 prescriptionFormIso8601 = new PrescriptionFormIso8601(localDate);
        Date date = prescriptionFormIso8601.toPrescriptionForm().expirationDate;

        Clock clock = Clock.fixed(date.toInstant(), ZoneId.systemDefault());
        ServiceLocator.getInstance().registerAgain(Clock.class, clock);

        String url = createPatientPrescriptionRessourceUrl(patientIdentifier, prescriptionIdentifier);
        currentRequest = givenBaseRequest().when().put(url);
    }

    @Override
    public void thenPrescriptionExecutionIsRefused() {
        assertEquals(BAD_REQUEST.getCode(), currentRequest.statusCode());
    }

    @Override
    public void thenPrescriptionIsExecuted() {
        assertEquals(OK.getCode(), currentRequest.statusCode());
    }

    private String createPatientPrescriptionRessourceUrl(PatientIdentifier patientIdentifier,
                                                         PrescriptionIdentifier prescriptionIdentifier) {
        return String.format("/patients/%s/prescriptions/%s", patientIdentifier.describe(),
                prescriptionIdentifier.describe());
    }
}
