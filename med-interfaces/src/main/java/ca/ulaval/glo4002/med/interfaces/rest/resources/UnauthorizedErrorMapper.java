package ca.ulaval.glo4002.med.interfaces.rest.resources;

import ca.ulaval.glo4002.med.core.prescriptions.UnauthorizedRenewalException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedErrorMapper implements ExceptionMapper<UnauthorizedRenewalException> {
    @Override
    public Response toResponse(UnauthorizedRenewalException e) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
