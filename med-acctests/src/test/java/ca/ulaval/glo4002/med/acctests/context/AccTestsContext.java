package ca.ulaval.glo4002.med.acctests.context;

import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.med.context.ContextBase;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.persistence.HibernatePatientRepository;

public class AccTestsContext extends ContextBase {

    private static boolean isFirstFeature = true;

    public void reinitialize() {
        if (isFirstFeature) {
            ServiceLocator.reset();
            apply();
            isFirstFeature = false;
        }
    }

    @Override
    protected void registerServices() {
        PatientRepository patientRepository = new HibernatePatientRepository();
        ServiceLocator.getInstance().register(PatientRepository.class, patientRepository);
    }

    @Override
    protected void applyFillers() {
        EntityManagerProvider.setEntityManager(EntityManagerFactoryProvider.getFactory().createEntityManager());
        try {
            PatientRepository repository = ServiceLocator.getInstance().resolve(PatientRepository.class);
            repository.persist(new Patient(new PatientIdentifier("1234")));
        } finally {
            EntityManagerProvider.clearEntityManager();
        }
    }
}
