package ca.ulaval.glo4002.med.applicationServices.shared.locator;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceLocator {

    private static ServiceLocator instance;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private HashMap<Class<?>, Object> services;

    public static ServiceLocator getInstance() {
        // Théoriquement la seule façon 100% safe de faire ça. C'est volontairement laid
        // et overkill, ce n'est pas simple la concurrence même si ici c'est plus ou moins nécessaire =)
        if (instance == null) {
            LOCK.lock();
            try {
                if (instance == null) {
                    instance = new ServiceLocator();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    public static void reset() {
        // C'est un lock "pessimiste", mais on préfère ça vu que reset ne sera pas vraiment utilisé...
        LOCK.lock();
        try {
            instance = null;
        } finally {
            LOCK.unlock();
        }
    }

    private ServiceLocator() {
        services = new HashMap<Class<?>, Object>();
    }

    // Pour les 2 méthodes suivantes on laisse faire les lock pour simplifier.

    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> service) {
        if (!services.containsKey(service)) {
            throw new UnregisteredServiceException(service);
        }

        return (T) services.get(service);

    }

    public <T> void register(Class<T> service, T implementation) {
        if (services.containsKey(service)) {
            throw new DoubleServiceRegistrationException(service);
        }
        services.put(service, implementation);
    }

    // For Clock in acceptance tests
    public <T> void registerAgain(Class<T> service, T implementation) {
        if (services.containsKey(service)) {
            services.remove(service);
        }
        services.put(service, implementation);
    }
}
