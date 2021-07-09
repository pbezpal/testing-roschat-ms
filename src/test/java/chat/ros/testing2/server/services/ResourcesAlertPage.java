package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ResourcesAlertPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusResult());
        if(methodTest.equals("test_Check_Auth_Alert_System_After_Add_User_Alert")
                || methodTest.equals("test_Check_Auth_Alert_System_Before_Add_User_Alert")
                || methodTest.equals("test_Check_Elements_In_Auth_Alert_Page")
        ){
            switchTo().window(getWebDriver().getWindowHandle()).close();
            switchTo().window(0);
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        String classTest = context.getTestClass().toString();
        if(methodTest.equals("test_Check_Auth_Alert_System_Before_Add_User_Alert")
                || methodTest.equals("test_Add_Test_User_For_Alert")
        )
            Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Create_Contact_For_Alert"),
                    "Add contact for alert don't create. Skip the test!");
        if(methodTest.equals("test_Create_Contact_For_Alert"))
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
        else
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Сервисы", "Система оповещения");
    }
}
