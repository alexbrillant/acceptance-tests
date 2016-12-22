package ca.ulaval.glo4002.med.core.patients;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

@Entity
public class Patient {

    @EmbeddedId
    private PatientIdentifier identifier;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Prescription> prescriptions = new ArrayList<>();

    protected Patient() {
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public Prescription getPrescription(PrescriptionIdentifier prescriptionIdentifier) {
        Prescription prescriptionFound;
        for (Prescription prescription : prescriptions) {
            if (prescription.getIdentifier().equals(prescriptionIdentifier)) {
                return prescription;
            }
        }
        return null;
    }

    public Patient(PatientIdentifier identifier) {
        this.identifier = identifier;
    }

    public PatientIdentifier getIdentifier() {
        return identifier;
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public boolean hasPrescription(PrescriptionIdentifier prescriptionIdentifier) {
        return prescriptions.stream().anyMatch(x -> x.hasIdentifier(prescriptionIdentifier));
    }

    public void executePrescription(PrescriptionIdentifier prescriptionIdentifier, Date executionDate) {
        for (Prescription prescription : prescriptions) {
            if (prescription.hasIdentifier(prescriptionIdentifier)) {
                prescription.execute(executionDate);
            }
        }
    }
}
