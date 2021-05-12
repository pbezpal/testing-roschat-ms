package chat.ros.testing2;

import chat.ros.testing2.server.settings.UserPage;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class StartWebDriver extends UserPage implements BeforeAllCallback {

    public final String hostServer = "https://" + System.getProperty("hostserver") + ":" + System.getProperty("portms");
    private static TestsBase testsBase;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        testsBase = new TestsBase();
        testsBase.init();
        TestStatusResult.setTestResult(false);
    }

    public static TestsBase getInstanceTestBase(){
        return testsBase;
    }
}
