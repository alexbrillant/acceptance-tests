package ca.ulaval.glo4002.med.applicationServices.shared.locator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ServiceLocatorTest {

    @Before
    public void clearServiceLocator() {
        ServiceLocator.reset();
    }

    @Test(expected = UnregisteredServiceException.class)
    public void cannotResolveAServiceThatIsNotRegistered() {
        ServiceLocator.getInstance().resolve(SampleService.class);
    }

    @Test(expected = DoubleServiceRegistrationException.class)
    public void cannotRegisterTheSameServiceTwice() {
        ServiceLocator.getInstance().register(SampleService.class, new SampleImplementation());
        ServiceLocator.getInstance().register(SampleService.class, new SampleImplementation());
    }

    @Test
    public void canResolveAServiceWhenRegisteredAsSingleton() {
        ServiceLocator.getInstance().register(SampleService.class, new SampleImplementation());

        SampleService implementation = ServiceLocator.getInstance().resolve(SampleService.class);

        assertThat(implementation, instanceOf(SampleImplementation.class));
    }

    private interface SampleService {

    }

    private class SampleImplementation implements SampleService {

    }
}
