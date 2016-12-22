package ca.ulaval.glo4002.med.core.prescriptions;

public class PrescriptionExpiredException extends RuntimeException {
    public PrescriptionExpiredException() {
        super("prescription expired");
    }
}
