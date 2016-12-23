package ca.ulaval.glo4002.med.acctests.fixtures.createPrescription;

import ca.ulaval.glo4002.med.acctests.fixtures.HibernateBaseFixture;
import ca.ulaval.glo4002.med.applicationServices.prescriptions.PrescriptionApplicationService;
import ca.ulaval.glo4002.med.applicationServices.prescriptions.PrescriptionForm;
import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import java.time.LocalDate;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CreatePrescriptionMediumFixture extends HibernateBaseFixture implements CreatePrescriptionFixture {

    private static final LocalDate EXPIRATION_DATE = LocalDate.now();
    private static final String PHYSICIAN = "Dr. Bob";
    private static final int RENEWALS = 1;
    private static final String DIN = "02017431";
    private static final String NAME = "ACETAMINOPHEN 160 MG";

    private PatientRepository patientRepository;

    private PrescriptionForm currentPrescription;
    PrescriptionApplicationService service;
    private PrescriptionIdentifier createdPrescriptionIdentifier;
    private PatientIdentifier currentPatientIdentifier;

    public CreatePrescriptionMediumFixture() {
        patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
        service = new PrescriptionApplicationService();
    }

    @Override
    public void givenValidPrescription(PatientIdentifier patientIdentifier) {
        PrescriptionFormIso8601 prescriptionFormIso8601 =
                new PrescriptionFormIso8601(PHYSICIAN, EXPIRATION_DATE, RENEWALS, DIN, NAME);
        currentPrescription = prescriptionFormIso8601.toPrescriptionForm();
    }

    @Override
    public void givenInvalidPrescription(PatientIdentifier patientIdentifier) {
        currentPatientIdentifier = patientIdentifier;
        PrescriptionFormIso8601 prescriptionFormIso8601 = new PrescriptionFormIso8601(EXPIRATION_DATE);
        currentPrescription = prescriptionFormIso8601.toPrescriptionForm();
    }

    @Override
    public void whenAddingPrescription(PatientIdentifier patientIdentifier) {
        currentPatientIdentifier = patientIdentifier;
        withEntityManager((tx) -> {
            try {
                createdPrescriptionIdentifier = service.addPrescription(patientIdentifier, currentPrescription);
            } catch (Exception e) {
                // Nothing to do here
            }
        });
    }

    @Override
    public PrescriptionIdentifier getCreatedPrescriptionIdentifier() {
        return createdPrescriptionIdentifier;
    }

    @Override
    public void thenPrescriptionCreationIsConfirmed() {
        withEntityManager((tx) -> {
            Patient patient = patientRepository.findByIdentifier(currentPatientIdentifier);
            assertTrue(patient.hasPrescription(createdPrescriptionIdentifier));
        });
    }

    @Override
    public void thenPrescriptionCreationHasAnError() {
        assertNull(createdPrescriptionIdentifier);
    }

    @Override
    public PrescriptionIdentifier givenPrescriptionForPatient(PatientIdentifier patientIdentifier) {
        return givenPrescriptionForPatient(patientIdentifier, RENEWALS, EXPIRATION_DATE);
    }

    @Override
    public PrescriptionIdentifier givenPrescriptionForPatient(PatientIdentifier patientIdentifier, int renewals, LocalDate expirationDate) {
        java.util.Date date = java.sql.Date.valueOf(expirationDate);

        withEntityManager((tx) -> {
            createdPrescriptionIdentifier = PrescriptionIdentifier.create();
            Prescription prescription = new Prescription(createdPrescriptionIdentifier, DIN, date, PHYSICIAN, renewals);
            Patient patient = patientRepository.findByIdentifier(patientIdentifier);
            patient.addPrescription(prescription);
            patientRepository.persist(patient);
        });

        return createdPrescriptionIdentifier;
    }
}
