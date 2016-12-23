package ca.ulaval.glo4002.med.acctests.context;

import ca.ulaval.glo4002.med.acctests.runners.MedServerRunner;
import ca.ulaval.glo4002.med.context.ContextBase;

public class AccTestContext extends ContextBase {

    @Override
    protected void registerServices() {
        try {
            new MedServerRunner().start();
        } catch (Exception e) {
            // nothing to do here
        }
    }

    @Override
    protected void applyFillers() {

    }
}
