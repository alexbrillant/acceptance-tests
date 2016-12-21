package ca.ulaval.glo4002.med.acctests.steps;

import ca.ulaval.glo4002.med.acctests.context.AccTestsContext;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;

public class SampleSteps implements Fr {

    @Before
    public void beforeScenario() {
        new AccTestsContext().reinitialize();
    }

    public SampleSteps() {
        Étantdonné("^une ordonnance valide pour Alice$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
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
