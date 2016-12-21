package ca.ulaval.glo4002.med.acctests.runners;

import ca.ulaval.glo4002.med.acctests.context.AccTestsContext;
import ca.ulaval.glo4002.med.interfaces.rest.server.MedServer;
import org.junit.Before;

public class MedServerRunner {
    public static final int JETTY_TEST_PORT = 15146;

    private static boolean isFirstFeature = true;
    private MedServer server;

    @Before
    public void beforeAll() throws Exception {
        if (isFirstFeature) {
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startJettyServer();
            isFirstFeature = false;
        }
    }

    private void startJettyServer() throws Exception {
        server = new MedServer(JETTY_TEST_PORT, new AccTestsContext());
        server.start();
    }

    private class JettyServerShutdown extends Thread {
        public void run() {
            try {
                server.stop();
            } catch (Exception e) {
                // Nothing do to anyways
            }
        }
    }
}