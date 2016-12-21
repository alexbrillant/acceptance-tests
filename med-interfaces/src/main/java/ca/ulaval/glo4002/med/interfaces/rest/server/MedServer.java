package ca.ulaval.glo4002.med.interfaces.rest.server;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import ca.ulaval.glo4002.med.context.ContextBase;
import ca.ulaval.glo4002.med.interfaces.rest.resources.filters.EntityManagerContextFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class MedServer {

    private final static String RESOURCES_PACKAGE = "ca.ulaval.glo4002.med.interfaces.rest.resources";

    private ContextBase context;
    private int port;

    private Server server;

    public MedServer(int port, ContextBase context) {
        this.port = port;
        this.context = context;
    }

    public void start() throws Exception {
        server = new Server(port);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        configurerJersey(servletContextHandler);

        context.apply();

        server.start();
    }

    private void configurerJersey(ServletContextHandler servletContextHandler) {
        ServletContainer container = new ServletContainer(new ResourceConfig().packages(RESOURCES_PACKAGE).register(JacksonFeature.class));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public void stop() throws Exception {
        if (server.isRunning()) {
            server.stop();
        }
    }

}
