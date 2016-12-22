package ca.ulaval.glo4002.med.acctests.steps;

import ca.ulaval.glo4002.med.acctests.fixtures.patient.PatientMediumFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.prescription.PrescriptionFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.prescription.PrescriptionRestFixture;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;

public class ExecuteSteps implements Fr {

    PatientMediumFixture patientMediumFixture;
    PatientIdentifier currentPatientIdentifier;
    PrescriptionFixture prescriptionFixture;
    private static int currentPatientNumber = 0;

    @Before
    public void beforeScenario() throws Exception {
        patientMediumFixture = new PatientMediumFixture();
        prescriptionFixture = new PrescriptionRestFixture();
        nextPatientIdentifier();
    }

    private void nextPatientIdentifier() {
        currentPatientNumber++;
        String identifier = Integer.toString(currentPatientNumber);
        currentPatientIdentifier = new PatientIdentifier(identifier);
    }

    public ExecuteSteps() {
        Étantdonné("^une patiente Alice$", () -> {
            ;
            patientMediumFixture.givenPatient(currentPatientIdentifier);
        });

        Étantdonné("^une nouvelle ordonnance d'acétaminophène$", () -> {
            prescriptionFixture.givenValidPrescription(currentPatientIdentifier);
            prescriptionFixture.addPrescriptionToPatient(currentPatientIdentifier);
        });

        Quand("^Alice demande a exécuter l'ordonnance d'acétaminophène$", () -> {
            throw new PendingException();
        });

        Alors("^l'exécution est autorisée$", () -> {
            throw new PendingException();
        });

        Alors("^la date d'exécution est conservée$", () -> {
            throw new PendingException();
        });

        Étantdonné("^une ordonnance d'acétaminophène au dossier de Alice avec (\\d+) répétitions$", (Integer renewal) -> {
            for (int i = 0; i < renewal; ++i) {
                prescriptionFixture.givenValidPrescription(currentPatientIdentifier);
                prescriptionFixture.addPrescriptionToPatient(currentPatientIdentifier);
            }
        });

        Quand("^Alice demande a exécuter l'ordonnance d'acétaminophène pour la (\\d+)e fois$", (Integer renewal) -> {
            throw new PendingException();
        });

        Quand("^Alice demande à exécuter l'ordonnance d'acétaminophène le (\\d+)-(\\d+)-(\\d+)$",
                (Integer year, Integer month, Integer day) -> {
                    throw new PendingException();
        });

        Alors("^l'exécution est refusée$", () -> {
            throw new PendingException();
        });

        Étantdonné("^une ordonnance d'acétaminophène au dossier de Alice expirant le '(\\d+)-(\\d+)-(\\d+)'$",
                (Integer year, Integer month, Integer day) -> {
            throw new PendingException();
        });
    }
}
