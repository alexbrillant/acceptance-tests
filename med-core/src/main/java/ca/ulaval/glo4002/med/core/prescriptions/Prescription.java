package ca.ulaval.glo4002.med.core.prescriptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Prescription {

    @EmbeddedId
    private PrescriptionIdentifier identifier;

    @Column
    private String din;

    @Column
    private Date date;

    @Column
    private String physician;

    @Column
    private int renewals;

    @ElementCollection
    private List<Date> executionDates;

    protected Prescription() {
    }

    public Prescription(PrescriptionIdentifier identifier, String din, Date date, String physician, int renewals) {
        this.identifier = identifier;
        this.din = din;
        this.date = date;
        this.physician = physician;
        this.renewals = renewals;
        this.executionDates = new ArrayList<Date>();
    }

    public Prescription(PrescriptionIdentifier identifier, String din, Date date, String physician, int renewals, List<Date> executionDates) {
        this.identifier = identifier;
        this.din = din;
        this.date = date;
        this.physician = physician;
        this.renewals = renewals;
        this.executionDates = executionDates;
    }

    public PrescriptionIdentifier getIdentifier() {
        return identifier;
    }

    public String getDin() {
        return din;
    }

    public Date getDate() {
        return date;
    }

    public String getPhysician() {
        return physician;
    }

    public int getRenewals() {
        return renewals;
    }

    public List<Date> getExecutionDates() {
        return executionDates;
    }

    public boolean hasIdentifier(PrescriptionIdentifier prescriptionIdentifier) {
        return identifier.equals(prescriptionIdentifier);
    }

    public void execute(Date executionDate) {
        if (renewals == 0) {
            throw new UnauthorizedRenewalException();
        }

        if (executionDate.after(date)) {
            throw new PrescriptionExpiredException();
        }

        renewals--;
        executionDates.add(executionDate);
    }
}
