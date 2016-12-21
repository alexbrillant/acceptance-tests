package ca.ulaval.glo4002.med.interfaces.rest.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.med.applicationServices.prescriptions.InvalidPrescriptionFormApplicationException;

@Provider
public class InvalidPrescriptionErrorMapper implements ExceptionMapper<InvalidPrescriptionFormApplicationException> {

    @Override
    public Response toResponse(InvalidPrescriptionFormApplicationException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }

}
