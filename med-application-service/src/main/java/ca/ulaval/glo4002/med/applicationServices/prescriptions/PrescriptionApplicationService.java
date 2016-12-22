package ca.ulaval.glo4002.med.applicationServices.prescriptions;

import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionFactory;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PrescriptionApplicationService {

    private Clock clock;
    private PatientRepository patientRepository;
    private PrescriptionFactory prescriptionFactory;

    // for testing
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public PrescriptionApplicationService() {
        patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
        prescriptionFactory = ServiceLocator.getInstance().resolve(PrescriptionFactory.class);
        clock = ServiceLocator.getInstance().resolve(Clock.class);
    }

    public PrescriptionApplicationService(PatientRepository patientRepository, PrescriptionFactory prescriptionAssembler) {
        this.patientRepository = patientRepository;
        this.prescriptionFactory = prescriptionAssembler;
    }

    public PrescriptionIdentifier addPrescription(PatientIdentifier patientIdentifier, PrescriptionForm form) {
        validateForm(form);

        Patient patient = patientRepository.findByIdentifier(patientIdentifier);
        Prescription prescription = prescriptionFactory.create(form.din, form.expirationDate, form.physician, form.renewals);

        patient.addPrescription(prescription);
        patientRepository.persist(patient);

        return prescription.getIdentifier();
    }

    private void validateForm(PrescriptionForm form) {
        if (!form.isValid()) {
            throw new InvalidPrescriptionFormApplicationException();
        }
    }

    public void executePrescription(PatientIdentifier patientIdentifier, PrescriptionIdentifier prescriptionIdentifier) {
        Patient patient = patientRepository.findByIdentifier(patientIdentifier);
        Date currentDate = getCurrentDate();
        patient.executePrescription(prescriptionIdentifier, currentDate);
        patientRepository.persist(patient);
    }

    private Date getCurrentDate() {
        return Date.from(LocalDate.now(clock).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
