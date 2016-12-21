package ca.ulaval.glo4002.med.applicationServices.prescriptions;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrescriptionForm {

    public String physician;
    @JsonProperty("expiration_date")
    public Date expirationDate;
    public int renewals;
    public String din;
    public String name;

    public boolean isValid() {
        if (din == null || din.equals("")) {
            return false;
        }

        if (expirationDate == null) {
            return false;
        }

        if (physician == null || physician.equals("")) {
            return false;
        }

        if (renewals < 0) {
            throw new InvalidPrescriptionFormApplicationException();
        }
        
        return true;
    }
}
