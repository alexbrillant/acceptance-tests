package ca.ulaval.glo4002.med.interfaces.rest.resources;

import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionExpiredException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PrescriptionExpiredErrorMapper implements ExceptionMapper<PrescriptionExpiredException> {
    @Override
    public Response toResponse(PrescriptionExpiredException e) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
