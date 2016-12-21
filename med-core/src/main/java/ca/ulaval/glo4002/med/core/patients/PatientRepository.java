package ca.ulaval.glo4002.med.core.patients;

public interface PatientRepository {

    void persist(Patient patient);

    Patient findByIdentifier(PatientIdentifier patientIdentifier);

}
