package ca.ulaval.glo4002.med.acctests.fixtures.prescription;

import ca.ulaval.glo4002.med.applicationServices.prescriptions.PrescriptionForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PrescriptionFormIso8601 {
    public String physician;

    @JsonIgnore
    public LocalDate expirationDateIso;
    public int renewals;
    public String din;
    public String name;

    public PrescriptionFormIso8601(String physician, LocalDate expirationDateIso, int renewals, String din, String name) {
        this.physician = physician;
        this.expirationDateIso = expirationDateIso;
        this.renewals = renewals;
        this.din = din;
        this.name = name;
    }

    public PrescriptionFormIso8601(LocalDate expirationDateIso) {
        this.expirationDateIso = expirationDateIso;
    }

    @JsonProperty("expiration_date")
    public String getExpirationDate() {
        LocalDateTime dateTime = LocalDateTime.of(expirationDateIso, LocalTime.MIDNIGHT);
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public PrescriptionForm toPrescriptionForm() {
        PrescriptionForm form = new PrescriptionForm();
        form.din = din;
        form.name = name;
        form.physician = physician;
        form.renewals = renewals;
        form.expirationDate = Date.from(expirationDateIso.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return form;
    }
}
