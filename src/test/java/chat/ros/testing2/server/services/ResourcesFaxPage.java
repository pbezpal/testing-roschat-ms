package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ResourcesFaxPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusResult());
        switch (methodTest){
            case "test_Add_Contact_For_Fax":
            case "test_Check_Title_Text_Modal_Window_When_Add_Users_Fax":
            case "test_Check_Title_Text_Modal_Window_When_Add_Number_Fax":
            case "test_Create_Contact_For_Alert":
            case "test_Info_Text_With_Link_Of_Users_Fax":
            case "test_Add_Number_Fax_With_Description":
            case "test_Add_Number_Fax_Without_Description":
            case "test_Delete_Number_Fax_Without_Description":
            case "test_Add_User_For_Fax":
            case "test_Delete_User_For_Fax":
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
        TestStatusResult.setTestResult(false);
        if(classTest.contains("TestFaxPage")){
            switch (methodTest){
                case "test_Delete_Number_Fax_Without_Description":
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Number_Fax_Without_Description"),
                            "The number fax without description don't add. Skip the test!");
                    break;
                case "test_Add_User_For_Fax":
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Contact_For_Fax"),
                            "The fax's contact not been created. Skip the test!");
                    break;
                case "test_Check_Auth_System_Fax_With_Wrong_Login":
                case "test_Check_Auth_System_Fax_With_Wrong_Password":
                case "test_Check_Auth_System_Fax_After_Add_User_Fax_With_Stay_System":
                case "test_Delete_User_For_Fax":
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_User_For_Fax"),
                            "No fax user has been added. Skip the test!");
                    break;
                case "test_Check_Auth_System_Fax_After_Delete_User_Fax":
                    Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Delete_User_For_Fax"),
                            "The user for fax not been deleted. Skip the test!");
            }

            switch (methodTest){
                case "test_Add_Contact_For_Fax":
                    getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
                    break;
                default:
                    getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Сервисы","Факс");
            }
        }
    }
}
