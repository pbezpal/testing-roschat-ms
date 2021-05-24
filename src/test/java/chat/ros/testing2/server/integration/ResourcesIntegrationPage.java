package chat.ros.testing2.server.integration;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;

public class ResourcesIntegrationPage extends StartWebDriver implements BeforeEachCallback, AfterEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        String classTest = context.getTestClass().toString();
        TestStatusResult.setTestResult(false);
        if (classTest.contains("TestActiveDirectoryPage")) {
            if (!methodTest.equals("test_Add_Service_Active_Directory"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Service_Active_Directory"),
                        "Service AD add test failed. Skip the test!");
        } else {
            beforeEachSkud(classTest, methodTest, "OM");
            beforeEachSkud(classTest, methodTest, "Orion");
            beforeEachSkud(classTest, methodTest, "Perco");
        }

        if (classTest.contains("TestIntegrationPage") && methodTest.equals("test_Open_Page"))
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "/settings/integration");
        else if (methodTest.contains("test_Status_Active_SKUD")
                || methodTest.contains("test_Status_Inactive_SKUD")
                || methodTest.contains("test_Status_SKUD_After_Delete_SKUD")
                || methodTest.contains("test_Status_Skud_Before_Add_Skud_OM"))
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Монитор");
        else
            getInstanceTestBase().openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Настройки", "Интеграция");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        String classTest = context.getTestClass().toString();
        if(methodTest.equals("test_Add_Service_Active_Directory"))
            TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
        if(classTest.contains("TestMonitorSkud")){
            if(methodTest.contains("test_Add_SKUD"))
                TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
            else if(methodTest.contains("test_Sync_Contacts_SKUD"))
                TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
            else if(methodTest.contains("test_Change_Data_Disconnect"))
                TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
            else if(methodTest.contains("test_Delete_SKUD"))
                TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
        }
    }

    private void beforeEachSkud(String classTest, String methodTest, String SKUD){
        if(classTest.contains("TestMonitorSkud" + SKUD)) {
            if (methodTest.equals("test_Sync_Contacts_SKUD_" + SKUD))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_SKUD_" + SKUD),
                        "SKUD " + SKUD + " add test failed. Skip the test!");
            else if (methodTest.equals("test_Status_Active_SKUD_" + SKUD))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Sync_Contacts_SKUD_" + SKUD),
                        "SKUD " + SKUD + " sync contacts test failed. Skip the test!");
            else if (methodTest.equals("test_Change_Data_Disconnect_SKUD_" + SKUD))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Sync_Contacts_SKUD_" + SKUD),
                        "SKUD " + SKUD + " sync contacts test failed. Skip the test!");
            else if (methodTest.equals("test_Status_Inactive_SKUD_" + SKUD))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Change_Data_Disconnect_SKUD_" + SKUD),
                        "SKUD " + SKUD + " change disconnect test failed. Skip the test!");
            else if (methodTest.equals("test_Delete_SKUD_" + SKUD))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_SKUD_" + SKUD),
                        "SKUD " + SKUD + " add test failed. Skip the test!");
            else if (methodTest.equals("test_Status_SKUD_After_Delete_SKUD_" + SKUD))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Delete_SKUD_" + SKUD),
                        "SKUD " + SKUD + " add test failed. Skip the test!");
        }
    }
}
