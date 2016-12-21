package ca.ulaval.glo4002.med.acctests;

import ca.ulaval.glo4002.med.applicationServices.shared.locator.ServiceLocator;
import ca.ulaval.glo4002.med.context.ContextBase;

public class AccTestsContext extends ContextBase {

    public void reinitialize() {
        ServiceLocator.reset();
        apply();
    }

    @Override
    protected void registerServices() {
        // ???
    }

    @Override
    protected void applyFillers() {
        // ???
    }

}
