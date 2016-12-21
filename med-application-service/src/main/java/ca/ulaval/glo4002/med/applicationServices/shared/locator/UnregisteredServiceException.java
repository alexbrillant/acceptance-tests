package ca.ulaval.glo4002.med.applicationServices.shared.locator;

public class UnregisteredServiceException extends RuntimeException {

    public UnregisteredServiceException(Class<?> service) {
        super("Cannot find service name '" + service.getCanonicalName() + "'. Did you register it?");
    }

    private static final long serialVersionUID = 1L;

}
