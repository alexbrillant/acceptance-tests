package ca.ulaval.glo4002.med.acctests.runners;

import ca.ulaval.glo4002.med.acctests.context.MediumContext;
import ca.ulaval.glo4002.med.interfaces.rest.server.MedServer;

public class MedServerRunner {
    public static final int JETTY_TEST_PORT = 15146;

    private MedServer server;

    public void start() throws Exception {
        Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
        startJettyServer();
    }

    private void startJettyServer() throws Exception {
        server = new MedServer(JETTY_TEST_PORT, new MediumContext());
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