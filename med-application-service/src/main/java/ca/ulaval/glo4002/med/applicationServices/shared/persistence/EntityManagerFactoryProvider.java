package ca.ulaval.glo4002.med.applicationServices.shared.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {

    private static EntityManagerFactory instance;

    public static EntityManagerFactory getFactory() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory("med-persistence");
        }
        return instance;
    }

    private EntityManagerFactoryProvider() {
    }
}
