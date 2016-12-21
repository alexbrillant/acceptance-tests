package ca.ulaval.glo4002.med.interfaces.rest.resources.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerProvider;

public class EntityManagerContextFilter implements Filter {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        entityManagerFactory = EntityManagerFactoryProvider.getFactory();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        EntityManager entityManager = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityManagerProvider.setEntityManager(entityManager);
            chain.doFilter(request, response);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            EntityManagerProvider.clearEntityManager();
        }

    }

    @Override
    public void destroy() {
        entityManagerFactory.close();
    }

}
