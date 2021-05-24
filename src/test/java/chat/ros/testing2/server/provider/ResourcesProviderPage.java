package chat.ros.testing2.server.provider;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;

public class ResourcesProviderPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();
        if(methodTest.equals("test_Add_Provider_Without_Registration"))
            TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(false);
        if(classTest.contains("TestProviderWithoutRegPage")){
            if(methodTest.equals("test_Add_Rout_Of_Provider_Without_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_Without_Registration"),
                        "The provider without registration don't add. Skip the test!");
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Телефония");
        }
    }
}
