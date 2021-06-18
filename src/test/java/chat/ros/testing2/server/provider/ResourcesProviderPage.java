package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.TestsBase;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ResourcesProviderPage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    private TestsBase testsBase = new TestsBase();

    @Override
    public void afterEach(ExtensionContext context) {
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusResult());
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(false);
        if(classTest.contains("TestProviderWithoutRegPage")) {
            if (methodTest.equals("test_Add_Incoming_Rout_In_Simple_Mode")
                    || methodTest.equals("test_Add_Outgoing_Rout_In_Expert_Mode")
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
            else if (methodTest.equals("test_Exist_Provider_With_Registration"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_With_Reg"),
                        "The turning on registration of provider didn't edit. Skip the test!");
            else if (methodTest.equals("test_Delete_Outgoing_Route_In_Expert_Mode"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Outgoing_Rout_In_Expert_Mode"),
                        "The outbox route didn't add. Skip the test!");
            else if (methodTest.equals("test_Delete_Incoming_Route_In_Simple_Mode"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Incoming_Rout_In_Simple_Mode"),
                        "The inbox route didn't add. Skip the test!");
            else if(methodTest.equals("test_Add_Incoming_Rout_In_Simple_Mode_After_Edit_Provider")
                    || methodTest.equals("test_Add_Outgoing_Rout_In_Expert_Mode_After_Edit_Provider"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_Without_Reg"),
                        "Test failed - editing a provider without registration. Skip the test!");
            else if(methodTest.equals("test_Delete_Outgoing_Route_In_Simple_Mode_After_Edit_Provider"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult()
                                .get("test_Add_Outgoing_Rout_In_Simple_Mode_After_Edit_Provider"),
                        "Test failed - adding a outgoing route after edit provider. Skip the test!");
            else if(methodTest.equals("test_Delete_Incoming_Route_In_Simple_Mode_After_Edit_Provider"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult()
                                .get("test_Add_Incoming_Rout_In_Expert_Mode_After_Edit_Provider"),
                        "Test failed - adding a incoming route after edit provider. Skip the test!");
            else if(methodTest.equals("test_Add_Incoming_Rout_In_Simple_Mode_After_Edit_Provider_With_Reg")
                    || methodTest.equals("test_Add_Outgoing_Rout_In_Expert_Mode_After_Edit_Provider_With_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_With_Reg"),
                        "Test failed - editing a provider with registration. Skip the test!");
            else if(methodTest.equals("test_Delete_Incoming_Route_In_Simple_Mode_After_Edit_Provider_With_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult()
                                .get("test_Add_Incoming_Rout_In_Simple_Mode_After_Edit_Provider_With_Reg"),
                        "Test failed - adding a incoming route after edit provider with registration. Skip the test!");
            else if(methodTest.equals("test_Delete_Outgoing_Route_In_Expert_Mode_After_Edit_Provider_With_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult()
                                .get("test_Add_Outgoing_Rout_In_Expert_Mode_After_Edit_Provider_With_Reg"),
                        "Test failed - adding a outgoing route after edit provider with registration. Skip the test!");
        }

        if(classTest.contains("TestProviderWithRegPage")) {
            if (methodTest.equals("test_Add_Incoming_Rout_In_Simple_Mode")
                    || methodTest.equals("test_Add_Outgoing_Rout_In_Expert_Mode")
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
            else if (methodTest.equals("test_Delete_Route_Out_With_Expert_Mode_Of_Provider_With_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Outgoing_Rout_In_Expert_Mode"),
                        "Test failed - adding a outgoing route. Skip the test!");
            else if (methodTest.equals("test_Delete_Incoming_Route_In_Simple_Mode"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Incoming_Rout_In_Simple_Mode"),
                        "Test failed - adding a incoming route. Skip the test!");
            else if (methodTest.equals("test_Add_Outgoing_Rout_In_Simple_Mode_After_Edit_Provider")
                    || methodTest.equals("test_Add_Incoming_Rout_In_Expert_Mode_After_Edit_Provider"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_With_Reg"),
                        "Test failed - editing a provider with registration. Skip the test!");
            else if(methodTest.equals("test_Delete_Outgoing_Route_In_Simple_Mode_After_Edit_Provider"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Outgoing_Rout_In_Simple_Mode_After_Edit_Provider"),
                        "Test failed - adding a outgoing route after edit provider. Skip the test!");
            else if(methodTest.equals("test_Delete_Incoming_Route_In_Expert_Mode_After_Edit_Provider"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Incoming_Rout_In_Expert_Mode_After_Edit_Provider"),
                        "Test failed - adding a incoming route after edit provider. Skip the test!");
            else if(methodTest.equals("test_Add_Incoming_Rout_In_Simple_Mode_After_Edit_Provider_Without_Reg")
                    || methodTest.equals("test_Add_Outgoing_Rout_In_Expert_Mode_After_Edit_Provider_Without_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Edit_Provider_Without_Reg"),
                        "Test failed - editing a provider without registration. Skip the test!");
            else if(methodTest.equals("test_Delete_Incoming_Route_In_Simple_Mode_After_Edit_Provider_Without_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Incoming_Rout_In_Simple_Mode_After_Edit_Provider_Without_Reg"),
                        "Test failed - adding a incoming route after edit provider without registration. Skip the test!");
            else if(methodTest.equals("test_Delete_Outgoing_Route_In_Simple_Mode_After_Edit_Provider_Without_Reg"))
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Outgoing_Rout_In_Expert_Mode_After_Edit_Provider_Without_Reg"),
                        "Test failed - adding a outgoing route after edit provider without registration. Skip the test!");
        }

        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Настройки", "Телефония");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        testsBase.init();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        closeWebDriver();
    }
}
