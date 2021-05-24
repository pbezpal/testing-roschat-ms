package chat.ros.testing2;

import chat.ros.testing2.server.settings.UserPage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class StartWebDriver extends UserPage implements BeforeAllCallback, AfterAllCallback {

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

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Selenide.closeWindow();
        Selenide.closeWebDriver();
    }
}
