package chat.ros.testing2.server;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ResourcesServerPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        TestStatusResult.setTestResult(false);
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();
        if (classTest.contains("TestGeozonesPage")) {
            if (methodTest.equals("test_Open_Page")) getInstanceTestBase().openMS(USER_LOGIN_ADMIN,
                    USER_PASSWORD_ADMIN,
                    "/settings/geozones");
            else{
                if(methodTest.equals("test_Add_Beacon"))
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Geozone"),
                            "Geozone add test failed. Skip the test!");
                getInstanceTestBase().openMS(USER_LOGIN_ADMIN,
                    USER_PASSWORD_ADMIN,
                    "Настройки", "Геозоны");
            }
        } else if (classTest.contains("TestMailPage")) {
            if (methodTest.equals("Open_Page")) getInstanceTestBase().openMS(USER_LOGIN_ADMIN,
                    USER_PASSWORD_ADMIN,
                    "/settings/mail");
            else getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Почта");
        } else if (classTest.contains("TestServerPage")) {
            if (methodTest.equals("test_Open_Page")) getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"/settings/web-server");
            else getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Сервер");
        } else if (classTest.contains("TestSNMPPage")) {
            if (methodTest.equals("test_Open_Page")) getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"/settings/snmp");
            else getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "SNMP");
        } else if (classTest.contains("TestTelephonyPage")) {
            if (methodTest.equals("test_Open_Page")) getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"/settings/telephony");
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Телефония");
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String methodTest = context.getRequiredTestMethod().getName();
        String classTest = context.getTestClass().toString();
        if(classTest.contains("TestGeozonesPage")) {
            if (methodTest.equals("test_Add_Geozone"))
                TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusResult());
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        closeWebDriver();
    }
}
