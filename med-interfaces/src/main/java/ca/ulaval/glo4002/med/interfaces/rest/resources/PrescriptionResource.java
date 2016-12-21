package ca.ulaval.glo4002.med.interfaces.rest.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.med.applicationServices.prescriptions.PrescriptionApplicationService;
import ca.ulaval.glo4002.med.applicationServices.prescriptions.PrescriptionForm;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

@Path("/patients/{patientIdentifier}/prescriptions")
@Produces(MediaType.APPLICATION_JSON)
public class PrescriptionResource {

    private PrescriptionApplicationService service;

    public PrescriptionResource() {
        service = new PrescriptionApplicationService();
    }

    public PrescriptionResource(PrescriptionApplicationService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPrescription(@PathParam("patientIdentifier") PatientIdentifier patientIdentifier,
            PrescriptionForm prescriptionForm) {

        PrescriptionIdentifier prescriptionIdentifier = service.addPrescription(patientIdentifier, prescriptionForm);
        return Response.created(URI.create(convertToUrl(patientIdentifier, prescriptionIdentifier))).build();
    }

    private String convertToUrl(PatientIdentifier patientIdentifier, PrescriptionIdentifier prescriptionIdentifier) {
        return String.format("/patients/%s/prescriptions/%s", patientIdentifier.describe(), prescriptionIdentifier.describe());
    }
}
