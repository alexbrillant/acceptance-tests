package ca.ulaval.glo4002.med.acctests.steps;

import ca.ulaval.glo4002.med.acctests.context.LargeContext;
import ca.ulaval.glo4002.med.acctests.fixtures.createPrescription.CreatePrescriptionMediumFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.executePrescription.ExecutePrescriptionFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.executePrescription.ExecutePrescriptionMediumFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.patient.PatientMediumFixture;
import ca.ulaval.glo4002.med.acctests.fixtures.createPrescription.CreatePrescriptionFixture;
import ca.ulaval.glo4002.med.core.patients.PatientIdentifier;
import ca.ulaval.glo4002.med.core.prescriptions.PrescriptionIdentifier;
import cucumber.api.java.Before;
import cucumber.api.java8.Fr;

import java.time.LocalDate;

public class ExecuteSteps implements Fr {

    public static final LocalDate LOCAL_DATE = LocalDate.now();

    private PatientMediumFixture patientMediumFixture;
    private CreatePrescriptionFixture createPrescriptionFixture;
    private ExecutePrescriptionFixture executePrescriptionFixture;

    private PatientIdentifier patientId;
    private PrescriptionIdentifier prescriptionId;

    private static int currentPatientNumber = 0;

    @Before
    public void beforeScenario() throws Exception {
        new LargeContext().apply();
        patientMediumFixture = new PatientMediumFixture();
        createPrescriptionFixture = new CreatePrescriptionMediumFixture();
        executePrescriptionFixture = new ExecutePrescriptionMediumFixture();
        nextPatientIdentifier();
    }

    private void nextPatientIdentifier() {
        currentPatientNumber++;
        String identifier = Integer.toString(currentPatientNumber);
        patientId = new PatientIdentifier(identifier);
    }

    public ExecuteSteps() {
        Étantdonné("^une patiente Alice$", () -> {
            patientMediumFixture.givenPatient(patientId);
        });

        Étantdonné("^une nouvelle ordonnance d'acétaminophène$", () -> {
            prescriptionId = createPrescriptionFixture.givenPrescriptionForPatient(patientId);
        });

        Étantdonné("^une ordonnance d'acétaminophène au dossier de Alice avec (\\d+) répétitions$", (Integer renewals) -> {
            prescriptionId = createPrescriptionFixture
                    .givenPrescriptionForPatient(patientId, renewals, LOCAL_DATE);
        });

        Étantdonné("^une ordonnance d'acétaminophène au dossier de Alice expirant le '(\\d+)-(\\d+)-(\\d+)'$", (Integer year, Integer month, Integer day) -> {
            LocalDate localDate = LocalDate.parse(String.format("%d-%d-%d", year, month, day));
            prescriptionId = createPrescriptionFixture.givenPrescriptionForPatient(patientId, 1, localDate);
        });

        Quand("^Alice demande a exécuter l'ordonnance d'acétaminophène$", () -> {
            executePrescriptionFixture.executePrescription(patientId, prescriptionId, LOCAL_DATE);
        });

        Quand("^Alice demande a exécuter l'ordonnance d'acétaminophène pour la (\\d+)e fois$", (Integer renewal) -> {
            for (int i = 0; i < renewal; i++) {
                executePrescriptionFixture.executePrescription(patientId, prescriptionId, LOCAL_DATE);
            }
        });

        Quand("^Alice demande à exécuter l'ordonnance d'acétaminophène le (\\d+)-(\\d+)-(\\d+)$", (Integer year, Integer month, Integer day) -> {
            LocalDate localDate = LocalDate.parse(String.format("%d-%d-%d", year, month, day));
            executePrescriptionFixture.executePrescription(patientId, prescriptionId, localDate);
        });

        Alors("^l'exécution est refusée$", () -> {
            executePrescriptionFixture.thenPrescriptionExecutionIsRefused();
        });

        Alors("^l'exécution est autorisée$", () -> {
            executePrescriptionFixture.thenPrescriptionIsExecuted();
        });

        Alors("^la date d'exécution est conservée$", () -> {
            patientMediumFixture.thenPatientHasPrescriptionWithExecutionDate(patientId, prescriptionId);
        });
    }
}
