package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;

public class ResourcesFaxPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback, AfterAllCallback {
    @Override
    public void afterAll(ExtensionContext context) throws Exception {

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {

    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String testMethod = context.getRequiredTestMethod().getName();
        String testClass = context.getTestClass().toString();
        TestStatusResult.setTestResult(false);
        if(testClass.contains("TestFaxPage")){
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Сервисы","Факс");
        }
    }
}
