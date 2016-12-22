package ca.ulaval.glo4002.med.acctests.steps;

import ca.ulaval.glo4002.med.acctests.context.LargeContext;
import ca.ulaval.glo4002.med.acctests.fixtures.patient.PatientMediumFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.prescription.PrescriptionFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.prescription.PrescriptionRestFixture;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;

import static ca.ulaval.glo4002.med.acctests.context.MediumContext.EXISTENT_PATIENT_IDENTIFIER;

public class SampleSteps implements Fr {

    private PrescriptionFixture prescriptionFixture;
    private PatientMediumFixture patientFixture;

    @Before
    public void beforeScenario() {
        new LargeContext().apply();
        prescriptionFixture = new PrescriptionRestFixture();
        patientFixture = new PatientMediumFixture();
    }

    public SampleSteps() {

        Étantdonné("^une ordonnance valide pour Alice$", () -> {
            prescriptionFixture.createValidPrescription(EXISTENT_PATIENT_IDENTIFIER);
        });

        Quand("^Alice ajoute l'ordonnance à son dossier$", () -> {
            prescriptionFixture.addPrescriptionToPatient(EXISTENT_PATIENT_IDENTIFIER);
        });

        Alors("^l'ordonnance est ajoutée à son dossier$", () -> {
            PrescriptionIdentifier prescriptionIdentifier = prescriptionFixture.getCreatedPrescriptionIdentifier();
            patientFixture.thenPatientHasPrescription(EXISTENT_PATIENT_IDENTIFIER, prescriptionIdentifier);
        });

        Alors("^une confirmation lui est signalée$", () -> {
            prescriptionFixture.thenPrescriptionIsConfirmed();
        });

        Étantdonné("^une ordonnance invalide pour Alice$", () -> {
            prescriptionFixture.createInvalidPrescription(EXISTENT_PATIENT_IDENTIFIER);
        });

        Alors("^l'ordonnance n'est pas ajoutée à son dossier$", () -> {
            PrescriptionIdentifier prescriptionIdentifier = prescriptionFixture.getCreatedPrescriptionIdentifier();
            patientFixture.thenPatientDoesntHaveAnyPrescription(EXISTENT_PATIENT_IDENTIFIER, prescriptionIdentifier);
        });

        Alors("^une erreur lui est signalée$", () -> {
            prescriptionFixture.thenPrescriptionHasAnError();
        });
    }
}
