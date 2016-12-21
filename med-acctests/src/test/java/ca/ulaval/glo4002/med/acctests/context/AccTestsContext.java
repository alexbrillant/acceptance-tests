package ca.ulaval.glo4002.med.acctests.context;

import ca.ulaval.glo4002.med.acctests.fixtures.PatientFixture;
import ca.ulaval.glo4002.med.acctests.runners.MedServerRunner;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.context.ContextBase;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.GespharPrescriptionFactory;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionFactory;
import ca.ulaval.glo4002.med.persistence.HibernatePatientRepository;

public class AccTestsContext extends ContextBase {

    public static final PatientIdentifier EXISTENT_PATIENT_IDENTIFIER = new PatientIdentifier("1234");

    public void reinitialize() {
            ServiceLocator.reset();
            apply();
            new MedServerRunner();
    }

    @Override
    protected void registerServices() {
        PatientRepository patientRepository = new HibernatePatientRepository();
        ServiceLocator.getInstance().register(PatientRepository.class, patientRepository);
        PrescriptionFactory prescriptionFactory = new GespharPrescriptionFactory();
        ServiceLocator.getInstance().register(PrescriptionFactory.class, prescriptionFactory);
    }

    @Override
    protected void applyFillers() {
        PatientFixture patientFixture = new PatientFixture();
        patientFixture.createPatient(EXISTENT_PATIENT_IDENTIFIER);
    }
}
