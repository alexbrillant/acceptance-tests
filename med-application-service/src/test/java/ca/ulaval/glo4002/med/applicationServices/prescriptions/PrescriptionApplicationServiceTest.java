package ca.ulaval.glo4002.med.applicationServices.prescriptions;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionFactory;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrescriptionApplicationServiceTest {

    private static final PatientIdentifier PATIENT_IDENTIFIER = new PatientIdentifier("12455");
    private static final PrescriptionIdentifier PRESCRIPTION_IDENTIFIER = PrescriptionIdentifier.create();

    @Mock
    private Patient patient;

    @Mock
    private Prescription prescription;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PrescriptionFactory prescriptionFactory;

    @InjectMocks
    private PrescriptionApplicationService service;

    @Before
    public void createPatientAndPrescription() {
        willReturn(PRESCRIPTION_IDENTIFIER).given(prescription).getIdentifier();
        willReturn(patient).given(patientRepository).findByIdentifier(any(PatientIdentifier.class));
        willReturn(prescription).given(prescriptionFactory).create(anyString(), any(Date.class), anyString(), anyInt());
    }

    @Test(expected = InvalidPrescriptionFormApplicationException.class)
    public void addValidatesThatTheDinIsProvided() {
        PrescriptionForm form = createValidForm();
        form.din = null;

        service.addPrescription(PATIENT_IDENTIFIER, form);
    }

    @Test(expected = InvalidPrescriptionFormApplicationException.class)
    public void addValidatesThatTheDinIsNotEmpty() {
        PrescriptionForm form = createValidForm();
        form.din = "";

        service.addPrescription(PATIENT_IDENTIFIER, form);
    }

    @Test(expected = InvalidPrescriptionFormApplicationException.class)
    public void addValidatesThatTheDateIsProvided() {
        PrescriptionForm form = createValidForm();
        form.expirationDate = null;

        service.addPrescription(PATIENT_IDENTIFIER, form);
    }

    @Test(expected = InvalidPrescriptionFormApplicationException.class)
    public void addValidatesThatThePhysicianIsProvided() {
        PrescriptionForm form = createValidForm();
        form.physician = null;

        service.addPrescription(PATIENT_IDENTIFIER, form);
    }

    @Test(expected = InvalidPrescriptionFormApplicationException.class)
    public void addValidatesThatThePhysicianIsNotEmpty() {
        PrescriptionForm form = createValidForm();
        form.physician = "";

        service.addPrescription(PATIENT_IDENTIFIER, form);
    }

    @Test(expected = InvalidPrescriptionFormApplicationException.class)
    public void addValidatesThatTheNumberOfRenewalsIsGreaterOrEqualZero() {
        PrescriptionForm form = createValidForm();
        form.renewals = -1;

        service.addPrescription(PATIENT_IDENTIFIER, form);
    }

    @Test
    public void addPrescriptionFindsThePatient() {
        PrescriptionForm form = createValidForm();

        service.addPrescription(PATIENT_IDENTIFIER, form);

        verify(patientRepository).findByIdentifier(PATIENT_IDENTIFIER);
    }

    @Test
    public void addPrescriptionCreatesPrescriptionWithFactory() {
        PrescriptionForm form = createValidForm();

        service.addPrescription(PATIENT_IDENTIFIER, form);

        verify(prescriptionFactory).create(form.din, form.expirationDate, form.physician, form.renewals);
    }

    @Test
    public void addPrescriptionAddsTheCreatedPrescriptionToThePatient() {
        PrescriptionForm form = createValidForm();

        service.addPrescription(PATIENT_IDENTIFIER, form);

        verify(patient).addPrescription(prescription);
    }

    @Test
    public void addPrescriptionPersistsThePatientWithItsPrescription() {
        PrescriptionForm form = createValidForm();

        service.addPrescription(PATIENT_IDENTIFIER, form);

        InOrder order = inOrder(patient, patientRepository);
        order.verify(patient).addPrescription(any(Prescription.class));
        order.verify(patientRepository).persist(patient);
    }

    @Test
    public void addPrescriptionReturnsThePrescriptionIdentifier() {
        PrescriptionForm form = createValidForm();

        PrescriptionIdentifier prescriptionIdentifier = service.addPrescription(PATIENT_IDENTIFIER, form);

        assertEquals(prescription.getIdentifier(), prescriptionIdentifier);
    }

    private PrescriptionForm createValidForm() {
        PrescriptionForm form = new PrescriptionForm();
        form.physician = "019375";
        form.din = "dw8797";
        form.expirationDate = new Date();
        form.renewals = 2;
        return form;
    }

    @Test
    public void whenExecutingPrescription_shouldFindPatientAndExecuteItsPrescription() throws Exception {
        PrescriptionIdentifier prescriptionIdentifier = mock(PrescriptionIdentifier.class);
        PatientIdentifier patientIdentifier = mock(PatientIdentifier.class);

        service.executePrescription(patientIdentifier, prescriptionIdentifier);

        verify(patientRepository).findByIdentifier(patientIdentifier);
    }

    public void whenExecutingPrescription_shouldPersistPatient() throws Exception {
        PrescriptionIdentifier prescriptionIdentifier = mock(PrescriptionIdentifier.class);
        PatientIdentifier patientIdentifier = mock(PatientIdentifier.class);

        service.executePrescription(patientIdentifier, prescriptionIdentifier);

        verify(patientRepository).persist(patient);
    }
}
