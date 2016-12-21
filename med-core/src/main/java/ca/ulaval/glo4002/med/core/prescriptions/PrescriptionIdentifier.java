package ca.ulaval.glo4002.med.core.prescriptions;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrescriptionIdentifier implements Serializable {

    @Column
    private UUID number;

    protected PrescriptionIdentifier() {
    }

    public PrescriptionIdentifier(UUID number) {
        this.number = number;
    }

    public static PrescriptionIdentifier create() {
        return new PrescriptionIdentifier(UUID.randomUUID());
    }

    public String describe() {
        return number.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof PrescriptionIdentifier)) {
            return ((PrescriptionIdentifier) obj).number.equals(number);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    private static final long serialVersionUID = 1L;
}
