package ca.ulaval.glo4002.med.persistence;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;

public class HibernatePatientRepository implements PatientRepository {

    private EntityManagerProvider entityManagerProvider;

    public HibernatePatientRepository() {
        entityManagerProvider = new EntityManagerProvider();
    }

    @Override
    public void persist(Patient patient) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(patient);
        entityManager.getTransaction().commit();
    }

    @Override
    public Patient findByIdentifier(PatientIdentifier patientIdentifier) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        return (Patient) entityManager.find(Patient.class, patientIdentifier);
    }
}
