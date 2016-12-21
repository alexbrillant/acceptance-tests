package ca.ulaval.glo4002.med.core.prescriptions;

import java.util.Date;

// Creates prescriptions for the Gesphar system, not just any prescription.
public class GespharPrescriptionFactory implements PrescriptionFactory {

    @Override
    public Prescription create(String din, Date date, String physician, int renewals) {
        Prescription prescription = new Prescription(PrescriptionIdentifier.create(), din, date, physician, renewals);
        return prescription;
    }

}
