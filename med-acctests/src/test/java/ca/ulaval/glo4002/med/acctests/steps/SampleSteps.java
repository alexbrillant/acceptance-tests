package ca.ulaval.glo4002.med.acctests.steps;

import ca.ulaval.glo4002.med.acctests.context.AccTestsContext;
import ca.ulaval.glo4002.med.acctests.fixtures.PatientFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.PrescriptionFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.PrescriptionRestFixture;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;

public class SampleSteps implements Fr {

    private PrescriptionFixture prescriptionFixture;

    @Before
    public void beforeScenario() {
        prescriptionFixture = new PrescriptionRestFixture();
    }

    public SampleSteps() {

        Étantdonné("^une ordonnance valide pour Alice$", () -> {
            prescriptionFixture.createValidPrescription(AccTestsContext.EXISTENT_PATIENT_IDENTIFIER);
        });

        Quand("^Alice ajoute l'ordonnance à son dossier$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Alors("^l'ordonnance est ajoutée à son dossier$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Alors("^une confirmation lui est signalée$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Étantdonné("^une ordonnance invalide pour Alice$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Alors("^l'ordonnance n'est pas ajoutée à son dossier$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });

        Alors("^une erreur lui est signalée$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
