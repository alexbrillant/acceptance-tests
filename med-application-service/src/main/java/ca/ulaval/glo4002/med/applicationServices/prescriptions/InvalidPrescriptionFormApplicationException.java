package ca.ulaval.glo4002.med.applicationServices.prescriptions;

public class InvalidPrescriptionFormApplicationException extends RuntimeException {

    public InvalidPrescriptionFormApplicationException() {
        super("The createPrescription form is invalid");
    }

    private static final long serialVersionUID = 1L;
}
