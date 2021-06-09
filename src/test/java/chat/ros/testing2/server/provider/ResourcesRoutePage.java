package chat.ros.testing2.server.provider;

import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.TestsBase;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ResourcesRoutePage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    private TestsBase testsBase = new TestsBase();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        testsBase.init();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        String classTest = context.getTestClass().toString();
        String methodTest = context.getRequiredTestMethod().getName();

        if(classTest.contains("TestProviderRouteIncomingSimpleMode")){
            if(methodTest.equals("test_Add_Incoming_Route_Simple_Mode")
                    || methodTest.equals("test_Modal_Info_Of_Route")
                    || methodTest.equals("test_Add_Outgoing_Route_Simple_Mode")
                    || methodTest.equals("test_Close_Modal_Window_When_Add_Incoming_Route_Simple_Mode")
                    || methodTest.equals("test_Close_Modal_Window_When_Add_Outgoing_Route_Simple_Mode")
            )
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Incoming_Route"),
                        "Test failed - adding a provider for tests route in simple mode. Skip the test!");
            else if(methodTest.equals("test_Edit_Incoming_Route_Simple_Mode")
                    || methodTest.equals("test_Edit_Incoming_Route_Simple_Mode_Without_Pattern_Replace")
                    || methodTest.equals("test_Edit_Incoming_Route_From_Simple_To_Expert_Mode")
                    || methodTest.equals("test_Delete_Incoming_Route_In_Simple_Mode")
                    || methodTest.equals("test_Close_Modal_Window_When_Edit_Incoming_Route_Simple_Mode")
                    || methodTest.equals("test_Close_Modal_Window_When_Edit_Incoming_Route_From_Simple_To_Expert_Mode")
            ) {
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Incoming_Route_Simple_Mode"),
                        "Test failed - adding a incoming route in simple mode. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Incoming_Route"),
                        "Test failed - adding a provider for tests route in simple mode. Skip the test!");
            }
            else if(methodTest.equals("test_Edit_Incoming_Route_Expert_Mode_Without_Group_Replace")
                    || methodTest.equals("test_Edit_Incoming_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace")
                    || methodTest.equals("test_Close_Modal_Window_When_Edit_Incoming_Route_Expert_Mode")
            ) {
                Assumptions.assumeTrue(TestStatusResult
                                .getTestResult()
                                .get("test_Edit_Incoming_Route_From_Simple_To_Expert_Mode"),
                        "Test failed - incoming route from simple to expert mode. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Incoming_Route"),
                        "Test failed - adding a provider for tests route in simple mode. Skip the test!");
            }else if(methodTest.equals("test_Edit_Outgoing_Route_Simple_Mode")
                    || methodTest.equals("test_Edit_Outgoing_Route_Simple_Mode_Without_Pattern_Replace")
                    || methodTest.equals("test_Edit_Outgoing_Route_From_Simple_To_Expert_Mode")
                    || methodTest.equals("test_Delete_Outgoing_Route_In_Simple_Mode")
                    || methodTest.equals("test_Close_Modal_Window_When_Edit_Outgoing_Route_Simple_Mode")
                    || methodTest.equals("test_Close_Modal_Window_When_Edit_Outgoing_Route_From_Simple_To_Expert_Mode")
            ) {
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Outgoing_Route_Simple_Mode"),
                        "Test failed - adding a outgoing route in simple mode. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Incoming_Route"),
                        "Test failed - adding a provider for tests route in simple mode. Skip the test!");
            }else if(methodTest.equals("test_Edit_Outgoing_Route_Expert_Mode_Without_Group_Replace")
                    || methodTest.equals("test_Edit_Outgoing_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace")
                    || methodTest.equals("test_Close_Modal_Window_When_Edit_Outgoing_Route_Expert_Mode")
            ) {
                Assumptions.assumeTrue(TestStatusResult
                                .getTestResult()
                                .get("test_Edit_Outgoing_Route_From_Simple_To_Expert_Mode"),
                        "Test failed - outgoing route from simple to expert mode. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Incoming_Route"),
                        "Test failed - adding a provider for tests route in simple mode. Skip the test!");
            }
        }else if(classTest.contains("TestProviderRouteExpertMode")){
            if(methodTest.equals("test_Close_Modal_Window_When_Add_Incoming_Route_With_Empty_Values")
                    || methodTest.equals("test_Close_Modal_Window_When_Add_Outgoing_Route_With_Empty_Values")
                    || methodTest.equals("test_Close_Modal_Window_When_Add_Incoming_Route")
                    || methodTest.equals("test_Add_Incoming_Route_Expert_Mode")
                    || methodTest.equals("test_Close_Modal_Window_When_Add_Outgoing_Route_Expert_Mode")
                    || methodTest.equals("test_Add_Outgoing_Route_Expert_Mode")
            )
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Route_In_Expert_Mode"),
                        "Test failed - adding a provider for tests route in expert mode. Skip the test!");
            else if(methodTest.equals("test_Close_Modal_Window_When_Edit_Incoming_Route_Expert_Mode")
                    || methodTest.endsWith("test_Edit_Incoming_Route_Expert_Mode")
                    || methodTest.equals("test_Edit_Incoming_Route_Expert_Mode_Without_Group_Replace")
                    || methodTest.equals("test_Edit_Incoming_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace")
                    || methodTest.equals("test_Delete_Incoming_Route_In_Expert_Mode")
            ) {
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Incoming_Route_Expert_Mode"),
                        "Test failed - adding a incoming route in expert mode. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Route_In_Expert_Mode"),
                        "Test failed - adding a provider for tests route in expert mode. Skip the test!");
            }else if(methodTest.equals("test_Close_Modal_Window_When_Edit_Outgoing_Route_Expert_Mode")
                    || methodTest.equals("test_Edit_Outgoing_Route_Expert_Mode")
                    || methodTest.equals("test_Edit_Outgoing_Route_Expert_Mode_Without_Group_Replace")
                    || methodTest.equals("test_Edit_Outgoing_Route_Expert_Mode_Without_Pattern_Replace_And_Group_Replace")
                    || methodTest.equals("test_Delete_Outgoing_Route_In_Expert_Mode")
            ) {
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Outgoing_Route_Expert_Mode"),
                        "Test failed - adding a outgoing route in expert mode. Skip the test!");
                Assumptions.assumeTrue(TestStatusResult.getTestResult().get("test_Add_Provider_For_Route_In_Expert_Mode"),
                        "Test failed - adding a provider for tests route in expert mode. Skip the test!");
            }
        }

        testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN, "Настройки", "Телефония");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        closeWebDriver();
    }
}
