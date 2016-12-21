package ca.ulaval.glo4002.med.core.patients;

import java.util.ArrayList;
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

}
