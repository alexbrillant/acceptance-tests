package ca.ulaval.glo4002.med.acctests.fixtures;

import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;

public class PatientFixture extends HibernateBaseFixture {
    public void createPatient(PatientIdentifier patientIdentifier) {
        withEntityManager((tx) -> {
            Patient patient = new Patient(patientIdentifier);
            PatientRepository patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
            patientRepository.persist(patient);
        });
    }
}
