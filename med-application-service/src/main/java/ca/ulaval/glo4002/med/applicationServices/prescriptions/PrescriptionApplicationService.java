package ca.ulaval.glo4002.med.applicationServices.prescriptions;

import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.core.patients.Patient;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.patients.PatientRepository;
import ca.ulaval.glo4002.med.core.prescriptions.Prescription;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionFactory;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;

public class PrescriptionApplicationService {

    private PatientRepository patientRepository;
    private PrescriptionFactory prescriptionFactory;

    public PrescriptionApplicationService() {
        patientRepository = ServiceLocator.getInstance().resolve(PatientRepository.class);
        prescriptionFactory = ServiceLocator.getInstance().resolve(PrescriptionFactory.class);
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
        patient.executePrescription(prescriptionIdentifier);
    }
}
