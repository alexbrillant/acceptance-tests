package ca.ulaval.glo4002.med.acctests.context;

import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.context.ContextBase;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.persistence.HibernatePatientRepository;

public class AccTestsContext extends ContextBase {

    private static boolean isFirstFeature = true;

    public void reinitialize() {
        if (isFirstFeature) {
            ServiceLocator.reset();
            apply();
        }
        else {
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
        // ???
    }

}
