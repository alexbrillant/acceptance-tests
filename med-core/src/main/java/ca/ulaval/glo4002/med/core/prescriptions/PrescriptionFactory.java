package ca.ulaval.glo4002.med.core.prescriptions;

import java.util.Date;

public interface PrescriptionFactory {

    Prescription create(String din, Date date, String physician, int renewals);

}
