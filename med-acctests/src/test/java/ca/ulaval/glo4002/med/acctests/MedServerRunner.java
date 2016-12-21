package ca.ulaval.glo4002.med.acctests;

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
        // ???
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