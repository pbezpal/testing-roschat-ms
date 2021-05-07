package chat.ros.testing2;

import chat.ros.testing2.server.contacts.UserPage;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public abstract class StartWebDriver extends UserPage implements BeforeAllCallback {

    private static TestsBase testsBase = new TestsBase();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        testsBase.init();
        TestStatusResult.setTestResult(false);
    }

    public static TestsBase getInstanceTestBase(){
        return testsBase;
    }
}
