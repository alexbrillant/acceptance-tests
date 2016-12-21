package ca.ulaval.glo4002.med.acctests.steps;

import ca.ulaval.glo4002.med.acctests.context.AccTestsContext;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;
import org.junit.Ignore;

@Ignore
public class ExecuteSteps implements Fr {

    @Before
    public void beforeScenario() throws Exception {
    }

    public ExecuteSteps() {
        Étantdonné("^une patiente Alice$", () -> {
            throw new PendingException();
        });

        Étantdonné("^une nouvelle ordonnance d'acétaminophène$", () -> {
            throw new PendingException();
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
            throw new PendingException();
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
