package ca.ulaval.glo4002.med.acctests.steps;

import ca.ulaval.glo4002.med.acctests.context.LargeContext;
import ca.ulaval.glo4002.med.acctests.fixtures.executePrescription.ExecutePrescriptionFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.executePrescription.ExecutePrescriptionRestFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.patient.PatientMediumFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.createPrescription.CreatePrescriptionFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.createPrescription.CreatePrescriptionRestFixture;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;

import java.time.LocalDate;
import java.time.ZoneId;

public class ExecuteSteps implements Fr {

    public static final LocalDate LOCAL_DATE = LocalDate.now();

    private PatientMediumFixture patientMediumFixture;
    private CreatePrescriptionFixture createPrescriptionFixture;
    private ExecutePrescriptionFixture executePrescriptionFixture;

    private PatientIdentifier patientIdentifier;
    private PrescriptionIdentifier prescriptionIdentifier;

    private static int currentPatientNumber = 0;

    @Before
    public void beforeScenario() throws Exception {
        new LargeContext().apply();
        patientMediumFixture = new PatientMediumFixture();
        createPrescriptionFixture = new CreatePrescriptionRestFixture();
        executePrescriptionFixture = new ExecutePrescriptionRestFixture();
        nextPatientIdentifier();
    }

    private void nextPatientIdentifier() {
        currentPatientNumber++;
        String identifier = Integer.toString(currentPatientNumber);
        patientIdentifier = new PatientIdentifier(identifier);
    }

    public ExecuteSteps() {
        Étantdonné("^une patiente Alice$", () -> {
            patientMediumFixture.givenPatient(patientIdentifier);
        });

        Étantdonné("^une nouvelle ordonnance d'acétaminophène$", () -> {
            prescriptionIdentifier = createPrescriptionFixture.givenAddedPrescription(patientIdentifier);
        });

        Quand("^Alice demande a exécuter l'ordonnance d'acétaminophène$", () -> {
            executePrescriptionFixture.executePrescription(patientIdentifier, prescriptionIdentifier, LOCAL_DATE);
        });

        Alors("^l'exécution est autorisée$", () -> {
            executePrescriptionFixture.thenPrescriptionIsExecuted();
        });

        Alors("^la date d'exécution est conservée$", () -> {
            patientMediumFixture.thenPatientHasPrescriptionWithExecutionDate(patientIdentifier, prescriptionIdentifier);
        });

        Étantdonné("^une ordonnance d'acétaminophène au dossier de Alice avec (\\d+) répétitions$",
                (Integer renewals) -> {
            prescriptionIdentifier = createPrescriptionFixture
                    .givenAddedPrescriptionWithRenewals(patientIdentifier, renewals, LOCAL_DATE);
        });

        Quand("^Alice demande a exécuter l'ordonnance d'acétaminophène pour la (\\d+)e fois$",
                (Integer renewal) -> {
            for (int i = 0; i < renewal; i++) {
                executePrescriptionFixture.executePrescription(patientIdentifier, prescriptionIdentifier, LOCAL_DATE);
            }
        });

        Quand("^Alice demande à exécuter l'ordonnance d'acétaminophène le (\\d+)-(\\d+)-(\\d+)$",
                (Integer year, Integer month, Integer day) -> {
             LocalDate localDate = LocalDate.parse(String.format("%d-%d-%d", year, month, day));
             executePrescriptionFixture.executePrescription(patientIdentifier, prescriptionIdentifier, localDate);
        });

        Alors("^l'exécution est refusée$", () -> {
            executePrescriptionFixture.thenPrescriptionExecutionIsRefused();
        });

        Étantdonné("^une ordonnance d'acétaminophène au dossier de Alice expirant le '(\\d+)-(\\d+)-(\\d+)'$",
                (Integer year, Integer month, Integer day) -> {
            LocalDate localDate = LocalDate.parse(String.format("%d-%d-%d", year, month, day));

            createPrescriptionFixture.givenAddedPrescriptionWithRenewals(patientIdentifier, 1, localDate);
        });
    }
}
