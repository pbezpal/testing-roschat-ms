package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;

public class ResourcesFaxPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();
        if(classTest.contains("TestFaxPage")){
            if(methodTest.equals("test_Add_Number_Fax_Without_Description") || methodTest.equals("test_Add_Contact_For_Fax"))
                TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        String classTest = context.getTestClass().toString();
        TestStatusResult.setTestResult(false);
        if(classTest.contains("TestFaxPage")){
            if(methodTest.equals("test_Delete_Number_Fax_Without_Description"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Number_Fax_Without_Description"),
                        "The number fax without description don't add. Skip the test!");
            else if(methodTest.equals("test_Add_User_For_Fax"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Contact_For_Fax"),
                        "The contact for fax don't created. Skip the test!");
            if(methodTest.equals("test_Add_Contact_For_Fax"))
                getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
            else
                getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Сервисы","Факс");
        }
    }
}
