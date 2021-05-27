package chat.ros.testing2.server.provider;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.TestsBase;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;

public class ResourcesProviderPage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    private TestsBase testsBase = new TestsBase();

    @Override
    public void afterEach(ExtensionContext context) {
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(false);
        if(classTest.contains("TestProviderWithoutRegPage")) {
            if (methodTest.equals("test_Add_Rout_In_With_Simple_Mode")
                    || methodTest.equals("test_Add_Rout_Out_With_Expert_Mode")
                    || methodTest.equals("test_Show_Settings_Provider_Without_Reg")
                    || methodTest.equals("test_Edit_Provider_Without_Reg")
                    || methodTest.equals("test_Edit_Provider_With_Reg")
                    || methodTest.equals("test_Delete_Provider_With_Reg")
            )
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_Without_Registration"),
                        "The provider without registration don't add. Skip the test!");
            else if (methodTest.equals("test_Show_Settings_Provider_Without_Reg_After_Edit"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_Without_Reg"),
                        "The provider without registration don't edit. Skip the test!");
            else if (methodTest.equals("test_Exist_Provider_Wit_Registration"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_With_Reg"),
                        "The turning on registration of provider didn't edit. Skip the test!");
        }

        if(classTest.contains("TestProviderWithRegPage")) {
            if (methodTest.equals("test_Add_Rout_In_With_Simple_Mode")
                    || methodTest.equals("test_Add_Rout_Out_With_Expert_Mode")
                    || methodTest.equals("test_Show_Settings_Provider_With_Reg")
                    || methodTest.equals("test_Edit_Provider_With_Reg")
                    || methodTest.equals("test_Edit_Provider_Without_Reg")
                    || methodTest.equals("test_Delete_Provider_Without_Reg")
            )
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_With_Registration"),
                        "The provider with registration don't add. Skip the test!");
            else if (methodTest.equals("test_Show_Settings_Provider_With_Reg_After_Edit"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_With_Reg"),
                        "The turning on registration of provider didn't edit. Skip the test!");
            else if (methodTest.equals("test_Exist_Provider_Without_Registration_After_Edit"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_Without_Reg"),
                        "The provider without registration don't edit. Skip the test!");
        }

        if (methodTest.equals("test_Edit_Route_Out_With_Expert_Mode")
                || methodTest.equals("test_Delete_Route_Out_With_Expert_Mode"))
            Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Rout_Out_With_Expert_Mode"),
                    "The outbox route didn't add. Skip the test!");
        else if (methodTest.equals("test_Edit_Route_In_Update_From_Simple_Mode_To_Expert_mode")
                || methodTest.equals("test_Delete_Route_In_Simple_Mode"))
            Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Rout_In_With_Simple_Mode"),
                    "The inbox route didn't add. Skip the test!");

        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Настройки", "Телефония");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        testsBase.init();
    }
}
