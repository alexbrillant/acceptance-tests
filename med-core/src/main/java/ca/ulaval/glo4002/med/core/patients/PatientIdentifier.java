package ca.ulaval.glo4002.med.core.patients;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PatientIdentifier implements Serializable {

    @Column
    private String number;

    protected PatientIdentifier() {
    }

    public PatientIdentifier(String number) {
        this.number = number;
    }

    public String describe() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof PatientIdentifier) {
            return ((PatientIdentifier) obj).number.equals(number);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    private static final long serialVersionUID = 1L;
}
