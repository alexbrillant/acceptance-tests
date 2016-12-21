package ca.ulaval.glo4002.med.core.prescriptions;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

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

    protected Prescription() {
    }

    public Prescription(PrescriptionIdentifier identifier, String din, Date date, String physician, int renewals) {
        this.identifier = identifier;
        this.din = din;
        this.date = date;
        this.physician = physician;
        this.renewals = renewals;
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

    public boolean hasIdentifier(PrescriptionIdentifier prescriptionIdentifier) {
        return identifier.equals(prescriptionIdentifier);
    }

}
