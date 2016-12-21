package ca.ulaval.glo4002.med.acctests.fixtures;


import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.med.applicationServices.shared.persistence.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

public class HibernateBaseFixture {
    protected void withEntityManager(Consumer<EntityTransaction> action) {
        EntityManager entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
        try {
            EntityManagerProvider.setEntityManager(entityManager);

            EntityTransaction transaction = entityManager.getTransaction();
            action.accept(transaction);
        } finally {
            entityManager.close();
        }
    }
}