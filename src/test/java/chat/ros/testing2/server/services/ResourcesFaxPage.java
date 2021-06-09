package chat.ros.testing2.server.services;

import chat.ros.testing2.StartWebDriver;
import chat.ros.testing2.TestStatusResult;
import chat.ros.testing2.TestsBase;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.*;

import static chat.ros.testing2.data.SettingsData.USER_LOGIN_ADMIN;
import static chat.ros.testing2.data.SettingsData.USER_PASSWORD_ADMIN;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class ResourcesFaxPage implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {

    private final String hostFaxPage = "http://"  + System.getProperty("hostserver") + ":5000";
    private TestsBase testsBase = new TestsBase();

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        String methodTest = context.getRequiredTestMethod().getName();
        TestStatusResult.setTestResult(methodTest, TestStatusResult.getStatusTest());
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
                testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Справочник");
            else
                testsBase.openMS(USER_LOGIN_ADMIN, USER_PASSWORD_ADMIN,"Сервисы","Факс");
        }
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
