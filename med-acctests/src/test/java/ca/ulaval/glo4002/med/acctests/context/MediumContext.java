package ca.ulaval.glo4002.med.acctests.context;

import ca.ulaval.glo4002.med.acctests.fixtures.patient.PatientMediumFixture;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.context.ContextBase;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.GespharPrescriptionFactory;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionFactory;
import ca.ulaval.glo4002.med.persistence.HibernatePatientRepository;

public class MediumContext extends ContextBase {

    public static final PatientIdentifier EXISTENT_PATIENT_IDENTIFIER = new PatientIdentifier("1234");

    @Override
    protected void registerServices() {
        ServiceLocator.reset();
        ServiceLocator.getInstance().register(PatientRepository.class, new HibernatePatientRepository());
        ServiceLocator.getInstance().register(PrescriptionFactory.class, new GespharPrescriptionFactory());
    }

    @Override
    protected void applyFillers() {
        PatientMediumFixture patientFixture = new PatientMediumFixture();
        patientFixture.givenPatient(EXISTENT_PATIENT_IDENTIFIER);
    }
}
