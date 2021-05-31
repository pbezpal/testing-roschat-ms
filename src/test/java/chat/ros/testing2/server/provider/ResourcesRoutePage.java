package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.TestsBase;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;

public class ResourcesRoutePage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    private TestsBase testsBase = new TestsBase();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        testsBase.init();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();

        if(classTest.contains("TestProviderRouteSimpleMode")){
            if(methodTest.equals("test_Add_Rout_In_Simple_Mode") || methodTest.equals("test_Modal_Info_Of_Route"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider"),
                        "The provider for testing route in simple mode don't add. Skip the test!");
        }

        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Настройки", "Телефония");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
    }
}
