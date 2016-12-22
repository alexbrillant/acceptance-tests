package ca.ulaval.glo4002.med.core.prescriptions;

public class UnauthorizedRenewalException extends RuntimeException {
    public UnauthorizedRenewalException() {
        super("unautorized renewal");
    }
}
