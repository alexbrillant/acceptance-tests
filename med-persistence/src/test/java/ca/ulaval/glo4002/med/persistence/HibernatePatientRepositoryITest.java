package ca.ulaval.glo4002.med.persistence;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

public class HibernatePatientRepositoryITest {

    private static final PatientIdentifier PATIENT_IDENTIFIER = new PatientIdentifier("12455");
    private HibernatePatientRepository repository;

    @Before
    public void createEntityManager() {
        EntityManagerProvider.setEntityManager(EntityManagerFactoryProvider.getFactory().createEntityManager());
        repository = new HibernatePatientRepository();
    }

    @After
    public void clearEntityManager() {
        EntityManagerProvider.clearEntityManager();
    }

    @Test
    public void persistsThePrescriptionsWithThePatient() {
        PrescriptionIdentifier prescriptionIdentifier = PrescriptionIdentifier.create();
        repository.persist(createPatientWithPrescriptions(prescriptionIdentifier));

        Patient patientFound = repository.findByIdentifier(PATIENT_IDENTIFIER);

        assertTrue("Should contain the prescription added before saving the patient",
                patientFound.hasPrescription(prescriptionIdentifier));
    }

    private Patient createPatientWithPrescriptions(PrescriptionIdentifier prescriptionIdentifier) {
        Patient patient = new Patient(PATIENT_IDENTIFIER);
        patient.addPrescription(createPrescription(prescriptionIdentifier));
        return patient;
    }

    private Prescription createPrescription(PrescriptionIdentifier prescriptionIdentifier) {
        return new Prescription(prescriptionIdentifier, "2134", new Date(), "physician", 2);
    }
}
