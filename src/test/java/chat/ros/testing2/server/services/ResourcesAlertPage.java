package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
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
        switch (methodTest){
            case "test_Create_Contact_For_Alert":
            case "test_Settings_Email_For_Alert":
            case "test_Add_Test_User_For_Alert":
            case "test_Add_Contact_For_SIP_And_MAIL_Alert":
            case "test_Text_Of_SIP_Alert":
            case "test_Text_Of_EMail_Alert":
            case "test_Text_Of_SMS_Alert":
            case "test_Check_Info_Text_With_Link_To_Go_MS_Alert_Page":
                break;
            default:
                switchTo().window(getWebDriver().getWindowHandle()).close();
                switchTo().window(0);
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        String classTest = context.getTestClass().toString();
        switch (methodTest){
            case "test_Check_Auth_Alert_System_Before_Add_User_Alert":
            case "test_Add_Test_User_For_Alert":
            case "test_Add_Contact_For_SIP_And_MAIL_Alert":
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Create_Contact_For_Alert"),
                        "The alert's contact not been created. Skip the test!");
                break;
            case "test_Check_Auth_System_Alert_With_Wrong_Login":
            case "test_Check_Auth_System_Alert_With_Wrong_Password":
            case "test_Check_Auth_Alert_System_After_Add_User_Alert_With_Stay_System":
            case "test_Check_Auth_Alert_System_After_Add_User_Alert_Without_Stay_System":
            case "test_Check_Status_Alert":
            case "test_Check_Elements_Left_Menu":
            case "test_Check_Elements_Top_Menu":
            case "test_Check_Status_Message_Before_Add_Contact_For_SIP_And_MAIL_Alert":
            case "test_Check_Status_Email_Before_Settings_EMail":
            case "test_Check_Status_SMS_Before_Settings_SMS":
            case "test_Check_Title_Modal_Window":
            case "test_Check_Title_Modal_Window_When_Add_Sound_File":
            case "test_Add_Sound_File":
            case "test_Add_Alert":
            case "test_Add_Message":
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Test_User_For_Alert"),
                        "The alert's user not been add in the alert. Skip the test!");
                break;
            case "test_Check_Status_Message_After_Add_Contact_For_SIP_And_MAIL_Alert":
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Test_User_For_Alert"),
                        "The alert's user not been add in the alert. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Contact_For_SIP_And_MAIL_Alert"),
                        "The alert's contact not been add in the sip and email alert. Skip the test!");
                break;
            case "test_Check_Status_Email_After_Settings_EMail":
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Test_User_For_Alert"),
                        "The alert's user not been add in the alert. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Settings_Email_For_Alert"),
                        "The mail not been settings. Skip the test!");
                break;
            case "test_Add_Task":
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Test_User_For_Alert"),
                        "The alert's user not been add in the alert. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Message"),
                        "The message not been add. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Alert"),
                        "The alert not been add. Skip the test!");
                break;
            case "test_Start_Task":
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Test_User_For_Alert"),
                        "The alert's user not been add in the alert. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Task"),
                        "The task not been add. Skip the test!");
        }
        switch (methodTest){
            case "test_Create_Contact_For_Alert":
                getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
                break;
            case "test_Settings_Email_For_Alert":
                getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Настройки", "Почта");
                break;
            default:
                getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Сервисы", "Система оповещения");
        }
    }
}
